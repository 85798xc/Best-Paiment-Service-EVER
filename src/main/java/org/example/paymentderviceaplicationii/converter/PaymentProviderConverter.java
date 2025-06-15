package org.example.paymentderviceaplicationii.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;

@Converter(autoApply = true)
public class PaymentProviderConverter implements AttributeConverter<PaymentProvider, String> {
    @Override
    public String convertToDatabaseColumn(PaymentProvider paymentProvider) {
        return paymentProvider == null ? null : paymentProvider.name();
    }

    @Override
    public PaymentProvider convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentProvider.fromString(dbData);
    }
}
