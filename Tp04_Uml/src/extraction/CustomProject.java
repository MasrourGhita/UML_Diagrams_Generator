package extraction;

import java.util.ArrayList;
import java.util.List;

public class CustomProject {
    private List<CustomPackage> packages;

    public CustomProject() {
        this.packages = new ArrayList<>();
    }

    public List<CustomPackage> getPackages() {
        return packages;
    }

    public void addPackage(CustomPackage customPackage) {
        packages.add(customPackage);
    }

    
    

	
}
