package md.utm.tmps.lab3.domain.chain;

/**
 * Result of a validation check
 * Part of the Chain of Responsibility Pattern
 */
public class ValidationResult {
    private final boolean isValid;
    private final String message;

    private ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public static ValidationResult success(String message) {
        return new ValidationResult(true, message);
    }

    public static ValidationResult failure(String message) {
        return new ValidationResult(false, message);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return (isValid ? " " : "✗ ") + message;
    }
}
