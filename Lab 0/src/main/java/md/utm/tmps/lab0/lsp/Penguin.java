package md.utm.tmps.lab0.lsp;

/**
 * Penguin - a bird that CANNOT fly
 * This class can be substituted for Bird without breaking LSP
 * It does NOT implement Flyable because penguins cannot fly
 * This is the CORRECT way to handle non-flying birds
 */
public class Penguin extends Bird {
    
    public Penguin(String name) {
        super(name, "Penguin");
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " calls: 'Honk honk!'");
    }
    
    /**
     * Penguins can swim instead of fly
     */
    public void swim() {
        System.out.println(name + " swims gracefully underwater!");
    }
    
    /**
     * Penguins have special sliding behavior
     */
    public void slide() {
        System.out.println(name + " slides on its belly across the ice!");
    }
    
    /**
     * Gets swimming speed
     * @return speed in km/h
     */
    public double getSwimmingSpeed() {
        return 8.0; // Penguins can swim at 8 km/h
    }
}