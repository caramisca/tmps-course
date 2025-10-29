package md.utm.tmps.lab0.ocp;

import java.util.List;

/**
 * AreaCalculator - demonstrates OCP principle
 * This class is CLOSED for modification but OPEN for extension
 * We can add new shapes without changing this calculator class
 */
public class AreaCalculator {
    
    public double calculateTotalArea(List<Shape> shapes) {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
    

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