package extraction;

import java.util.ArrayList;
import java.util.List;

public class CustomPackage {
    private String name;
    private List<CustomClass> classes;

    public CustomPackage(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<CustomClass> getClasses() {
        return classes;
    }

    public void addClass(CustomClass aClass) {
        classes.add(aClass);
    }
}
