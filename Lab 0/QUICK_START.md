# Lab 0 - SOLID Principles Implementation Guide

## 🎯 Quick Start

### Option 1: PowerShell (Recommended)
```powershell
.\run.ps1
```

### Option 2: Command Prompt
```cmd
run.bat
```

### Option 3: Manual Commands
```powershell
# Compile
javac -encoding UTF-8 -d classes -cp classes src/main/java/md/utm/tmps/lab0/*.java src/main/java/md/utm/tmps/lab0/srp/*.java src/main/java/md/utm/tmps/lab0/ocp/*.java src/main/java/md/utm/tmps/lab0/lsp/*.java

# Run demonstration
java -cp classes md.utm.tmps.lab0.Main

# Run tests
java -cp classes md.utm.tmps.lab0.SimpleTestRunner
```

### Option 4: VS Code (if Java Extension Pack is installed)
1. Press `Ctrl+Shift+P`
2. Type "Tasks: Run Task"
3. Select "Run Main Demo" or "Run Tests"

## 📋 What This Lab Demonstrates

### ✅ Single Responsibility Principle (SRP)
- **User.java**: Only handles user data
- **UserValidator.java**: Only handles validation logic  
- **UserRepository.java**: Only handles data storage
- **Benefit**: Easy to maintain, modify, and test each responsibility independently

### ✅ Open/Closed Principle (OCP)
- **Shape.java**: Abstract base class (closed for modification)
- **Rectangle.java, Circle.java, Triangle.java**: Extensions (open for extension)
- **AreaCalculator.java**: Works with any shape without modification
- **Benefit**: Add new shapes without changing existing code

### ✅ Liskov Substitution Principle (LSP)
- **Bird.java**: Base class with common behaviors
- **Eagle.java, Sparrow.java**: Flying birds implementing Flyable
- **Penguin.java**: Non-flying bird (correctly doesn't implement Flyable)
- **BirdKeeper.java**: Works with any Bird subclass seamlessly
- **Benefit**: True polymorphism and correct inheritance relationships

## 🧪 Test Results
- **25/25 tests passed**
- **100% success rate**
- **All SOLID principles working correctly**

## 📚 Educational Value
This lab provides hands-on experience with:
- Proper object-oriented design principles
- Clean code architecture
- Java best practices
- Test-driven thinking
- Real-world software design patterns

## 🎓 Course Integration
Perfect for "Tehnici si Metode de Proiectare Software" coursework:
- Practical SOLID principles implementation
- Clear separation of concerns
- Educational code comments
- Comprehensive testing
- Professional project structure