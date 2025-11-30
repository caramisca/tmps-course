package md.utm.tmps.lab3.domain.chain;

/**
 * Factory class to build a validation chain
 * Part of the Chain of Responsibility Pattern
 */
public class ValidationChainBuilder {
    
    /**
     * Build a standard validation chain for orders
     * @return The first validator in the chain
     */
    public static OrderValidator buildStandardChain() {
        OrderValidator emptyCheck = new EmptyOrderValidator();
        OrderValidator emailCheck = new EmailValidator();
        OrderValidator minimumCheck = new MinimumOrderValidator();
        OrderValidator inventoryCheck = new InventoryValidator();
        
        // Chain them together
        emptyCheck.setNext(emailCheck)
                  .setNext(minimumCheck)
                  .setNext(inventoryCheck);
        
        return emptyCheck;
    }
    
    /**
     * Build a custom validation chain
     * @param validators The validators to chain in order
     * @return The first validator in the chain
     */
    public static OrderValidator buildCustomChain(OrderValidator... validators) {
        if (validators.length == 0) {
            throw new IllegalArgumentException("At least one validator is required");
        }
        
        for (int i = 0; i < validators.length - 1; i++) {
            validators[i].setNext(validators[i + 1]);
        }
        
        return validators[0];
    }
}
