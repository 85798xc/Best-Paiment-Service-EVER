package org.example.paymentderviceaplicationii.model.enums;

public enum PaymentProvider {
    STRIPE, PAYPAL;

    public static PaymentProvider fromString(String paymentProvider) {
        for(PaymentProvider paymentProviderEnum : PaymentProvider.values()) {
            if(paymentProviderEnum.toString().equalsIgnoreCase(paymentProvider)) {
                return paymentProviderEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Payment Provider: " + paymentProvider);
    }
}
