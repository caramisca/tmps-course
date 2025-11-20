package md.utm.tmps.lab2.domain.subsystems;

/**
 * Subsystem for managing order IDs
 */
public class OrderIdGenerator {
    private static OrderIdGenerator instance;
    private int currentId;
    
    private OrderIdGenerator() {
        this.currentId = 1000;
    }
    
    public static synchronized OrderIdGenerator getInstance() {
        if (instance == null) {
            instance = new OrderIdGenerator();
        }
        return instance;
    }
    
    public synchronized int generateOrderId() {
        return currentId++;
    }
}
