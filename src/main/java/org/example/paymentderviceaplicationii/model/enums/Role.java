package org.example.paymentderviceaplicationii.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER, ADMIN;

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.toString().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid Role: " + role);
    }
}
