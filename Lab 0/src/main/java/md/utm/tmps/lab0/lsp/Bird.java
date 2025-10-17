package md.utm.tmps.lab0.lsp;

/**
 * Liskov Substitution Principle (LSP) Demonstration
 * 
 * PRINCIPLE: Objects of a superclass should be replaceable with objects 
 * of a subclass without breaking the application.
 * 
 * Subclasses should be substitutable for their base classes without 
 * altering the correctness of the program.
 * 
 * EXAMPLE: Bird hierarchy where all birds can fly vs. birds that cannot fly
 * We'll show the CORRECT way to implement this.
 */

/**
 * Base Bird class with common bird behaviors
 * This class defines what ALL birds can do without exception
 */
public abstract class Bird {
    protected String name;
    protected String species;
    
    public Bird(String name, String species) {
        this.name = name;
        this.species = species;
    }
    
    /**
     * All birds can eat - this is safe for LSP
     */
    public void eat() {
        System.out.println(name + " is eating.");
    }
    
    /**
     * All birds can sleep - this is safe for LSP
     */
    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
    
    /**
     * All birds can make sounds - this is safe for LSP
     */
    public abstract void makeSound();
    
    // Getters
    public String getName() { return name; }
    public String getSpecies() { return species; }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", name, species);
    }
}