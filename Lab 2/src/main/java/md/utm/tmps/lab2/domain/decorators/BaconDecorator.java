package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Bacon topping
 */
public class BaconDecorator extends ToppingDecorator {
    
    public BaconDecorator(PizzaComponent pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", crispy bacon";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 2.50;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding crispy bacon strips (+$2.50)");
    }
}
