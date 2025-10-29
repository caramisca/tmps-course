package md.utm.tmps.lab1.domain.factory;

import md.utm.tmps.lab1.domain.models.Meal;

/**
 * Abstract Factory Pattern Demonstration
 * 
 * PATTERN: Provides an interface for creating families of related or dependent objects
 * without specifying their concrete classes.
 * 
 * USE CASE: MealFactory creates complete meals (Pizza + Beverage) where the components
 * are designed to work together. Different factory implementations create different
 * meal types (Vegetarian, Meat Lovers, Italian, etc.) with appropriate combinations.
 */
public interface MealFactory {
    /**
     * Creates a complete meal with coordinated pizza and beverage
     * @return a Meal object
     */
    Meal createMeal();
    
    /**
     * Gets the name of this meal type
     * @return meal type name
     */
    String getMealType();
}
