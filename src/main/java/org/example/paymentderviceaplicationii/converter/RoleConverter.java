package org.example.paymentderviceaplicationii.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.paymentderviceaplicationii.model.enums.Role;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.toString();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Role.fromString(dbData);
    }
}
