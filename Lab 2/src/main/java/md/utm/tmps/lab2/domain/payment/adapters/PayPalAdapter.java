package md.utm.tmps.lab2.domain.payment.adapters;

import md.utm.tmps.lab2.domain.payment.PaymentProcessor;
import md.utm.tmps.lab2.domain.payment.external.PayPalAPI;

/**
 * Adapter for PayPal payment system
 * Adapts PayPalAPI to work with our PaymentProcessor interface
 */
public class PayPalAdapter implements PaymentProcessor {
    private final PayPalAPI payPalAPI;
    
    public PayPalAdapter() {
        this.payPalAPI = new PayPalAPI();
    }
    
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        // Adapt our interface to PayPal's interface
        System.out.println("PayPal Adapter: Converting request for " + payPalAPI.getServiceName());
        return payPalAPI.sendPayment(accountInfo, amount);
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
    
    @Override
    public boolean validatePaymentDetails(String accountInfo) {
        return payPalAPI.verifyEmail(accountInfo);
    }
}
