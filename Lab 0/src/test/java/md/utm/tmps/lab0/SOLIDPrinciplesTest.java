package md.utm.tmps.lab0;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import md.utm.tmps.lab0.srp.*;
import md.utm.tmps.lab0.ocp.*;
import md.utm.tmps.lab0.lsp.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for all SOLID principles
 * This class demonstrates all three principles working together
 */
@DisplayName("🎯 SOLID Principles Test Suite")
class SOLIDPrinciplesTest {
    
    @BeforeEach
    void setUp() {
        System.out.println("🧪 Starting SOLID principles test...");
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("✅ Test completed successfully!\n");
    }
    
    @Test
    @DisplayName("✅ SRP Integration Test")
    void testSingleResponsibilityPrinciple() {
        System.out.println("Testing Single Responsibility Principle...");
        
        // Each class has one responsibility
        User user = new User("John Doe", "john@example.com", 25);
        UserValidator validator = new UserValidator();
        UserRepository repository = new UserRepository();
        
        // Test that each class does its job correctly
        assertTrue(validator.isValid(user), "Validator should validate correctly");
        assertTrue(repository.save(user), "Repository should save correctly");
        assertEquals(1, repository.getUserCount(), "Repository should track count correctly");
        
        System.out.println("✅ SRP test passed");
    }
    
    @Test
    @DisplayName("✅ OCP Integration Test")
    void testOpenClosedPrinciple() {
        System.out.println("Testing Open/Closed Principle...");
        
        // We can add new shapes without modifying AreaCalculator
        Rectangle rectangle = new Rectangle(4.0, 5.0);
        Circle circle = new Circle(3.0);
        Triangle triangle = new Triangle(6.0, 4.0);
        
        List<Shape> shapes = Arrays.asList(rectangle, circle, triangle);
        AreaCalculator calculator = new AreaCalculator();
        
        double totalArea = calculator.calculateTotalArea(shapes);
        assertTrue(totalArea > 0, "Calculator should handle all shapes");
        
        String report = calculator.generateAreaReport(shapes);
        assertNotNull(report, "Report should be generated for all shapes");
        
        System.out.println("✅ OCP test passed");
    }
    
    @Test
    @DisplayName("✅ LSP Integration Test")
    void testLiskovSubstitutionPrinciple() {
        System.out.println("Testing Liskov Substitution Principle...");
        
        // All birds can be substituted for Bird
        Eagle eagle = new Eagle("Golden Eagle");
        Sparrow sparrow = new Sparrow("House Sparrow");
        Penguin penguin = new Penguin("Emperor Penguin");
        
        BirdKeeper keeper = new BirdKeeper();
        
        // All birds should work as Bird objects
        List<Bird> birds = Arrays.asList(eagle, sparrow, penguin);
        assertDoesNotThrow(() -> keeper.feedAllBirds(birds), "All birds should work as Bird objects");
        
        // Flying birds should work as Flyable objects
        List<Flyable> flyingBirds = Arrays.asList(eagle, sparrow);
        assertDoesNotThrow(() -> keeper.organizeFlightTraining(flyingBirds), "Flying birds should work as Flyable objects");
        
        System.out.println("✅ LSP test passed");
    }
    
    @Test
    @DisplayName("🎯 All SOLID Principles Working Together")
    void testAllPrinciplesTogether() {
        System.out.println("Testing all SOLID principles working together...");
        
        // SRP: Each class has one responsibility
        User user = new User("Alice", "alice@example.com", 30);
        UserValidator validator = new UserValidator();
        UserRepository repository = new UserRepository();
        
        // OCP: We can extend with new shapes
        Rectangle rectangle = new Rectangle(3.0, 4.0);
        Circle circle = new Circle(2.5);
        AreaCalculator calculator = new AreaCalculator();
        
        // LSP: Birds can be substituted correctly
        Eagle eagle = new Eagle("Bald Eagle");
        Penguin penguin = new Penguin("King Penguin");
        BirdKeeper keeper = new BirdKeeper();
        
        // All principles work together
        assertTrue(validator.isValid(user), "SRP: Validation works");
        assertTrue(repository.save(user), "SRP: Storage works");
        
        List<Shape> shapes = Arrays.asList(rectangle, circle);
        double totalArea = calculator.calculateTotalArea(shapes);
        assertTrue(totalArea > 0, "OCP: Extension works");
        
        List<Bird> birds = Arrays.asList(eagle, penguin);
        assertDoesNotThrow(() -> keeper.feedAllBirds(birds), "LSP: Substitution works");
        
        System.out.println("🎉 All SOLID principles work together perfectly!");
    }
}