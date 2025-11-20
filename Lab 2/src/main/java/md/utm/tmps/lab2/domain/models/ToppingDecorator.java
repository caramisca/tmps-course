package md.utm.tmps.lab2.domain.models;

/**
 * Abstract Decorator class for Pizza toppings
 * 
 * DECORATOR PATTERN:
 * Allows behavior to be added to individual objects dynamically without affecting
 * the behavior of other objects from the same class.
 * 
 * USE CASE: Dynamically add toppings to pizzas with flexible pricing
 */
public abstract class ToppingDecorator implements PizzaComponent {
    protected PizzaComponent pizza;
    
    public ToppingDecorator(PizzaComponent pizza) {
        this.pizza = pizza;
    }
    
    @Override
    public String getDescription() {
        return pizza.getDescription();
    }
    
    @Override
    public double getCost() {
        return pizza.getCost();
    }
    
    @Override
    public void prepare() {
        pizza.prepare();
    }
}
