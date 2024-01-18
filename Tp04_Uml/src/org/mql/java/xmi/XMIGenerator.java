package org.mql.java.xmi;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.extraction.CustomClass;
import org.mql.java.extraction.CustomPackage;
import org.mql.java.extraction.CustomProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class XMIGenerator {

    public static void generateXMI(CustomProject customProject, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("xmi:Projet");
            rootElement.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
            rootElement.setAttribute("xmlns:uml", "http://www.omg.org/spec/UML/20090901");
            doc.appendChild(rootElement);

            for (CustomPackage customPackage : customProject.getPackages()) {
                Element packageElement = doc.createElement("xmi:Package");
                packageElement.setAttribute("nom", customPackage.getName());
                rootElement.appendChild(packageElement);

                for (CustomClass customClass : customPackage.getClasses()) {
                    Element classElement = doc.createElement("xmi:Class");
                    classElement.setAttribute("nom", customClass.getName());
                    classElement.setAttribute("type", "uml:Class");
                    packageElement.appendChild(classElement);

                    for (Field field : customClass.getFields()) {
                        Element attributeElement = doc.createElement("xmi:Attribute");
                        attributeElement.setAttribute("xmi:type", "uml:Property");
                        attributeElement.setAttribute("nom", field.getName());
                        classElement.appendChild(attributeElement);
                        addNewLineElement(doc, classElement);
                    }

                    for (Method method : customClass.getMethods()) {
                        Element operationElement = doc.createElement("xmi:Operation");
                        operationElement.setAttribute("xmi:type", "uml:Operation");
                        operationElement.setAttribute("nom", method.getName());
                        classElement.appendChild(operationElement);
                        addNewLineElement(doc, classElement);
                    }

                    if (customClass.getSuperclass() != null && !customClass.getSuperclass().isEmpty()) {
                        Element generalizationElement = doc.createElement("xmi:Generalization");
                        generalizationElement.setAttribute("xmi:type", "uml:Generalization");
                        Element superclass = doc.createElement("xmi:Generalization.superclass");
                        superclass.setTextContent(customClass.getSuperclass());
                        generalizationElement.appendChild(superclass);
                        classElement.appendChild(generalizationElement);
                        addNewLineElement(doc, classElement);
                    }

                    for (CustomClass aggregatedClass : customClass.getAggregatedClasses()) {
                        Element associationElement = doc.createElement("xmi:Association");
                        associationElement.setAttribute("xmi:type", "uml:Association");
                        Element memberEndElement = doc.createElement("xmi:Association.memberEnd");
                        memberEndElement.setTextContent(aggregatedClass.getName());
                        associationElement.appendChild(memberEndElement);
                        classElement.appendChild(associationElement);
                        addNewLineElement(doc, classElement);
                    }

                    for (CustomClass usedClass : customClass.getUsedClasses()) {
                        Element dependencyElement = doc.createElement("xmi:Dependency");
                        dependencyElement.setAttribute("xmi:type", "uml:Dependency");
                        Element supplier = doc.createElement("xmi:Dependency.supplier");
                        supplier.setTextContent(usedClass.getName());
                        dependencyElement.appendChild(supplier);
                        classElement.appendChild(dependencyElement);
                        addNewLineElement(doc, classElement);
                    }

                    addNewLineElement(doc, packageElement);
                }

                addNewLineElement(doc, rootElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("XMI généré avec succès dans " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNewLineElement(Document doc, Element parentElement) {
        parentElement.appendChild(doc.createTextNode("\n"));
    }
}
