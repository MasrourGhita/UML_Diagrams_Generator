package org.mql.java.reflexion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mql.java.infoProject.CustomClass;
import org.mql.java.infoProject.CustomPackage;
import org.mql.java.infoProject.CustomProject;



public class RelationsExtractor {
	public static void extractMethods(CustomClass customClass, Class<?> clazz) {
	    try {
	        Method[] methods = clazz.getDeclaredMethods();
	        for (Method method : methods) {
	            customClass.addMethod(method);
	            customClass.addMethodName(method.getName());
	           
	        } 
	    } catch (Exception e) {
	        System.out.println("Erreur lors de l'extraction des méthodes : " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void extractFields(CustomClass customClass, Class<?> clazz) {
	    try {
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            customClass.addField(field);
	            customClass.addFieldName(field.getName());
	          
	        }
	    } catch (Exception e) {
	        System.out.println("Erreur lors de l'extraction des champs : " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	   
	  
	    
	   
	    public static void extractExtraction(CustomClass customClass, Class<?> clazz) {
	        try {
	            Class<?> superclass = clazz.getSuperclass();
	            if (superclass != null) {
	                customClass.setSuperclass(superclass.getSimpleName());
	            }
	        } catch (Exception e) {
	            System.out.println("Erreur lors de l'extraction de l'extension : " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    public static void extractAggregations(CustomClass customClass, Class<?> clazz, CustomProject customProject) {
	        try {
	            Field[] fields = clazz.getDeclaredFields();
	            for (Field field : fields) {
	                Class<?> fieldType = field.getType();
	                CustomClass referencedClass = findClassByType(customProject, fieldType);
	                if (referencedClass != null) {
	                    customClass.addAggregation(referencedClass);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Erreur lors de l'extraction des agrégations : " + e.getMessage());
	        }
	    }

	    private static CustomClass findClassByType(CustomProject customProject, Class<?> type) {
	        try {
	            for (CustomPackage customPackage : customProject.getPackages()) {
	                for (CustomClass customClass : customPackage.getClasses()) {
	                    if (customClass.getName().equals(type.getSimpleName())) {
	                        return customClass;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Erreur : " + e.getMessage());
	        }
	        return null;
	    }

		  public static void extractUsages(CustomClass customClass, Method[] methods, CustomProject customProject) {
       try {
           for (Method method : methods) {
               Class<?>[] parameterTypes = method.getParameterTypes();
               for (Class<?> parameterType : parameterTypes) {
                   CustomClass usedClass = findClassByType(customProject, parameterType);
                   if (usedClass != null) {
                       customClass.addUsage(usedClass);

                       
                      
                   }
               }
           }
       } catch (Exception e) {
           System.out.println("Erreur lors de l'extraction des utilisations : " + e.getMessage());
           e.printStackTrace();
       }
   }
		  

}