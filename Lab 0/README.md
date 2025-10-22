
**Course:** Tehnici si Metode de Proiectare Software  
**Student:** Caraman Mihai
**Group:** FAF 233
**Date:** October 2025

##  Project Overview

This project demonstrates the first three SOLID principles through practical Java examples:

1. **Single Responsibility Principle (SRP)**
2. **Open/Closed Principle (OCP)**
3. **Liskov Substitution Principle (LSP)**

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

**Benefits:**

- Easy to maintain and modify
- Clear separation of concerns
- Reduced coupling between functionalities

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

**Benefits:**

- Can add new functionality without changing existing code
- Reduces risk of breaking existing features
- Promotes code reusability

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

**Benefits:**

- Ensures proper inheritance relationships
- Enables true polymorphism
- Prevents contract violations

**Example Output:**

```

 LSP test passed - All Bird subclasses are perfectly substitutable!

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