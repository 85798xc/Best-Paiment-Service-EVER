package org.example.paymentderviceaplicationii;

import lombok.AllArgsConstructor;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.example.paymentderviceaplicationii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentDerviceAplicationIiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentDerviceAplicationIiApplication.class, args);

        System.out.println("Application started");
    }

}
