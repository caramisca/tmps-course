package md.utm.tmps.lab2.domain.models;

/**
 * Base component interface for Pizza in Decorator pattern
 * Represents the core abstraction that both concrete components and decorators implement
 */
public interface PizzaComponent {
    /**
     * Gets the description of the pizza with all toppings
     * @return complete description
     */
    String getDescription();
    
    /**
     * Calculates the total cost including all decorations
     * @return total price
     */
    double getCost();
    
    /**
     * Prepares the pizza - shows the preparation process
     */
    void prepare();
}
