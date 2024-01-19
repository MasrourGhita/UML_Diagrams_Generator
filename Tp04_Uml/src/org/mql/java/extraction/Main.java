package org.mql.java.extraction;


import java.util.List;
import java.util.Vector;

import org.mql.java.reflexion.ProjectExtractor;
import org.mql.java.xml.XMLLoader;
import org.mql.java.xml.XMLPersister;


public class Main {

	 public static void main(String[] args) {
		 String projectPath = "C:\\Users\\pc\\eclipseWorksapace\\Test\\bin";
		 CustomClassLoader classLoader = CustomClassLoader.createCustomClassLoader(projectPath);

	       
	        ProjectExtractor projectExtractor = new ProjectExtractor(classLoader, projectPath);
	        CustomProject customProject = projectExtractor.extractProjectInfo(projectPath);
	      printProjectDetails(customProject);
	        XMLPersister.persistToXML(customProject, "resources/output.xml");
	      //  CustomProject loadedProject = XMLLoader.loadXMLFile("resources/output.xml");
	       // printProjectDetails(loadedProject);
	        
	    }

	 
	 private static void printProjectDetails(CustomProject customProject) {
		    for (CustomPackage customPackage : customProject.getPackages()) {
		        if (!customPackage.getName().isEmpty()) {
		            System.out.println("Package: " + customPackage.getName() + "\n");
		            for (CustomClass customClass : customPackage.getClasses()) {
		                System.out.println(" ------> " + customClass.getType() + " : " + customClass.getName());

		                List<String> fieldNames = customClass.getFieldNames();
		                if (!fieldNames.isEmpty()) {
		                    System.out.println("         Fields: ");
		                    for (String fieldName : fieldNames) {
		                        System.out.println("                 " + fieldName);
		                    }
		                }
 
		                List<String> methodNames = customClass.getMethodNames();
		                if (!methodNames.isEmpty()) {
		                    System.out.println("         Methods: ");
		                    for (String methodName : methodNames) {
		                        System.out.println("                  " + methodName);
		                    }
		                }

		                String superclass = customClass.getSuperclass();
		                if (superclass != null && !superclass.isEmpty()) {
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