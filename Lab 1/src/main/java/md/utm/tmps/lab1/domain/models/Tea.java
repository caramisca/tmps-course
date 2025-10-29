package md.utm.tmps.lab1.domain.models;

/**
 * Tea beverage implementation
 */
public class Tea extends Beverage {
    private String teaType;
    
    public Tea(String size, String teaType) {
        super("Tea", size, calculatePrice(size));
        this.teaType = teaType;
    }
    
    private static double calculatePrice(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> 2.00;
            case "medium" -> 3.00;
            case "large" -> 4.00;
            default -> 3.00;
        };
    }
    
    @Override
    public void prepare() {
        System.out.println("Steeping " + teaType + " tea leaves...");
        System.out.println("Adding hot water...");
        System.out.println("Tea is ready!");
    }
    
    @Override
    public String getDescription() {
        return teaType + " " + name + " - " + size;
    }
    
    public String getTeaType() { return teaType; }
}
