package md.utm.tmps.lab0.ocp;

/**
 * Triangle implementation - extends Shape without modifying it
 * This demonstrates OCP: we can add new shapes without changing existing code
 */
public class Triangle extends Shape {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    @Override
    public String getShapeName() {
        return "Triangle";
    }
    
    // Getters
    public double getBase() { return base; }
    public double getHeight() { return height; }
}