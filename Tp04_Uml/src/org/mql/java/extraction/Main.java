package org.mql.java.extraction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

import org.mql.java.reflexion.ProjectExtractor;
import org.mql.java.xml.XMLPersister;


public class Main {

	 public static void main(String[] args) {
	        String projectPath = "C:\\Users\\pc\\eclipseWorksapace\\Test\\bin";
	        CustomClassLoader classLoader = CustomClassLoader.createCustomClassLoader(projectPath);
	        ProjectExtractor projectExtractor = new ProjectExtractor(classLoader);
	        CustomProject customProject = projectExtractor.extractProjectInfo(projectPath);
	        printProjectDetails(customProject);
	        XMLPersister.persistToXML(customProject, "resources/output.xml");
	    }

	 
	 private static void printProjectDetails(CustomProject customProject) {
	        for (CustomPackage customPackage : customProject.getPackages()) {
	            if (!customPackage.getName().isEmpty()) {
	                System.out.println("Package: " + customPackage.getName() + "\n");
	                for (CustomClass customClass : customPackage.getClasses()) {
	                    System.out.println(" ------> " + customClass.getType() + " : " + customClass.getName());

	                    System.out.println("         Fields: ");
	                    Vector<Field> fields = customClass.getFields();
	                    if (!fields.isEmpty()) {
	                        for (Field field : fields) {
	                            System.out.println("                 " + field.getName());
	                        }
	                    }

	                    System.out.println("         Methods: ");
	                    Vector<Method> methods = customClass.getMethods();
	                    if (!methods.isEmpty()) {
	                        for (Method method : methods) {
	                            System.out.println("                  " + method.getName());
	                        }
	                    }
	                    String superclass = customClass.getSuperclass();
	                    if (superclass != null) {
	                        System.out.println("        Extension:\n                   " + superclass);
	                    }

	                    Vector<String> aggregations = customClass.getAggregations();
	                    if (!aggregations.isEmpty()) {
	                        System.out.println("        Agrégation:\n                  " + String.join(", ", aggregations));
	                    }

	                    Vector<String> usages = customClass.getUsages();
	                    if (!usages.isEmpty()) {
	                        System.out.println("        Utilisation :\n                  " + String.join(" , ", usages));
	                    }
	                    System.out.println("\n");
	                }
	            }
	        }
	    }
	}