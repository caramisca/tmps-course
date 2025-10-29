package md.utm.tmps.lab0.ocp;

/**
 * Open/Closed Principle (OCP) Demonstration
 * 
 * PRINCIPLE: Classes should be open for extension but closed for modification.
 * You should be able to add new functionality without changing existing code.
 * 
 * EXAMPLE: Shape calculator that can calculate areas for different shapes
 * without modifying the calculator class when new shapes are added.
 */

/**
 * Abstract base class for all shapes
 * This is "closed for modification" but "open for extension"
 */
public abstract class Shape {
    /**
     * Abstract method that each shape must implement
     * @return the area of the shape
     */
    public abstract double calculateArea();
    
    /**
     * @return the name of the shape
     */
    public abstract String getShapeName();
}