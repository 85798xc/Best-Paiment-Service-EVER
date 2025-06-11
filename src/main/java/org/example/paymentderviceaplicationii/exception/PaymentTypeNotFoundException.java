package org.example.paymentderviceaplicationii.exception;

public class PaymentTypeNotFoundException extends RuntimeException {
    public PaymentTypeNotFoundException() {
        super("Unsupported paymentType");
    }
}
