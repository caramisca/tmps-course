package md.utm.tmps.lab1.domain.models;

/**
 * Singleton Pattern Demonstration
 * 
 * PATTERN: Ensures a class has only one instance and provides a global point of access to it.
 * 
 * USE CASE: OrderIdGenerator ensures all orders get unique IDs from a single source
 * This prevents ID conflicts and ensures proper order tracking across the system.
 */
public class OrderIdGenerator {
    // Single instance - volatile ensures visibility across threads
    private static volatile OrderIdGenerator instance;
    
    // Counter for generating unique IDs
    private int nextOrderId;
    
    // Private constructor prevents direct instantiation
    private OrderIdGenerator() {
        this.nextOrderId = 1000; // Start from 1000
    }
    
    /**
     * Double-checked locking for thread-safe lazy initialization
     * @return the single instance of OrderIdGenerator
     */
    public static OrderIdGenerator getInstance() {
        if (instance == null) {
            synchronized (OrderIdGenerator.class) {
                if (instance == null) {
                    instance = new OrderIdGenerator();
                }
            }
        }
        return instance;
    }
    
    /**
     * Generate next unique order ID
     * @return unique order ID
     */
    public synchronized int generateOrderId() {
        return nextOrderId++;
    }
    
    /**
     * Get the next order ID without incrementing (for testing)
     * @return next order ID that will be generated
     */
    public synchronized int peekNextOrderId() {
        return nextOrderId;
    }
    
    /**
     * Reset counter (useful for testing)
     */
    public synchronized void reset() {
        nextOrderId = 1000;
    }
}
