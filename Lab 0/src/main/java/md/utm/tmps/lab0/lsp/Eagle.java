package md.utm.tmps.lab0.lsp;

/**
 * Eagle - a bird that can fly
 * This class can be substituted for Bird without breaking LSP
 * It also implements Flyable because eagles can fly
 */
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
        return 80.0; // Eagles can fly at 80 km/h
    }
    
    /**
     * Eagles have a special hunting behavior
     */
    public void hunt() {
        System.out.println(name + " is hunting for prey from above.");
    }
}