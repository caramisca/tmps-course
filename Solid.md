Absolutely — here’s a clear and concise overview of **each SOLID principle**, with **simple Java examples** for each 👇

---

## 🧱 **S — Single Responsibility Principle (SRP)**

> A class should have only **one reason to change** — one responsibility.

**❌ Bad Example:**

```java
public class Report {
    public void generateReport() {
        // generate report
    }
    public void saveToFile(String filename) {
        // save to disk
    }
}
```

➡️ The class both **creates** and **saves** the report — two responsibilities.

**✅ Good Example:**

```java
public class Report {
    public void generateReport() {
        // generate report
    }
}

public class ReportSaver {
    public void saveToFile(Report report, String filename) {
        // save to disk
    }
}
```

➡️ Each class has **a single purpose** — one generates, one saves.

---

## 🧩 **O — Open/Closed Principle (OCP)**

> Classes should be **open for extension**, but **closed for modification**.

**❌ Bad Example:**

```java
public class AreaCalculator {
    public double calculate(Object shape) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius * c.radius;
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width * r.height;
        }
        return 0;
    }
}
```

➡️ Adding a new shape means **modifying** the class.

**✅ Good Example:**

```java
interface Shape {
    double area();
}

class Circle implements Shape {
    double radius;
    Circle(double r) { radius = r; }
    public double area() { return Math.PI * radius * radius; }
}

class Rectangle implements Shape {
    double width, height;
    Rectangle(double w, double h) { width = w; height = h; }
    public double area() { return width * height; }
}

class AreaCalculator {
    public double calculate(Shape shape) {
        return shape.area();
    }
}
```

➡️ You can add new shapes without touching `AreaCalculator`.

---

## 🧱 **L — Liskov Substitution Principle (LSP)**

> Subclasses should be substitutable for their base classes — the program should still work correctly.

**❌ Bad Example:**

```java
class Bird {
    public void fly() { System.out.println("Flying"); }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}
```

➡️ A `Penguin` **breaks expectations** of `Bird`.

**✅ Good Example:**

```java
abstract class Bird { }

class FlyingBird extends Bird {
    public void fly() { System.out.println("Flying"); }
}

class Penguin extends Bird {
    public void swim() { System.out.println("Swimming"); }
}
```

➡️ Each subclass respects the expected behavior.

---

## 🔌 **I — Interface Segregation Principle (ISP)**

> Clients should not be forced to depend on interfaces they don’t use.

**❌ Bad Example:**

```java
interface Worker {
    void work();
    void eat();
}

class Robot implements Worker {
    public void work() { System.out.println("Working..."); }
    public void eat() { /* robots don't eat */ }
}
```

➡️ `Robot` has a **useless** `eat()` method.

**✅ Good Example:**

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    public void work() { System.out.println("Working..."); }
    public void eat() { System.out.println("Eating..."); }
}

class Robot implements Workable {
    public void work() { System.out.println("Working..."); }
}
```

➡️ Interfaces are **segregated by purpose**.

---

## 🧠 **D — Dependency Inversion Principle (DIP)**

> High-level modules should not depend on low-level modules — both should depend on **abstractions**.

**❌ Bad Example:**

```java
class LightBulb {
    public void turnOn() { System.out.println("Light On"); }
    public void turnOff() { System.out.println("Light Off"); }
}

class Switch {
    private LightBulb bulb;
    public Switch(LightBulb b) { bulb = b; }
    public void operate() { bulb.turnOn(); }
}
```

➡️ `Switch` depends directly on a **concrete class**.

**✅ Good Example:**

```java
interface Switchable {
    void turnOn();
    void turnOff();
}

class LightBulb implements Switchable {
    public void turnOn() { System.out.println("Light On"); }
    public void turnOff() { System.out.println("Light Off"); }
}

class Switch {
    private Switchable device;
    public Switch(Switchable device) { this.device = device; }

    public void operate() {
        device.turnOn();
    }
}
```

➡️ `Switch` works with **any Switchable device**, not just bulbs.

---

Would you like me to put these 5 examples together into a **single small Java project structure (with tests)** to visualize how they work?
