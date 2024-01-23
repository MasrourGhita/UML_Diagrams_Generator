package org.mql.java.xml;


import org.mql.java.infoProject.CustomClass;
import org.mql.java.infoProject.CustomPackage;
import org.mql.java.infoProject.CustomProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLLoader {

    public static CustomProject loadXMLFile(String filePath) {
        CustomProject customProject = new CustomProject();

        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList packageNodes = doc.getElementsByTagName("Package");
            for (int i = 0; i < packageNodes.getLength(); i++) {
                Node packageNode = packageNodes.item(i);
                if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element packageElement = (Element) packageNode;
                    String packageName = packageElement.getAttribute("nom");
                    CustomPackage customPackage = new CustomPackage(packageName);
                    customProject.addPackage(customPackage);

                    NodeList classNodes = packageElement.getElementsByTagName("CustomClass");
                    for (int j = 0; j < classNodes.getLength(); j++) {
                        Node classNode = classNodes.item(j);
                        if (classNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element classElement = (Element) classNode;
                            String className = classElement.getAttribute("nom");
                            String classType = classElement.getAttribute("type");
                            CustomClass customClass = new CustomClass(className);
                            customClass.setType(classType);
                            customPackage.addClass(customClass);

                            NodeList fieldNodes = classElement.getElementsByTagName("Champ");
                            for (int k = 0; k < fieldNodes.getLength(); k++) {
                                Node fieldNode = fieldNodes.item(k);
                                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element fieldElement = (Element) fieldNode;
                                    String fieldName = fieldElement.getTextContent();
                                    customClass.addFieldName(fieldName);
                                }
                            }

                            NodeList aggregationNodes = classElement.getElementsByTagName("Aggregation");
                            for (int k = 0; k < aggregationNodes.getLength(); k++) {
                                Node aggregationNode = aggregationNodes.item(k);
                                if (aggregationNode.getNodeType() == Node.ELEMENT_NODE) {
                                    customClass.addAggregation(customClass);
                                }
                            }

                            NodeList superclassNodes = classElement.getElementsByTagName("Superclass");
                            if (superclassNodes.getLength() > 0) {
                                Node superclassNode = superclassNodes.item(0);
                                if (superclassNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element superclassElement = (Element) superclassNode;
                                    String superclass = superclassElement.getTextContent();
                                    customClass.setSuperclass(superclass);
                                }
                            }
                            NodeList usageNodes = classElement.getElementsByTagName("Utilisation");
                            for (int k = 0; k < usageNodes.getLength(); k++) {
                                Node usageNode = usageNodes.item(k);
                                if (usageNode.getNodeType() == Node.ELEMENT_NODE) {
                                    customClass.addUsage(customClass);
                                }
                            }
                            NodeList methodNodes = classElement.getElementsByTagName("Methode");
                            for (int k = 0; k < methodNodes.getLength(); k++) {
                                Node methodNode = methodNodes.item(k);
                                if (methodNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element methodElement = (Element) methodNode;
                                    String methodName = methodElement.getTextContent();
                                    customClass.addMethodName(methodName);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customProject;
    }
}