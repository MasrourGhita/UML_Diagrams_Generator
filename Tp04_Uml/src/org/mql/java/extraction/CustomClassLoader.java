package org.mql.java.extraction;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

public class CustomClassLoader extends URLClassLoader {

    public CustomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static CustomClassLoader createCustomClassLoader(String projectPath) {
        try {
            URL projectUrl = new File(projectPath).toURI().toURL();
            return new CustomClassLoader(new URL[]{projectUrl}, CustomClassLoader.class.getClassLoader());
        } catch (Exception e) {
            System.out.println("Erreur lors de la création du CustomClassLoader : " + e.getMessage());
            return null;
        }
    }
}