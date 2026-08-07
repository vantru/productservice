package com.appsdeveloperblog.estore.productservice.kafka;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.trutran.estore.core.events.ProductReserveEvent;
import com.trutran.estore.core.kafka.ProductReservePubSub;

//@Component
public class KafkaPublisher {
    
    private final KafkaTemplate<String, ProductReservePubSub> kafkaTemplate;

    public KafkaPublisher( KafkaTemplate<String, ProductReservePubSub> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventHandler
    public void on(ProductReserveEvent event){
        ProductReservePubSub productReservePubSub = new ProductReservePubSub();
        BeanUtils.copyProperties(event, productReservePubSub);

        kafkaTemplate.send("product-events", productReservePubSub);
    }
}
