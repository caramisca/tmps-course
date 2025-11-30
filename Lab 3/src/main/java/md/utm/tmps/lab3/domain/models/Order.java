package md.utm.tmps.lab3.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Order entity that holds components and order information
 */
public class Order {
    private final int orderId;
    private final String customerEmail;
    private final List<Component> components;
    private OrderStatus status;
    private double totalPrice;
    private double shippingCost;

    public Order(int orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.components = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.totalPrice = 0.0;
        this.shippingCost = 0.0;
    }

    public void addComponent(Component component) {
        components.add(component);
        calculateTotal();
    }

    public void addComponents(List<Component> components) {
        this.components.addAll(components);
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = components.stream()
            .mapToDouble(Component::getPrice)
            .sum();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public double getFinalPrice() {
        return totalPrice + shippingCost;
    }

    public int getComponentCount() {
        return components.size();
    }

    public double getTotalWeight() {
        // Approximate weight calculation: 0.5 kg per component
        return components.size() * 0.5;
    }

    @Override
    public String toString() {
        return String.format("Order #%d [%s] - %d items - $%.2f (+ $%.2f shipping)", 
            orderId, status, components.size(), totalPrice, shippingCost);
    }
}
