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

   

    public Vector<CustomClass> getClasses() {
        return classes;
    }

    public void addClass(CustomClass aClass) {
        classes.add(aClass);
    }
}
