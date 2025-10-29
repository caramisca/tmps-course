# Lab 0 - SOLID Principles Demonstration

**Course:** Tehnici si Metode de Proiectare Software 

**Student:** Caraman Mihai

**Group:** FAF 233 

**Date:** October 2025 

##  Project Overview

This project demonstrates the first three SOLID principles through practical Java examples:

1. **Single Responsibility Principle (SRP)**
2. **Open/Closed Principle (OCP)**
3. **Liskov Substitution Principle (LSP)**

Additionally, this README includes example notes for the remaining two SOLID principles:
4. **Interface Segregation Principle (ISP)**
5. **Dependency Inversion Principle (DIP)**

Each principle is implemented in a separate package with clear examples, comprehensive tests, and detailed documentation.
##  Project Structure

  

```

src/

├── main/java/md/utm/tmps/lab0/

│   ├── Main.java                    # Main demonstration class

│   ├── srp/                         # Single Responsibility Principle

│   │   ├── User.java               # User data representation

│   │   ├── UserValidator.java      # User validation logic

│   │   └── UserRepository.java     # User storage operations

│   ├── ocp/                         # Open/Closed Principle

│   │   ├── Shape.java              # Abstract shape base class

│   │   ├── Rectangle.java          # Rectangle implementation

│   │   ├── Circle.java             # Circle implementation

│   │   ├── Triangle.java           # Triangle implementation

│   │   └── AreaCalculator.java     # Area calculation service

│   └── lsp/                         # Liskov Substitution Principle

│       ├── Bird.java               # Abstract bird base class

│       ├── Flyable.java            # Flying behavior interface

│       ├── Eagle.java              # Flying bird implementation

│       ├── Sparrow.java            # Flying bird implementation

│       ├── Penguin.java            # Non-flying bird implementation

│       └── BirdKeeper.java         # Bird management service

└── test/java/md/utm/tmps/lab0/

    ├── SOLIDPrinciplesTest.java    # Comprehensive test suite

    ├── srp/SRPTest.java            # SRP-specific tests

    ├── ocp/OCPTest.java            # OCP-specific tests

    └── lsp/LSPTest.java            # LSP-specific tests

```

  
  

##  SOLID Principles Explanation

---
### 1. Single Responsibility Principle (SRP)

> [!definition]
> **"A class should have only one reason to change."**

**Implementation:**

- `User` class: Only handles user data representation
- `UserValidator` class: Only handles user data validation
- `UserRepository` class: Only handles user data storage

**Code Example:**

```java
// User.java - ONLY responsible for holding user data
public class User {
    private String name;
    private String email;
    private int age;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
}

// UserValidator.java - ONLY responsible for validation logic
public class UserValidator {
    public boolean isValid(User user) {
        return isNameValid(user.getName()) && 
               isEmailValid(user.getEmail()) && 
               isAgeValid(user.getAge());
    }
    
    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }
    
    private boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    private boolean isAgeValid(int age) {
        return age > 0 && age < 150;
    }
}

// UserRepository.java - ONLY responsible for data storage
public class UserRepository {
    private List<User> users = new ArrayList<>();
    
    public boolean save(User user) {
        if (user != null) {
            users.add(user);
            return true;
        }
        return false;
    }
    
    public User findByEmail(String email) {
        return users.stream()
                   .filter(user -> user.getEmail().equals(email))
                   .findFirst()
                   .orElse(null);
    }
}
```

**Benefits:**

- Easy to maintain and modify
- Clear separation of concerns
- Reduced coupling between functionalities
- Each class has exactly one reason to change

**Example Output:**

```

 SRP test passed - Each class has a single, well-defined responsibility!

```
---
### 2. Open/Closed Principle (OCP)

> [!definition]
> **"Software entities should be open for extension but closed for modification."**

**Implementation:**

- `Shape` abstract class: Closed for modification
- `Rectangle`, `Circle`, `Triangle`: Extensions without modifying existing code
- `AreaCalculator`: Works with any shape without modification

**Code Example:**

```java
// Shape.java - Abstract base class (CLOSED for modification)
public abstract class Shape {
    public abstract double calculateArea();
    public abstract String getShapeName();
}

// Circle.java - EXTENDS Shape without modifying it (OPEN for extension)
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
}

// Rectangle.java - Another extension (adding new shapes is easy!)
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
}

// AreaCalculator.java - Works with ANY shape without modification
public class AreaCalculator {
    public double calculateTotalArea(List<Shape> shapes) {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea(); // Polymorphism!
        }
        return totalArea;
    }
}

// Usage Example - Adding new shapes doesn't require changing AreaCalculator!
List<Shape> shapes = Arrays.asList(
    new Circle(5.0),
    new Rectangle(4.0, 6.0),
    new Triangle(3.0, 4.0)  // New shape - no changes to existing code!
);
AreaCalculator calculator = new AreaCalculator();
double total = calculator.calculateTotalArea(shapes);
```

**Benefits:**

