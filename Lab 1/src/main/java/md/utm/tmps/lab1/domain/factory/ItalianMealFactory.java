package md.utm.tmps.lab1.domain.factory;

import md.utm.tmps.lab1.domain.models.Meal;
import md.utm.tmps.lab1.domain.models.Pizza;
import md.utm.tmps.lab1.domain.models.Beverage;

/**
 * Concrete factory for creating Italian Classic meals
 */
public class ItalianMealFactory implements MealFactory {
    
    @Override
    public Meal createMeal() {
        // Create a classic Italian pizza
        Pizza pizza = new Pizza.Builder("Medium", "Regular")
                .cheese(true)
                .mushrooms(true)
                .olives(true)
                .addExtraTopping("Fresh Basil")
                .addExtraTopping("Mozzarella")
                .build();
        
        // Create an Italian beverage
        Beverage beverage = BeverageFactory.createCoffee("Small", "Cappuccino");
        
        return new Meal(pizza, beverage, "Italian Classic");
    }
    
    @Override
    public String getMealType() {
        return "Italian Classic";
    }
}
