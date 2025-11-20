package md.utm.tmps.lab2.domain.decorators;

import md.utm.tmps.lab2.domain.models.PizzaComponent;
import md.utm.tmps.lab2.domain.models.ToppingDecorator;

/**
 * Concrete Decorator - Olive topping
 */
public class OliveDecorator extends ToppingDecorator {
    private final String oliveType;
    
    public OliveDecorator(PizzaComponent pizza, String oliveType) {
        super(pizza);
        this.oliveType = oliveType;
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription() + ", " + oliveType + " olives";
    }
    
    @Override
    public double getCost() {
        return pizza.getCost() + 1.00;
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
        System.out.println("  → Adding " + oliveType + " olives (+$1.00)");
    }
}
