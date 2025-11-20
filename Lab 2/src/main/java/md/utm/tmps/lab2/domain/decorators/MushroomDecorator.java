package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Mushroom topping
 */
public class MushroomDecorator extends ToppingDecorator {
    
    public MushroomDecorator(PizzaComponent pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", mushrooms";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 1.25;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding fresh mushrooms (+$1.25)");
    }
}
