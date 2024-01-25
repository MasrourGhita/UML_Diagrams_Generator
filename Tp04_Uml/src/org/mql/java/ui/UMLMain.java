package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.extraction.CustomClassLoader;
import org.mql.java.extraction.ProjectExtractor;
import org.mql.java.infoProject.CustomProject;

public class UMLMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String projectPath = "C:\\Users\\pc\\eclipseWorksapace\\Test\\bin";
            CustomClassLoader classLoader = CustomClassLoader.createCustomClassLoader(projectPath);

            ProjectExtractor projectExtractor = new ProjectExtractor(classLoader, projectPath);
            CustomProject customProject = projectExtractor.extractProjectInfo(projectPath);

            new UMLDiagramDrawer(customProject);
        });
    }
}
