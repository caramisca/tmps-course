package md.utm.tmps.lab1.domain.factory;

import md.utm.tmps.lab1.domain.models.Meal;
import md.utm.tmps.lab1.domain.models.Pizza;
import md.utm.tmps.lab1.domain.models.Beverage;

/**
 * Concrete factory for creating Meat Lovers meals
 */
public class MeatLoversMealFactory implements MealFactory {
    
    @Override
    public Meal createMeal() {
        // Create a meat-heavy pizza
        Pizza pizza = new Pizza.Builder("Large", "Thick")
                .cheese(true)
                .pepperoni(true)
                .bacon(true)
                .addExtraTopping("Sausage")
                .addExtraTopping("Ham")
                .build();
        
        // Create a strong beverage
        Beverage beverage = BeverageFactory.createCoffee("Large", "Espresso");
        
        return new Meal(pizza, beverage, "Meat Lovers");
    }
    
    @Override
    public String getMealType() {
        return "Meat Lovers";
    }
}
