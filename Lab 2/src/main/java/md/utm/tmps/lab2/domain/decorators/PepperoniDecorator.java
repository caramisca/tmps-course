package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Pepperoni topping
 */
public class PepperoniDecorator extends ToppingDecorator {
    
    public PepperoniDecorator(PizzaComponent pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", pepperoni";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 2.00;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding pepperoni slices (+$2.00)");
    }
}