- Can add new functionality without changing existing code
- Reduces risk of breaking existing features
- Promotes code reusability
- Extensible architecture

**Example Output:**

```

 OCP test passed - Added new shapes without modifying AreaCalculator!

```
---
### 3. Liskov Substitution Principle (LSP)

> [!definition]
> **"Objects of a superclass should be replaceable with objects of a subclass without breaking the application."**

**Implementation:**

- `Bird` base class: Defines common bird behaviors
- `Eagle`, `Sparrow`: Flying birds that implement `Flyable`
- `Penguin`: Non-flying bird that doesn't implement `Flyable`
- `BirdKeeper`: Works with any bird type correctly

**Code Example:**

```java
// Bird.java - Base class with behaviors ALL birds have
public abstract class Bird {
    protected String name;
    protected String species;
    
    public Bird(String name, String species) {
        this.name = name;
        this.species = species;
    }
    
    // All birds can eat and sleep
    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
    
    public abstract void makeSound();
    
    public String getName() { return name; }
}

// Flyable.java - Separate interface for flying behavior
public interface Flyable {
    void fly();
    int getMaxAltitude();
    double getFlyingSpeed();
}

// Eagle.java - Flying bird (implements Flyable)
public class Eagle extends Bird implements Flyable {
    public Eagle(String name) {
        super(name, "Eagle");
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " screeches: 'Screech!'");
    }
    
    @Override
    public void fly() {
        System.out.println(name + " soars majestically through the sky!");
    }
    
    @Override
    public int getMaxAltitude() {
        return 6000; // Eagles can fly up to 6000 meters
    }
    
    @Override
    public double getFlyingSpeed() {
        return 80.0; // Eagles fly at 80 km/h
    }
}

// Sparrow.java - Another flying bird (also implements Flyable)
public class Sparrow extends Bird implements Flyable {
    public Sparrow(String name) {
        super(name, "Sparrow");
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " chirps: 'Tweet tweet!'");
    }
    
    @Override
    public void fly() {
        System.out.println(name + " flutters quickly from branch to branch!");
    }
    
    @Override
    public int getMaxAltitude() {
        return 100; // Sparrows fly at lower altitudes
    }
    
    @Override
    public double getFlyingSpeed() {
        return 24.0; // Sparrows fly at about 24 km/h
    }
}

// Penguin.java - Non-flying bird (does NOT implement Flyable)
public class Penguin extends Bird {
    public Penguin(String name) {
        super(name, "Penguin");
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " calls: 'Honk honk!'");
    }
    
    // Penguins have their own special abilities
    public void swim() {
        System.out.println(name + " swims gracefully underwater!");
    }
    
    public void slide() {
        System.out.println(name + " slides on its belly across the ice!");
    }
}

// BirdKeeper.java - Works correctly with ALL birds
public class BirdKeeper {
    // All birds can be cared for (uses base Bird class)
    public void careForBird(Bird bird) {
        System.out.println("=== Caring for " + bird + " ===");
        bird.eat();
        bird.makeSound();
        bird.sleep();
    }
    
    // Only flying birds can perform flight (uses Flyable interface)
    public void makeBirdFly(Flyable flyableBird) {
        if (flyableBird instanceof Bird) {
            Bird bird = (Bird) flyableBird;
            System.out.println("=== Flight session for " + bird + " ===");
        }
        flyableBird.fly();
        System.out.println("Flying at " + flyableBird.getFlyingSpeed() + " km/h");
        System.out.println("Max altitude: " + flyableBird.getMaxAltitude() + " meters");
    }
    
    // Feeds all birds - works with mixed collection of Bird subclasses
    public void feedAllBirds(List<Bird> birds) {
        System.out.println("=== Feeding time for all birds ===");
        for (Bird bird : birds) {
            System.out.println("Feeding " + bird);
            bird.eat();
        }
        System.out.println("All birds have been fed!");
    }
}

// Usage Example - Perfect substitutability!
BirdKeeper keeper = new BirdKeeper();

// Create different bird instances
Bird eagle = new Eagle("Eddie");
Bird sparrow = new Sparrow("Sally");
Bird penguin = new Penguin("Penny");

// LSP in action: All birds can be cared for identically
keeper.careForBird(eagle);    // ✓ Works!
keeper.careForBird(sparrow);  // ✓ Works!
keeper.careForBird(penguin);  // ✓ Works!

// Feed all birds together (mixed collection)
List<Bird> allBirds = Arrays.asList(eagle, sparrow, penguin);
keeper.feedAllBirds(allBirds);  // ✓ All work perfectly!

// Only flying birds can fly (type safety)
if (eagle instanceof Flyable) {
    keeper.makeBirdFly((Flyable) eagle);    // ✓ Works! Eddie soars!
}
if (sparrow instanceof Flyable) {
    keeper.makeBirdFly((Flyable) sparrow);  // ✓ Works! Sally flutters!
}
// Penguin doesn't implement Flyable - no contract violation!
// This is CORRECT LSP implementation

```

**Benefits:**

