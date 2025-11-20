package md.utm.tmps.lab2.domain.payment.adapters;

import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.payment.external.CashRegisterSystem;

/**
 * Adapter for Cash payment system
 * Adapts CashRegisterSystem to work with our PaymentProcessor interface
 */
public class CashAdapter implements PaymentProcessor {
    private final CashRegisterSystem cashRegister;
    
    public CashAdapter() {
        this.cashRegister = new CashRegisterSystem();
    }
    
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        // accountInfo contains the cash received as a string
        System.out.println("Cash Adapter: Converting cash payment request");
        try {
            double cashReceived = Double.parseDouble(accountInfo);
            return cashRegister.acceptCash(amount, cashReceived);
        } catch (NumberFormatException e) {
            System.out.println("  → Cash Adapter: Invalid cash amount format");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
    
    @Override
    public boolean validatePaymentDetails(String accountInfo) {
        try {
            double amount = Double.parseDouble(accountInfo);
            return cashRegister.verifyAmount(amount);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public double getCashDrawerBalance() {
        return cashRegister.getCashDrawerBalance();
    }
}
