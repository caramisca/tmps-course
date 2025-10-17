package md.utm.tmps.lab0.ocp;

/**
 * Circle implementation - extends Shape without modifying it
 * This demonstrates OCP: we extend functionality without changing existing code
 */
public class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String getShapeName() {
        return "Circle";
    }
    
    // Getter
    public double getRadius() { return radius; }
}