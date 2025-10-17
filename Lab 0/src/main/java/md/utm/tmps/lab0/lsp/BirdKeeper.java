package md.utm.tmps.lab0.lsp;

import java.util.List;

/**
 * BirdKeeper class demonstrates LSP
 * This class can work with ANY Bird subclass without knowing the specific type
 * It can also work with Flyable birds when flying operations are needed
 */
public class BirdKeeper {
    
    /**
     * Takes care of any bird - demonstrates LSP
     * This method works with ANY Bird subclass without modification
     * @param bird any bird instance
     */
    public void careForBird(Bird bird) {
        System.out.println("=== Caring for " + bird + " ===");
        bird.eat();
        bird.makeSound();
        bird.sleep();
    }
    
    /**
     * Makes flying birds perform flight - demonstrates LSP with interfaces
     * This method works with ANY Flyable implementation
     * @param flyableBird any bird that implements Flyable
     */
    public void makeBirdFly(Flyable flyableBird) {
        if (flyableBird instanceof Bird) {
            Bird bird = (Bird) flyableBird;
            System.out.println("=== Flight session for " + bird + " ===");
        }
        flyableBird.fly();
        System.out.println("Flying at " + flyableBird.getFlyingSpeed() + " km/h");
        System.out.println("Max altitude: " + flyableBird.getMaxAltitude() + " meters");
    }
    
    /**
     * Feeds all birds in the collection - demonstrates LSP with collections
     * This method works with a mixed collection of different Bird subclasses
     * @param birds list of any bird types
     */
    public void feedAllBirds(List<Bird> birds) {
        System.out.println("=== Feeding time for all birds ===");
        for (Bird bird : birds) {
            System.out.println("Feeding " + bird);
            bird.eat();
        }
        System.out.println("All birds have been fed!");
    }
    
    /**
     * Organizes flight training for all flying birds
     * @param flyableBirds list of birds that can fly
     */
    public void organizeFlightTraining(List<Flyable> flyableBirds) {
        System.out.println("=== Flight Training Session ===");
        for (Flyable flyableBird : flyableBirds) {
            flyableBird.fly();
        }
        System.out.println("Flight training completed!");
    }
}