package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Vegetable topping
 */
public class VegetableDecorator extends ToppingDecorator {
    private final String vegetableType;
    
    public VegetableDecorator(PizzaComponent pizza, String vegetableType) {
        super(pizza);
        this.vegetableType = vegetableType;
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", " + vegetableType;
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 0.75;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding " + vegetableType + " (+$0.75)");
    }
}
