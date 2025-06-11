package org.example.paymentderviceaplicationii.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User not found " + message);
    }
}
