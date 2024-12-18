package com.kaminsky.marketshipping.service;

import com.kaminsky.marketshipping.entity.PackedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingProducerService {
    private final KafkaTemplate<String, PackedOrder> kafkaTemplate;

    @Autowired
    public ShippingProducerService(KafkaTemplate<String, PackedOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, PackedOrder order)
    {
        kafkaTemplate.send(topic, order);
    }
}