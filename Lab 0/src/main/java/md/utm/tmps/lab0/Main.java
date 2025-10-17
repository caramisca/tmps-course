package md.utm.tmps.lab0;

import md.utm.tmps.lab0.srp.*;
import md.utm.tmps.lab0.ocp.*;
import md.utm.tmps.lab0.lsp.*;

import java.util.Arrays;
import java.util.List;

/**
 * Main class demonstrating all three SOLID principles
 * This class runs examples of SRP, OCP, and LSP with visual output
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("SOLID PRINCIPLES DEMONSTRATION");
        System.out.println("=====================================\n");
        
        // Demonstrate SRP
        demonstrateSRP();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Demonstrate OCP
        demonstrateOCP();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Demonstrate LSP
        demonstrateLSP();
        
        System.out.println("\n*** All SOLID principles demonstrated successfully! ***");
    }
    
    /**
     * Demonstrates Single Responsibility Principle (SRP)
     */
    private static void demonstrateSRP() {
        System.out.println("1. SINGLE RESPONSIBILITY PRINCIPLE (SRP)");
        System.out.println("Each class has only ONE reason to change\n");
        
        try {
            // Create instances of our SRP classes
            User user1 = new User("John Doe", "john@example.com", 25);
            User user2 = new User("", "invalid-email", -5); // Invalid user
            
            UserValidator validator = new UserValidator();
            UserRepository repository = new UserRepository();
            
            System.out.println(">> Testing User class (Data responsibility):");
            System.out.println("   Valid user: " + user1);
            System.out.println("   Invalid user: " + user2);
            
            System.out.println("\n>> Testing UserValidator class (Validation responsibility):");
            System.out.println("   User1 valid: " + validator.isValid(user1));
            System.out.println("   User2 valid: " + validator.isValid(user2));
            System.out.println("   User2 error: " + validator.getValidationError(user2));
            
            System.out.println("\n>> Testing UserRepository class (Storage responsibility):");
            repository.save(user1);
            System.out.println("   Saved user1. Total users: " + repository.getUserCount());
            System.out.println("   Found by email: " + repository.findByEmail("john@example.com"));
            
            System.out.println("\nSUCCESS: SRP test passed - Each class has a single, well-defined responsibility!");
            
        } catch (Exception e) {
            System.out.println("ERROR: SRP test failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates Open/Closed Principle (OCP)
     */
    private static void demonstrateOCP() {
        System.out.println("2. OPEN/CLOSED PRINCIPLE (OCP)");
        System.out.println("Open for extension, closed for modification\n");
        
        try {
            // Create different shapes without modifying the AreaCalculator
            Rectangle rectangle = new Rectangle(5.0, 4.0);
            Circle circle = new Circle(3.0);
            Triangle triangle = new Triangle(6.0, 4.0);
            
            List<Shape> shapes = Arrays.asList(rectangle, circle, triangle);
            AreaCalculator calculator = new AreaCalculator();
            
            System.out.println(">> Created shapes:");
            System.out.println("   Rectangle (5x4): " + rectangle.calculateArea());
            System.out.println("   Circle (radius 3): " + String.format("%.2f", circle.calculateArea()));
            System.out.println("   Triangle (base 6, height 4): " + triangle.calculateArea());
            
            System.out.println("\n>> Area Calculator Report:");
            String report = calculator.generateAreaReport(shapes);
            System.out.println(report);
            
            System.out.println("\nSUCCESS: OCP test passed - Added new shapes without modifying AreaCalculator!");
            
        } catch (Exception e) {
            System.out.println("ERROR: OCP test failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates Liskov Substitution Principle (LSP)
     */
    private static void demonstrateLSP() {
        System.out.println("3. LISKOV SUBSTITUTION PRINCIPLE (LSP)");
        System.out.println("Subclasses should be substitutable for their base classes\n");
        
        try {
            // Create different birds
            Eagle eagle = new Eagle("Golden Eagle");
            Sparrow sparrow = new Sparrow("House Sparrow");
            Penguin penguin = new Penguin("Emperor Penguin");
            
            BirdKeeper keeper = new BirdKeeper();
            
            System.out.println(">> Testing LSP with different bird types:");
            
            // All birds can be treated as Bird objects (LSP compliance)
            List<Bird> allBirds = Arrays.asList(eagle, sparrow, penguin);
            keeper.feedAllBirds(allBirds);
            
            System.out.println("\n>> Individual bird care (LSP - any Bird can be substituted):");
            keeper.careForBird(eagle);
            System.out.println();
            keeper.careForBird(penguin);
            
            System.out.println("\n>> Flight operations (only for flying birds):");
            List<Flyable> flyingBirds = Arrays.asList(eagle, sparrow);
            keeper.organizeFlightTraining(flyingBirds);
            
            System.out.println("\n>> Penguin-specific behavior:");
            penguin.swim();
            penguin.slide();
            System.out.println("Penguin swimming speed: " + penguin.getSwimmingSpeed() + " km/h");
            
            System.out.println("\nSUCCESS: LSP test passed - All Bird subclasses are perfectly substitutable!");
            
        } catch (Exception e) {
            System.out.println("ERROR: LSP test failed: " + e.getMessage());
        }
    }
}