package com.kaminsky.marketshipping.service;

import com.kaminsky.entity.MarketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingProducerService {
    private final KafkaTemplate<String, MarketOrder> kafkaTemplate;

    @Autowired
    public ShippingProducerService(KafkaTemplate<String, MarketOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, MarketOrder order)
    {
        kafkaTemplate.send(topic, order);
    }
}