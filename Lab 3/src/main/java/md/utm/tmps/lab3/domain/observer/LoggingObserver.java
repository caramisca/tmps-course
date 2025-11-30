package md.utm.tmps.lab3.domain.observer;

import md.utm.tmps.lab3.domain.models.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Concrete observer that logs order changes to console
 * Part of the Observer Pattern
 */
public class LoggingObserver implements OrderObserver {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onOrderStatusChanged(Order order, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("  📝 [LOG " + timestamp + "] " + message);
    }
}
