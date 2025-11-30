package md.utm.tmps.lab3.domain.models;

/**
 * Enum representing the status of an order
 */
public enum OrderStatus {
    PENDING,
    VALIDATED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
