package org.example.paymentderviceaplicationii.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.paymentderviceaplicationii.model.PaymentTransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, PaymentTransactionDTO> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PaymentTransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void send(PaymentTransactionDTO transaction) {
        log.info("Sending transaction: {}", transaction);
        kafkaTemplate.send(topic, transaction.getStatus().name(), transaction);
    }
}
