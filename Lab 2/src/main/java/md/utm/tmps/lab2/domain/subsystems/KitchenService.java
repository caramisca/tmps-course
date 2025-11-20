package md.utm.tmps.lab2.domain.subsystems;

import md.utm.tmps.lab2.domain.models.PizzaComponent;

/**
 * Subsystem for managing kitchen operations
 */
public class KitchenService {
    
    public void preparePizza(PizzaComponent pizza) {
        System.out.println("  → Kitchen: Starting pizza preparation");
        pizza.prepare();
        System.out.println("  → Kitchen: Pizza ready!");
    }
    
    public void prepareOrder(int orderId) {
        System.out.println("  → Kitchen: Starting preparation for Order #" + orderId);
    }
    
    public void completeOrder(int orderId) {
        System.out.println("  → Kitchen: Order #" + orderId + " completed and ready for pickup");
    }
}
