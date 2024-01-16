package org.mql.java.extraction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

public class CustomClass {
    private String name;
    private String type;
    private Vector<Method> methods;
    private Vector<CustomClass> aggregatedClasses;
    private Vector<CustomClass> usedClasses;
    private Vector<Field> fields;
    private String superclass;

    public CustomClass(String name) {
        this.name = name;
        this.methods = new Vector<>();
        this.aggregatedClasses = new Vector<>();
        this.usedClasses = new Vector<>();
        this.fields = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Vector<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public Vector<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public Vector<String> getAggregations() {
        Vector<String> aggregationNames = new Vector<>();
        for (CustomClass aggregatedClass : aggregatedClasses) {
            aggregationNames.add(aggregatedClass.getName());
        }
        return aggregationNames;
    }

    public Vector<String> getUsages() {
        Vector<String> usageNames = new Vector<>();
        for (CustomClass usedClass : usedClasses) {
            usageNames.add(usedClass.getName());
        }
        return usageNames;
    }

    public void addAggregation(CustomClass aggregatedClass) {
        aggregatedClasses.add(aggregatedClass);
    }

    public void addUsage(CustomClass usedClass) {
        usedClasses.add(usedClass);
    }
    
    public Vector<CustomClass> getAggregatedClasses() {
        return aggregatedClasses;
    }

    public Vector<CustomClass> getUsedClasses() {
        return usedClasses;
    }
}
