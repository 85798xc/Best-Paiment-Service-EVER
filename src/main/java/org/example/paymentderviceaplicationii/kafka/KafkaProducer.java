package org.example.paymentderviceaplicationii.kafka;

import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.example.paymentderviceaplicationii.model.PaymentTransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, PaymentTransactionDTO> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;


    public KafkaProducer(KafkaTemplate<String, PaymentTransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PaymentTransactionDTO transaction) {
        kafkaTemplate.send(topic, transaction);
    }
}
