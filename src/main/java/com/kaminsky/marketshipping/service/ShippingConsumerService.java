package com.kaminsky.marketshipping.service;

import com.kaminsky.entity.MarketOrder;
import com.kaminsky.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ShippingConsumerService {
    private final ShippingProducerService shippingProducerService;

    private final Logger logger = LoggerFactory.getLogger(ShippingConsumerService.class);

    public ShippingConsumerService(ShippingProducerService shippingProducerService) {
        this.shippingProducerService = shippingProducerService;
    }

    @Async
    @RetryableTopic(backoff = @Backoff(delay = 3000))
    @KafkaListener(topics = "payed_orders", groupId = "shipping_group")
    public void listen(MarketOrder order) {
        try {
            logger.info("Новый заказ: {}", order);
            order.setPacked(true);
            order.setStatus(Status.DONE);
            shippingProducerService.sendMessage("sent_orders", order);
        } catch (Exception e) {
            logger.error("Ошибка при обработке заказа: {}", order, e);
            throw e;
        }
    }
}
