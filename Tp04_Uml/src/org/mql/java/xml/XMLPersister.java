package org.mql.java.xml;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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



public class XMLPersister {

    public static void persistToXML(CustomProject customProject, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Projet");
            doc.appendChild(rootElement);


            for (CustomPackage customPackage : customProject.getPackages()) {
                Element packageElement = doc.createElement("Package");
                packageElement.setAttribute("nom", customPackage.getName());
                rootElement.appendChild(packageElement);

                for (CustomClass customClass : customPackage.getClasses()) {
                    Element classElement = doc.createElement("CustomClass");
                    classElement.setAttribute("nom", customClass.getName());
                    classElement.setAttribute("type", customClass.getType());
                    packageElement.appendChild(classElement);

                    for (Field field : customClass.getFields()) {
                        Element fieldElement = doc.createElement("Champ");
                        fieldElement.setTextContent(field.getName());
                        classElement.appendChild(fieldElement);
                        addNewLineElement(doc, classElement);
                    }

                    for (Method method : customClass.getMethods()) {
                        Element methodElement = doc.createElement("Methode");
                        methodElement.setTextContent(method.getName());
                        classElement.appendChild(methodElement);
                        addNewLineElement(doc, classElement);
                    }

                    Element superClassElement = doc.createElement("Superclass");
                    superClassElement.setTextContent(customClass.getSuperclass());
                    classElement.appendChild(superClassElement);
                    addNewLineElement(doc, classElement);

                    for (CustomClass aggregatedClass : customClass.getAggregatedClasses()) {
                        Element aggregationElement = doc.createElement("Aggregation");
                        aggregationElement.setTextContent(aggregatedClass.getName());
                        classElement.appendChild(aggregationElement);
                        addNewLineElement(doc, classElement);
                    }

                    for (CustomClass usedClass : customClass.getUsedClasses()) {
                        Element usageElement = doc.createElement("Utilisation");
                        usageElement.setTextContent(usedClass.getName());
                        classElement.appendChild(usageElement);
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

            System.out.println("Structure de données persistée dans " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNewLineElement(Document doc, Element parentElement) {
        parentElement.appendChild(doc.createTextNode("\n"));
    }
}
