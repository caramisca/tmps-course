package md.utm.tmps.lab2.domain.payment.external;

/**
 * External Cash Register System (Adaptee)
 * Represents the in-house cash payment system
 */
public class CashRegisterSystem {
    private double cashDrawer = 500.0; // Starting cash amount
    
    public boolean acceptCash(double amount, double cashReceived) {
        // Simulate cash payment processing
        System.out.println("  → Cash Register: Payment amount: $" + String.format("%.2f", amount));
        System.out.println("  → Cash Register: Cash received: $" + String.format("%.2f", cashReceived));
        
        if (cashReceived >= amount) {
            double change = cashReceived - amount;
            cashDrawer += amount;
            System.out.println("  → Cash Register: Change due: $" + String.format("%.2f", change));
            System.out.println("  → Cash Register: Transaction complete");
            return true;
        } else {
            System.out.println("  → Cash Register: Insufficient cash received");
            return false;
        }
    }
    
    public boolean verifyAmount(double amount) {
        return amount > 0;
    }
    
    public double getCashDrawerBalance() {
        return cashDrawer;
    }
}
