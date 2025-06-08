package org.example.paymentderviceaplicationii.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    PROCESSING, SUCCESS, FAILED;

    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.toString().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + status);
    }
}
