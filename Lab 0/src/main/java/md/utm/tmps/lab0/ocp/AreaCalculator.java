package md.utm.tmps.lab0.ocp;

import java.util.List;

/**
 * AreaCalculator - demonstrates OCP principle
 * This class is CLOSED for modification but OPEN for extension
 * We can add new shapes without changing this calculator class
 */
public class AreaCalculator {
    
    /**
     * Calculates total area of all shapes
     * This method doesn't need to change when we add new shape types
     * @param shapes list of shapes to calculate total area for
     * @return total area of all shapes
     */
    public double calculateTotalArea(List<Shape> shapes) {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
    
    /**
     * Generates a report of all shapes and their areas
     * This method also doesn't need to change for new shapes
     * @param shapes list of shapes to report on
     * @return formatted string report
     */
    public String generateAreaReport(List<Shape> shapes) {
        StringBuilder report = new StringBuilder();
        report.append("=== AREA CALCULATION REPORT ===\n");
        
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            double area = shape.calculateArea();
            totalArea += area;
            report.append(String.format("%s: %.2f\n", shape.getShapeName(), area));
        }
        
        report.append(String.format("TOTAL AREA: %.2f\n", totalArea));
        report.append("===============================");
        
        return report.toString();
    }
}