package com.customer.cart.consumer;

import com.customer.cart.model.OrderNotificationMessage;
import com.customer.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderNotificationConsumer {

    private final CartService cartService;

    @KafkaListener(topics = "${order.message.topic}", groupId = "${order.consumer.group}")
    public void consume(ConsumerRecord<String, OrderNotificationMessage> consumerRecord) {
        cartService.deleteCartByCartId(consumerRecord.value().getCartId());
        log.info("Cart data for the cartId deleted successfully");
    }

}
