package org.mql.java.extraction;

import java.util.Vector;

public class CustomProject {

    private Vector<CustomPackage> packages;
   
    public CustomProject() {
        this.packages = new Vector<>();
    }

    public Vector<CustomPackage> getPackages() {
        return packages;
    }

    public void addPackage(CustomPackage customPackage) {
        packages.add(customPackage);
    }

   
}
