package org.example.paymentderviceaplicationii.exception;

public class UserListNotFoundException extends RuntimeException {
    public UserListNotFoundException() {
        super("User list not found");
    }
}
