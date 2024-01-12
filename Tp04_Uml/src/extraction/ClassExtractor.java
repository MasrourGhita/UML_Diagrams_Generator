package extraction;

import java.io.File;
import java.nio.file.Files;


public class ClassExtractor {
    public static void main(String[] args) {
        String projectPath = "C:\\Users\\pc\\eclipseWorksapace\\Project\\bin";
        CustomProject customProject = extractProjectInfo(projectPath);

        for (CustomPackage customPackage : customProject.getPackages()) {
            if (!customPackage.getName().isEmpty()) {
                System.out.println("\nPackage: " + customPackage.getName());
                for (CustomClass customClass : customPackage.getClasses()) {
                    System.out.println(" ------> " + customClass.getType() + "   : " + customClass.getName());
                   
                    
                }
            }
        }
    }

  
    public static CustomProject extractProjectInfo(String projectPath) {
        CustomProject customProject = new CustomProject();

        File projectDirectory = new File(projectPath);

        if (projectDirectory.exists() && projectDirectory.isDirectory()) {
            extractPackagesRecursive(projectDirectory, customProject);
        } else {
            System.out.println("Le répertoire du projet n'existe pas.");
        }

        return customProject;
    }

    private static void extractPackagesRecursive(File directory, CustomProject customProject) {
        CustomPackage customPackage = new CustomPackage(directory.getName());

        customProject.addPackage(customPackage);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().equals("bin")) {
                    extractPackagesRecursive(file, customProject);
                } else if (file.getName().endsWith(".class")) {
                    extractClassInfo(file, customPackage);
                }
            }
        }
    }

    private static void extractClassInfo(File file, CustomPackage customPackage) {
        try {
            String className = file.getName().replace(".class", "");
            CustomClass customClass = new CustomClass(className);

            String sourceCode = new String(Files.readAllBytes(file.toPath()));

            if (sourceCode.contains("@interface " + className)) {
                customClass.setType("annotation");
            } else if (sourceCode.contains("enum " + className)) {
                customClass.setType("Enumeration");
            } else if (sourceCode.contains("interface " + className)) {
                customClass.setType("interface");
            } else {
                customClass.setType("Class");
            }

            customPackage.addClass(customClass);
        } catch (Exception e) {
            System.out.println("Erreur :" + e.getMessage());
        }
    }
}

