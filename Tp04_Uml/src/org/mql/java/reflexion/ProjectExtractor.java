package org.mql.java.reflexion;

import java.io.File;

import org.mql.java.extraction.CustomClass;
import org.mql.java.extraction.CustomClassLoader;
import org.mql.java.extraction.CustomPackage;
import org.mql.java.extraction.CustomProject;

public class ProjectExtractor {
    private CustomClassLoader classLoader;

    public ProjectExtractor(CustomClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public CustomProject extractProjectInfo(String projectPath) {
        CustomProject customProject = new CustomProject();
        File projectDirectory = new File(projectPath);
        if (projectDirectory.exists() && projectDirectory.isDirectory()) {
            extractPackagesRecursive(projectDirectory, customProject);
        } else {
            System.out.println("Le répertoire du projet n'existe pas.");
        }
        return customProject;
    }

    private void extractPackagesRecursive(File directory, CustomProject customProject) {
        CustomPackage customPackage = new CustomPackage(directory.getName());
        customProject.addPackage(customPackage);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().equals("src")) {
                    extractPackagesRecursive(file, customProject);
                } else if (file.getName().endsWith(".class")) {
                    extractClassInfo(file, customPackage, customProject);
                }
            }
        }
    }

    private void extractClassInfo(File file, CustomPackage customPackage, CustomProject customProject) {
        try {
            String className = file.getName().replace(".class", "");
            CustomClass customClass = new CustomClass(className);

            try {
                Class<?> clazz = classLoader.loadClass(customPackage.getName() + "." + className);
                if (clazz != null) {
                    customClass.setType(TypeClass(clazz));

                    RelationsExtractor.extractMethods(customClass, clazz);
                    RelationsExtractor.extractFields(customClass, clazz);
                    RelationsExtractor.extractInheritance(customClass, clazz);
                    RelationsExtractor.extractAggregations(customClass, clazz, customProject);
                    RelationsExtractor.extractUsages(customClass, clazz.getDeclaredMethods(), customProject);
                    customPackage.addClass(customClass);
                   
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Erreur : Classe non trouvée - " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'extraction des informations de la classe : " + e.getMessage());
        } 
    }

    private String TypeClass(Class<?> clazz) {
        if (clazz.isAnnotation()) {
            return "Annotation";
        } else if (clazz.isEnum()) {
            return "Enumeration";
        } else if (clazz.isInterface()) {
            return "Interface";
        } else {
            return "Class";
        }
    }

    
}
