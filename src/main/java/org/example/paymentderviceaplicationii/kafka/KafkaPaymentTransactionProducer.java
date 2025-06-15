package org.example.paymentderviceaplicationii.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPaymentTransactionProducer {
    @Value("${spring.kafka.topics[0].topic-name}")
    private String paymentTransactionResultTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String paymentTransaction) {
        log.info("Sending kafka message");

        kafkaTemplate.send(paymentTransactionResultTopic, paymentTransaction);
    }
}
