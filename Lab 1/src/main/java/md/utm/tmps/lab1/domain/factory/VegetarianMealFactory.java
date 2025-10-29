package md.utm.tmps.lab1.domain.factory;

import md.utm.tmps.lab1.domain.models.Meal;
import md.utm.tmps.lab1.domain.models.Pizza;
import md.utm.tmps.lab1.domain.models.Beverage;

/**
 * Concrete factory for creating Vegetarian meals
 */
public class VegetarianMealFactory implements MealFactory {
    
    @Override
    public Meal createMeal() {
        // Create a vegetarian pizza
        Pizza pizza = new Pizza.Builder("Medium", "Thin")
                .cheese(true)
                .mushrooms(true)
                .olives(true)
                .onions(true)
                .addExtraTopping("Tomatoes")
                .addExtraTopping("Bell Peppers")
                .build();
        
        // Create a healthy beverage
        Beverage beverage = BeverageFactory.createJuice("Medium", "Orange");
        
        return new Meal(pizza, beverage, "Vegetarian");
    }
    
    @Override
    public String getMealType() {
        return "Vegetarian";
    }
}
