package org.example.paymentderviceaplicationii.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.paymentderviceaplicationii.model.enums.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.toString();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Status.fromString(dbData);
    }
}