- Ensures proper inheritance relationships
- Enables true polymorphism
- Prevents contract violations
- Subclasses can be used interchangeably with their base class

**Example Output:**

```

 LSP test passed - All Bird subclasses are perfectly substitutable!

```
---
### 4. Interface Segregation Principle (ISP)

> [!definition]
> "Clients should not be forced to depend on interfaces they do not use."

**Implementation:**

- Split large, "fat" interfaces into focused, role-specific ones
- Clients depend only on the capabilities they actually use
- Classes can implement exactly the interfaces that match their behavior

**Code Example:**

```java
// Focused interfaces instead of one fat Machine interface
public interface Printer {
    void print(String content);
}

public interface Scanner {
    String scan();
}

public interface Fax {
    void fax(String number, String content);
}

// Simple devices implement only what they support
public class SimplePrinter implements Printer {
    @Override
    public void print(String content) {
        System.out.println("Printing: " + content);
    }
}

public class SimpleScanner implements Scanner {
    @Override
    public String scan() {
        System.out.println("Scanning a document...");
        return "scanned-content";
    }
}

// Multifunction device implements multiple small interfaces
public class MultiFunctionPrinter implements Printer, Scanner, Fax {
    @Override
    public void print(String content) {
        System.out.println("MFP printing: " + content);
    }

    @Override
    public String scan() {
        System.out.println("MFP scanning...");
        return "mfp-scan";
    }

    @Override
    public void fax(String number, String content) {
        System.out.println("Faxing to " + number + ": " + content);
    }
}

// Client depends only on what it needs (Printer and Scanner here)
public class OfficeDesk {
    private final Printer printer;
    private final Scanner scanner;

    public OfficeDesk(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    public void copy(String title) {
        String scanned = scanner.scan();
        printer.print(title + " -> " + scanned);
    }
}

// Usage: compose capabilities without unused methods
Printer p = new SimplePrinter();
Scanner s = new SimpleScanner();
OfficeDesk desk = new OfficeDesk(p, s);
desk.copy("Report");
```

**Benefits:**

- Avoids empty or throwing method implementations
- Reduces coupling and increases flexibility
- Easier testing by mocking only the needed interfaces
- Clearer contracts per capability

**Example Output:**

```
Scanning a document...
Printing: Report -> scanned-content
```
---
### 5. Dependency Inversion Principle (DIP)

> [!definition]
> "High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions."

**Implementation:**

- Define stable abstractions (interfaces) for volatile details
- High-level policy depends on abstractions, not concrete classes
- Inject dependencies (constructor injection shown below)

**Code Example:**

```java
// Abstraction
public interface MessageSender {
    void send(String to, String message);
}

// Low-level details depend on the abstraction
public class EmailSender implements MessageSender {
    @Override
    public void send(String to, String message) {
        System.out.println("[EMAIL] to=" + to + ", msg=" + message);
    }
}

public class SmsSender implements MessageSender {
    @Override
    public void send(String to, String message) {
        System.out.println("[SMS] to=" + to + ", msg=" + message);
    }
}

// High-level module depends only on MessageSender abstraction
public class NotificationService {
    private final MessageSender sender;

    // Constructor injection
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String user, String message) {
        sender.send(user, message);
    }
}

// Usage: easily swap implementations without changing high-level logic
NotificationService emailNotif = new NotificationService(new EmailSender());
emailNotif.notifyUser("student@utm.md", "Exam tomorrow at 9:00");

NotificationService smsNotif = new NotificationService(new SmsSender());
smsNotif.notifyUser("+37360000000", "Room changed to 301");
```

**Benefits:**

- High-level business logic is isolated from infrastructure changes
- Easy to substitute or add new transports (Email, SMS, Push, etc.)
- Improved testability (inject fakes/mocks)
- Encourages clean architecture boundaries

**Example Output:**

```
[EMAIL] to=student@utm.md, msg=Exam tomorrow at 9:00
[SMS] to=+37360000000, msg=Room changed to 301
```
---




##  Test Results

The project includes comprehensive JUnit 5 tests that verify each principle:
### Test Coverage:

- **SRP Tests:** 4 test methods covering data, validation, storage, and integration
- **OCP Tests:** 5 test methods covering extensibility and polymorphism
- **LSP Tests:** 5 test methods covering substitution and behavioral compliance

### Expected Output:

```

 SRP test passed - Each class has a single, well-defined responsibility!

 OCP test passed - Added new shapes without modifying AreaCalculator!

 LSP test passed - All Bird subclasses are perfectly substitutable!

```

---

##  Quick observations

1. **SRP** helps create maintainable code by ensuring each class has one job
2. **OCP** allows extending functionality without breaking existing code
3. **LSP** ensures inheritance relationships are semantically correct
4. These principles work together to create robust, scalable software architectures

---
## Conclusion

This project serves as a practical introduction to SOLID principles for software engineering students, demonstrating:

- How to structure code following SOLID principles
- The benefits of proper object-oriented design
- How principles work together in real applications
- Best practices for Java development and testing

  

---
