package org.mql.java.extraction;

import java.util.Vector;

public class CustomPackage {

    private Vector<CustomClass> classes;
    private String name;

    public CustomPackage(String name) {
        this.name = name;
        this.classes = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public String getParentPackage() {
        int lastDotIndex = name.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return name.substring(0, lastDotIndex);
        }
        return "";  // Ou lancez une exception si vous le préférez
    }

    public Vector<CustomClass> getClasses() {
        return classes;
    }

    public void addClass(CustomClass aClass) {
        classes.add(aClass);
    }
}
