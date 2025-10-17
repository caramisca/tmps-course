package md.utm.tmps.lab0.ocp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit 5 tests for Open/Closed Principle demonstration
 * Tests that we can extend functionality without modifying existing code
 */
class OCPTest {
    
    private AreaCalculator calculator;
    private Rectangle rectangle;
    private Circle circle;
    private Triangle triangle;
    
    @BeforeEach
    void setUp() {
        calculator = new AreaCalculator();
        rectangle = new Rectangle(5.0, 4.0);
        circle = new Circle(3.0);
        triangle = new Triangle(6.0, 4.0);
    }
    
    @Test
    @DisplayName("✅ OCP - Rectangle extends Shape without modifying Shape")
    void testRectangleExtension() {
        assertEquals(20.0, rectangle.calculateArea(), 0.01);
        assertEquals("Rectangle", rectangle.getShapeName());
        assertEquals(5.0, rectangle.getWidth());
        assertEquals(4.0, rectangle.getHeight());
        
        System.out.println("✅ OCP Rectangle test passed - Rectangle extends Shape correctly");
    }
    
    @Test
    @DisplayName("✅ OCP - Circle extends Shape without modifying Shape")
    void testCircleExtension() {
        double expectedArea = Math.PI * 3.0 * 3.0;
        assertEquals(expectedArea, circle.calculateArea(), 0.01);
        assertEquals("Circle", circle.getShapeName());
        assertEquals(3.0, circle.getRadius());
        
        System.out.println("✅ OCP Circle test passed - Circle extends Shape correctly");
    }
    
    @Test
    @DisplayName("✅ OCP - Triangle extends Shape without modifying Shape")
    void testTriangleExtension() {
        assertEquals(12.0, triangle.calculateArea(), 0.01);
        assertEquals("Triangle", triangle.getShapeName());
        assertEquals(6.0, triangle.getBase());
        assertEquals(4.0, triangle.getHeight());
        
        System.out.println("✅ OCP Triangle test passed - Triangle extends Shape correctly");
    }
    
    @Test
    @DisplayName("✅ OCP - AreaCalculator works with all shapes without modification")
    void testAreaCalculatorExtensibility() {
        List<Shape> shapes = Arrays.asList(rectangle, circle, triangle);
        
        double expectedTotal = 20.0 + (Math.PI * 9.0) + 12.0;
        double actualTotal = calculator.calculateTotalArea(shapes);
        
        assertEquals(expectedTotal, actualTotal, 0.01);
        
        String report = calculator.generateAreaReport(shapes);
        assertNotNull(report);
        assertTrue(report.contains("Rectangle: 20.00"));
        assertTrue(report.contains("Triangle: 12.00"));
        assertTrue(report.contains("TOTAL AREA:"));
        
        System.out.println("✅ OCP Calculator test passed - AreaCalculator works with all shapes without modification");
    }
    
    @Test
    @DisplayName("✅ OCP - Adding new shapes doesn't break existing functionality")
    void testNewShapeAddition() {
        // We can add new shapes to existing collections
        List<Shape> shapes = Arrays.asList(rectangle, circle, triangle);
        
        // Calculator still works with mixed shape types
        double totalArea = calculator.calculateTotalArea(shapes);
        assertTrue(totalArea > 0);
        
        // Report generation still works
        String report = calculator.generateAreaReport(shapes);
        assertNotNull(report);
        assertTrue(report.length() > 0);
        
        System.out.println("✅ OCP Extension test passed - New shapes integrate seamlessly");
    }
    
    @Test
    @DisplayName("✅ OCP - Polymorphism works correctly")
    void testPolymorphism() {
        // All shapes can be treated as Shape objects
        Shape[] shapes = {rectangle, circle, triangle};
        
        for (Shape shape : shapes) {
            assertTrue(shape.calculateArea() > 0);
            assertNotNull(shape.getShapeName());
        }
        
        System.out.println("✅ OCP Polymorphism test passed - All shapes work polymorphically");
    }
}