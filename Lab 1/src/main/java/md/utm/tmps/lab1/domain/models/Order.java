package md.utm.tmps.lab1.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Order class representing a customer order
 */
public class Order {
    private final int orderId;
    private final List<Pizza> pizzas;
    private final List<Beverage> beverages;
    private final List<Meal> meals;
    
    public Order() {
        this.orderId = OrderIdGenerator.getInstance().generateOrderId();
        this.pizzas = new ArrayList<>();
        this.beverages = new ArrayList<>();
        this.meals = new ArrayList<>();
    }
    
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
    
    public void addBeverage(Beverage beverage) {
        beverages.add(beverage);
    }
    
    public void addMeal(Meal meal) {
        meals.add(meal);
    }
    
    public int getOrderId() { return orderId; }
    public List<Pizza> getPizzas() { return new ArrayList<>(pizzas); }
    public List<Beverage> getBeverages() { return new ArrayList<>(beverages); }
    public List<Meal> getMeals() { return new ArrayList<>(meals); }
    
    public double calculateTotal() {
        double total = 0.0;
        
        // Pizza base price
        total += pizzas.size() * 12.99;
        
        // Beverages
        for (Beverage beverage : beverages) {
            total += beverage.getPrice();
        }
        
        // Meals
        for (Meal meal : meals) {
            total += meal.getTotalPrice();
        }
        
        return total;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(String.format("Order #%d\n", orderId));
        sb.append("========================================\n");
        
        if (!pizzas.isEmpty()) {
            sb.append("PIZZAS:\n");
            for (Pizza pizza : pizzas) {
                sb.append("  - ").append(pizza).append("\n");
            }
        }
        
        if (!beverages.isEmpty()) {
            sb.append("BEVERAGES:\n");
            for (Beverage beverage : beverages) {
                sb.append("  - ").append(beverage).append("\n");
            }
        }
        
        if (!meals.isEmpty()) {
            sb.append("MEALS:\n");
            for (Meal meal : meals) {
                sb.append("  - ").append(meal.getMealType()).append(" Meal\n");
            }
        }
        
        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL: $%.2f\n", calculateTotal()));
        sb.append("========================================");
        
        return sb.toString();
    }
}
