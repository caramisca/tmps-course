package md.utm.tmps.lab0.lsp;

/**
 * Interface for birds that can fly
 * This follows LSP by separating flying behavior from the base Bird class
 * Not all birds can fly, so we create a separate contract for flying birds
 */
public interface Flyable {
    /**
     * Makes the bird fly
     */
    void fly();
    
    /**
     * Gets the maximum flying altitude for this bird
     * @return altitude in meters
     */
    int getMaxAltitude();
    
    /**
     * Gets the flying speed of this bird
     * @return speed in km/h
     */
    double getFlyingSpeed();
}