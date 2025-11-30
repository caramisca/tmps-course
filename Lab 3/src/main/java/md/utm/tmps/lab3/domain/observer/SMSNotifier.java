package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;

/**
 * Concrete observer that sends SMS notifications
 * Part of the Observer Pattern
 */
public class SMSNotifier implements OrderObserver {
    private final String phoneNumber;

    public SMSNotifier(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onOrderStatusChanged(Order order, String message) {
        System.out.println("  📱 SMS Notification to " + phoneNumber + ":");
        System.out.println("     " + message);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
