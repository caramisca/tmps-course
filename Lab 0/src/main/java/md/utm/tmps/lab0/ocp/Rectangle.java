package md.utm.tmps.lab0.ocp;

/**
 * Rectangle implementation - extends Shape without modifying it
 * This demonstrates OCP: we extend functionality without changing existing code
 */
public class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public String getShapeName() {
        return "Rectangle";
    }
    
    // Getters
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}