package com.kaminsky.marketshipping.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Component
public class PartitionConfiguration {
    private final Logger log = LoggerFactory.getLogger(PartitionConfiguration.class);

    @PostConstruct
    public void configureTopics() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");

        try (AdminClient adminClient = AdminClient.create(properties)) {
            NewTopic newOrdersTopic = new NewTopic("new_orders", 1, (short) 1);
            NewTopic payedOrdersTopic = new NewTopic("payed_orders", 1, (short) 1);
            NewTopic sentOrdersTopic = new NewTopic("sent_orders", 1, (short) 1);

            adminClient.createTopics(Arrays.asList(newOrdersTopic, payedOrdersTopic, sentOrdersTopic)).all().get();
            log.info("Топики созданы");

            NewPartitions newPartitions = NewPartitions.increaseTo(3);
            adminClient.createPartitions(Map.of("sent_orders", newPartitions)).all().get();

            log.info("Увеличиваем количество партиций для sent_orders");
        } catch (Exception e) {
            log.error("Ошибка при увеличении кол-ва партиций {}", e);
            throw new RuntimeException(e);
        }
    }
}
