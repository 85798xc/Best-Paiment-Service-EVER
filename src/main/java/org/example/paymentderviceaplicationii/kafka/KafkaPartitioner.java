package org.example.paymentderviceaplicationii.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.example.paymentderviceaplicationii.model.PaymentTransactionDTO;

import java.util.Map;

public class KafkaPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes,
                         Object value, byte[] valueBytes, Cluster cluster) {

        PaymentTransactionDTO transaction = (PaymentTransactionDTO) value;

        return switch (transaction.getStatus()){
            case SUCCESS -> 0;
            case PROCESSING -> 1;
            case FAILED -> 2;
        };
    }

    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> map) {}
}
