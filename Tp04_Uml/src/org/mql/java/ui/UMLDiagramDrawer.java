package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.extraction.CustomClassLoader;
import org.mql.java.extraction.ProjectExtractor;
import org.mql.java.infoProject.CustomProject;

public class UMLDiagramDrawer extends JFrame {

    private static final long serialVersionUID = 1L;

    public UMLDiagramDrawer(CustomProject customProject) {
        setTitle("UML Class Diagram");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UMLPanel panel = new UMLPanel(customProject);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setSize(800, 600);
        setLocationRelativeTo(null);

        setVisible(true);
    }

   
}
