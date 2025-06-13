package org.example.paymentderviceaplicationii;

import jakarta.persistence.EntityNotFoundException;
import org.example.paymentderviceaplicationii.converter.ToDtoConverter;
import org.example.paymentderviceaplicationii.kafka.KafkaProducer;
import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.example.paymentderviceaplicationii.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class implTest implements CommandLineRunner {
    @Autowired
    PaymentTransactionRepository repository;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    ToDtoConverter toDtoConverter;


    @Override
    public void run(String... args) throws Exception {

        for (PaymentTransaction paymentTransaction : repository.findAll()){
            System.out.println(paymentTransaction);

            kafkaProducer.send(toDtoConverter.TransactionToDto(paymentTransaction));
        }
    }
}
