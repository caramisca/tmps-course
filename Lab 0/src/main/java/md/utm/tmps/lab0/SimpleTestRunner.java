package md.utm.tmps.lab0;

import md.utm.tmps.lab0.srp.*;
import md.utm.tmps.lab0.ocp.*;
import md.utm.tmps.lab0.lsp.*;

import java.util.Arrays;
import java.util.List;

/**
 * Simple test runner without JUnit dependencies
 * Demonstrates all SOLID principles with basic assertions
 */
public class SimpleTestRunner {
    
    private static int testsPassed = 0;
    private static int testsTotal = 0;
    
    public static void main(String[] args) {
        System.out.println("SOLID PRINCIPLES TEST SUITE");
        System.out.println("============================\n");
        
        runSRPTests();
        runOCPTests();
        runLSPTests();
        
        System.out.println("\n" + "=".repeat(40));
        System.out.println("TEST RESULTS: " + testsPassed + "/" + testsTotal + " tests passed");
        
        if (testsPassed == testsTotal) {
            System.out.println("SUCCESS: All tests passed!");
        } else {
            System.out.println("FAILURE: Some tests failed!");
        }
    }
    
    private static void runSRPTests() {
        System.out.println("Testing Single Responsibility Principle:");
        System.out.println("-----------------------------------------");
        
        // Test SRP
        try {
            User user = new User("John", "john@example.com", 25);
            UserValidator validator = new UserValidator();
            UserRepository repository = new UserRepository();
            
            // Test User class
            assertTrue(user.getName().equals("John"), "User name should be 'John'");
            assertTrue(user.getEmail().equals("john@example.com"), "User email should match");
            assertTrue(user.getAge() == 25, "User age should be 25");
            
            // Test UserValidator class
            assertTrue(validator.isValid(user), "Valid user should pass validation");
            
            User invalidUser = new User("", "bad-email", -1);
            assertTrue(!validator.isValid(invalidUser), "Invalid user should fail validation");
            
            // Test UserRepository class
            assertTrue(repository.save(user), "Repository should save user");
            assertTrue(repository.getUserCount() == 1, "Repository should show 1 user");
            assertTrue(repository.findByEmail("john@example.com") != null, "Repository should find user by email");
            
            System.out.println("SUCCESS: SRP tests passed");
            
        } catch (Exception e) {
            System.out.println("ERROR: SRP tests failed - " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void runOCPTests() {
        System.out.println("Testing Open/Closed Principle:");
        System.out.println("-------------------------------");
        
        try {
            Rectangle rectangle = new Rectangle(4.0, 5.0);
            Circle circle = new Circle(3.0);
            Triangle triangle = new Triangle(6.0, 4.0);
            
            AreaCalculator calculator = new AreaCalculator();
            
            // Test individual shapes
            assertTrue(rectangle.calculateArea() == 20.0, "Rectangle area should be 20.0");
            assertTrue(Math.abs(circle.calculateArea() - (Math.PI * 9.0)) < 0.01, "Circle area should be PI * 9");
            assertTrue(triangle.calculateArea() == 12.0, "Triangle area should be 12.0");
            
            // Test calculator with all shapes
            List<Shape> shapes = Arrays.asList(rectangle, circle, triangle);
            double totalArea = calculator.calculateTotalArea(shapes);
            assertTrue(totalArea > 40.0, "Total area should be greater than 40");
            
            String report = calculator.generateAreaReport(shapes);
            assertTrue(report.contains("Rectangle"), "Report should contain Rectangle");
            assertTrue(report.contains("Circle"), "Report should contain Circle");
            assertTrue(report.contains("Triangle"), "Report should contain Triangle");
            
            System.out.println("SUCCESS: OCP tests passed");
            
        } catch (Exception e) {
            System.out.println("ERROR: OCP tests failed - " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void runLSPTests() {
        System.out.println("Testing Liskov Substitution Principle:");
        System.out.println("---------------------------------------");
        
        try {
            Eagle eagle = new Eagle("Golden Eagle");
            Sparrow sparrow = new Sparrow("House Sparrow");
            Penguin penguin = new Penguin("Emperor Penguin");
            
            BirdKeeper keeper = new BirdKeeper();
            
            // Test that all birds work as Bird objects
            Bird[] birds = {eagle, sparrow, penguin};
            for (Bird bird : birds) {
                assertTrue(bird.getName() != null, "Bird name should not be null");
                assertTrue(bird.getSpecies() != null, "Bird species should not be null");
            }
            
            // Test flying birds
            assertTrue(eagle instanceof Flyable, "Eagle should be flyable");
            assertTrue(sparrow instanceof Flyable, "Sparrow should be flyable");
            assertTrue(!(penguin instanceof Flyable), "Penguin should not be flyable");
            
            // Test BirdKeeper works with all birds
            List<Bird> allBirds = Arrays.asList(eagle, sparrow, penguin);
            // This should not throw any exceptions
            keeper.feedAllBirds(allBirds);
            
            // Test flying operations
            List<Flyable> flyingBirds = Arrays.asList(eagle, sparrow);
            keeper.organizeFlightTraining(flyingBirds);
            
            // Test penguin specific behavior
            assertTrue(penguin.getSwimmingSpeed() > 0, "Penguin should have swimming speed");
            
            System.out.println("SUCCESS: LSP tests passed");
            
        } catch (Exception e) {
            System.out.println("ERROR: LSP tests failed - " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void assertTrue(boolean condition, String message) {
        testsTotal++;
        if (condition) {
            testsPassed++;
            System.out.println("  PASS: " + message);
        } else {
            System.out.println("  FAIL: " + message);
        }
    }
}