package org.mql.java.ui;

import java.awt.*;
import java.util.List;

import javax.swing.JPanel;

import org.mql.java.infoProject.CustomClass;
import org.mql.java.infoProject.CustomPackage;
import org.mql.java.infoProject.CustomProject;

public class UMLPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private CustomProject customProject;
    private final int diagramSpacing = 50;

    public UMLPanel(CustomProject customProject) {
        this.customProject = customProject;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        List<CustomPackage> packages = customProject.getPackages();
        int x = 50;
        int y = 50;

        for (CustomPackage customPackage : packages) {
            for (CustomClass customClass : customPackage.getClasses()) {
                drawClass(g2d, customClass, x, y);
                x += 300 + diagramSpacing;
            }
        }
    }

    private void drawClass(Graphics2D g2d, CustomClass customClass, int x, int y) {
        int totalAttributesAndMethods = customClass.getFieldNames().size() + customClass.getMethodNames().size();
        int classHeight = Math.max(100, totalAttributesAndMethods * 15 + 40);

        g2d.drawRect(x, y, 300, classHeight);
        g2d.drawString("Class: " + customClass.getName(), x + 10, y + 20);

        int lineY1 = y + 40;
        g2d.drawLine(x, lineY1, x + 300, lineY1);

        int yOffsetAttributes = lineY1 + 20;
        drawSection(g2d, " ", customClass.getFieldNames(), x, yOffsetAttributes);

        int lineY2 = yOffsetAttributes + 20 + customClass.getFieldNames().size() * 20;
        g2d.drawLine(x, lineY2, x + 300, lineY2); // Ajout de la ligne entre champs et méthodes

        int yOffsetMethods = lineY2 + 20;
        drawSection(g2d, "", customClass.getMethodNames(), x, yOffsetMethods);
    }

    private void drawSection(Graphics2D g2d, String sectionName, List<String> items, int x, int yOffset) {
        g2d.drawString(sectionName + ":", x + 10, yOffset);
        int tempYOffset = yOffset + 20;
        for (String itemName : items) {
            g2d.drawString("- " + itemName, x + 10, tempYOffset);
            tempYOffset += 20;
        }
    }
    @Override
    public Dimension getPreferredSize() {
        int numClasses = customProject.getPackages().stream().mapToInt(pkg -> pkg.getClasses().size()).sum();
        int width = numClasses * 350 + 50;
        return new Dimension(width, 600);
    }
}
