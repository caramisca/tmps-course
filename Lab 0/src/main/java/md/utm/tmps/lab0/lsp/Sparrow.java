package md.utm.tmps.lab0.lsp;

/**
 * Sparrow - a bird that can fly
 * This class can be substituted for Bird without breaking LSP
 * It also implements Flyable because sparrows can fly
 */
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
        return 100; // Sparrows typically fly at lower altitudes
    }
    
    @Override
    public double getFlyingSpeed() {
        return 24.0; // Sparrows fly at about 24 km/h
    }
    
    /**
     * Sparrows have special social behavior
     */
    public void flock() {
        System.out.println(name + " joins a flock of sparrows.");
    }
}