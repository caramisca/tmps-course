package md.utm.tmps.lab1.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizza class - represents a complex object that will be built using Builder pattern
 */
public class Pizza {
    // Required parameters
    private final String size;
    private final String crustType;
    
    // Optional parameters
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;
    private final boolean olives;
    private final boolean bacon;
    private final boolean onions;
    private final List<String> extraToppings;
    
    // Private constructor - only Builder can create Pizza instances
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.crustType = builder.crustType;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
        this.olives = builder.olives;
        this.bacon = builder.bacon;
        this.onions = builder.onions;
        this.extraToppings = new ArrayList<>(builder.extraToppings);
    }
    
    // Getters
    public String getSize() { return size; }
    public String getCrustType() { return crustType; }
    public boolean hasCheese() { return cheese; }
    public boolean hasPepperoni() { return pepperoni; }
    public boolean hasMushrooms() { return mushrooms; }
    public boolean hasOlives() { return olives; }
    public boolean hasBacon() { return bacon; }
    public boolean hasOnions() { return onions; }
    public List<String> getExtraToppings() { return new ArrayList<>(extraToppings); }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pizza [").append(size).append(", ").append(crustType).append(" crust] - Toppings: ");
        
        List<String> toppings = new ArrayList<>();
        if (cheese) toppings.add("Cheese");
        if (pepperoni) toppings.add("Pepperoni");
        if (mushrooms) toppings.add("Mushrooms");
        if (olives) toppings.add("Olives");
        if (bacon) toppings.add("Bacon");
        if (onions) toppings.add("Onions");
        toppings.addAll(extraToppings);
        
        if (toppings.isEmpty()) {
            sb.append("None");
        } else {
            sb.append(String.join(", ", toppings));
        }
        
        return sb.toString();
    }
    
    /**
     * Builder Pattern Implementation
     * 
     * PATTERN: Separates the construction of a complex object from its representation
     * allowing the same construction process to create different representations.
     * 
     * USE CASE: Pizza has many optional toppings. Builder makes it easy to create
     * customized pizzas without constructor pollution or telescoping constructors.
     */
    public static class Builder {
        // Required parameters
        private final String size;
        private final String crustType;
        
        // Optional parameters - initialized to default values
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushrooms = false;
        private boolean olives = false;
        private boolean bacon = false;
        private boolean onions = false;
        private List<String> extraToppings = new ArrayList<>();
        
        /**
         * Constructor with required parameters
         */
        public Builder(String size, String crustType) {
            this.size = size;
            this.crustType = crustType;
        }
        
        public Builder cheese(boolean value) {
            this.cheese = value;
            return this;
        }
        
        public Builder pepperoni(boolean value) {
            this.pepperoni = value;
            return this;
        }
        
        public Builder mushrooms(boolean value) {
            this.mushrooms = value;
            return this;
        }
        
        public Builder olives(boolean value) {
            this.olives = value;
            return this;
        }
        
        public Builder bacon(boolean value) {
            this.bacon = value;
            return this;
        }
        
        public Builder onions(boolean value) {
            this.onions = value;
            return this;
        }
        
        public Builder addExtraTopping(String topping) {
            this.extraToppings.add(topping);
            return this;
        }
        
        /**
         * Build the Pizza instance
         */
        public Pizza build() {
            return new Pizza(this);
        }
    }
}
