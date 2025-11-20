package md.utm.tmps.lab2.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Order class representing a customer order
 */
public class Order {
    private final int orderId;
    private final List<PizzaComponent> pizzas;
    private double totalAmount;
    private OrderStatus status;
    
    public enum OrderStatus {
        PENDING, PREPARING, READY, PAID, COMPLETED, CANCELLED
    }
    
    public Order(int orderId) {
        this.orderId = orderId;
        this.pizzas = new ArrayList<>();
        this.totalAmount = 0.0;
        this.status = OrderStatus.PENDING;
    }
    
    public void addPizza(PizzaComponent pizza) {
        pizzas.add(pizza);
        calculateTotal();
    }
    
    public void calculateTotal() {
        totalAmount = 0.0;
        for (PizzaComponent pizza : pizzas) {
            totalAmount += pizza.getCost();
        }
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public List<PizzaComponent> getPizzas() {
        return new ArrayList<>(pizzas);
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ Order #%-5d                                      Status: %s ║\n", 
                                orderId, status.toString().substring(0, Math.min(4, status.toString().length()))));
        sb.append("╠════════════════════════════════════════════════════════════╣\n");
        
        int pizzaNum = 1;
        for (PizzaComponent pizza : pizzas) {
            sb.append(String.format("║ Pizza %d:                                                   ║\n", pizzaNum++));
            sb.append(String.format("║   %s\n", pizza.getDescription()));
            // Pad to 60 chars and add border
            String costLine = String.format("   Cost: $%.2f", pizza.getCost());
            sb.append(String.format("║ %-58s ║\n", costLine));
        }
        
        sb.append("╠════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ TOTAL: $%-50.2f ║\n", totalAmount));
        sb.append("╚════════════════════════════════════════════════════════════╝");
        
        return sb.toString();
    }
}
