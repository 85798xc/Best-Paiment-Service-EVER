package org.example.paymentderviceaplicationii.kafka;

import lombok.Data;

@Data
public class KafkaTopic {
    private String topicName;

    private int partitions;

    private short replicationFactor;
}
