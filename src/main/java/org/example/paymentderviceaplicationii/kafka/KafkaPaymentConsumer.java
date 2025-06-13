package org.example.paymentderviceaplicationii.kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "payment-group")
    public void listen(@Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        String output;
        switch (partition) {
            case 0 -> output = "Payment Transaction is successful";
            case 1 -> output = "Payment Transaction is still processing";
            case 2 -> output = "Payment Transaction is failed";
            default -> output = "Unknown payment transaction";
        }
        System.out.println(output);
    }
}
