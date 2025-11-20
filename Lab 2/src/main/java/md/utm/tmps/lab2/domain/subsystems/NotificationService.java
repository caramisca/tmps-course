package md.utm.tmps.lab2.domain.subsystems;

/**
 * Subsystem for customer notifications
 */
public class NotificationService {
    
    public void sendOrderConfirmation(int orderId, String customerContact) {
        System.out.println("  → Notification: Order confirmation sent to " + customerContact);
        System.out.println("    'Your order #" + orderId + " has been received and is being prepared'");
    }
    
    public void sendOrderReady(int orderId, String customerContact) {
        System.out.println("  → Notification: Ready notification sent to " + customerContact);
        System.out.println("    'Your order #" + orderId + " is ready for pickup!'");
    }
    
    public void sendPaymentConfirmation(int orderId, double amount, String paymentMethod) {
        System.out.println("  → Notification: Payment confirmation sent");
        System.out.println("    'Payment of $" + String.format("%.2f", amount) + 
                          " via " + paymentMethod + " was successful'");
    }
}
