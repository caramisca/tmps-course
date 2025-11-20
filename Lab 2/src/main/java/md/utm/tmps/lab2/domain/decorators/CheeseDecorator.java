package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Cheese topping
 */
public class CheeseDecorator extends ToppingDecorator {
    private final String cheeseType;
    
    public CheeseDecorator(PizzaComponent pizza, String cheeseType) {
        super(pizza);
        this.cheeseType = cheeseType;
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", " + cheeseType + " cheese";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 1.50;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding " + cheeseType + " cheese (+$1.50)");
    }
}
