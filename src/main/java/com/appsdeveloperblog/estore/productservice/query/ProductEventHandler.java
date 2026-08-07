package com.appsdeveloperblog.estore.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.core.event.ProductCreatedEvent;
import com.appsdeveloperblog.estore.productservice.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductRepository;
import com.trutran.estore.core.events.ProductReservationCancelEvent;
import com.trutran.estore.core.events.ProductReserveEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
    
    private final ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);

    public ProductEventHandler(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        productRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReserveEvent productReserveEvent){
        ProductEntity productEntity = productRepository.findByProductId(productReserveEvent.getProductId());
        if(productEntity == null){
            throw new IllegalStateException("ProductId: " + productReserveEvent.getProductId() + " can not found");
        }
        productEntity.setQuantity(productEntity.getQuantity() - productReserveEvent.getQuantity());
        productRepository.save(productEntity);

        LOGGER.info("ProductReservedEvent is called for productId: "+ productReserveEvent.getProductId() + " and orderID: " + productReserveEvent.getOrderId());
    }

    @EventHandler
    public void on(ProductReservationCancelEvent event){
        ProductEntity productEntity = productRepository.findByProductId(event.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() + event.getQuantity());
        productRepository.save(productEntity);
        LOGGER.info("CancelReservationProductEvent is called for productId: "+ event.getProductId() + " and quantity: " + event.getQuantity());
    }
    
    @ResetHandler
    public void reset(){
        productRepository.deleteAll();
    }
}
