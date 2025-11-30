package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete observer that sends email notifications
 * Part of the Observer Pattern
 */
public class EmailNotifier implements OrderObserver {
    private final String recipientEmail;

    public EmailNotifier(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void onOrderStatusChanged(Order order, String message) {
        System.out.println("  📧 Email Notification to " + recipientEmail + ":");
        System.out.println("     Subject: Order #" + order.getOrderId() + " Update");
        System.out.println("     Message: " + message);
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }
}
