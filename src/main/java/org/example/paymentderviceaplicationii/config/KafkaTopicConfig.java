package org.example.paymentderviceaplicationii.config;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.paymentderviceaplicationii.kafka.KafkaTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaTopicConfig {
    private List<KafkaTopic> topics;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();

        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("offsets.topic.replication.factor", "1");
        props.put("transaction.state.log.replication.factor", "1");
        props.put("transaction.state.log.min.isr", "1");

        return new KafkaAdmin(props);
    }

    @Bean
    public List<NewTopic> createTopics() {
        return topics.stream()
                .map(topic -> new NewTopic(
                        topic.getTopicName(),
                        topic.getPartitions(),
                        topic.getReplicationFactor())
                ).toList();
    }
}
