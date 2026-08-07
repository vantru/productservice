package com.appsdeveloperblog.estore.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.productservice.core.event.ProductCreatedEvent;
import com.trutran.estore.core.commands.CancelReservationProductCommand;
import com.trutran.estore.core.events.ProductReservationCancelEvent;
import com.trutran.estore.core.events.ProductReserveEvent;

@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {
    
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    protected ProductAggregate() {
    }
    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        if(createProductCommand.title.isEmpty() || createProductCommand.title.isBlank()){
            throw new IllegalArgumentException("title can not be empty or space");
        }
        if(createProductCommand.price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("quantity can be less or than 0");
        }
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handler(com.trutran.estore.core.commands.ReserveProductCommand reserveProductCommand){

        if(reserveProductCommand.getQuantity() <= 0){
            throw new IllegalArgumentException("Insufficient number of items in stock");
        }

        ProductReserveEvent productReserveEvent = new ProductReserveEvent();
        BeanUtils.copyProperties(reserveProductCommand, productReserveEvent);
        AggregateLifecycle.apply(productReserveEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId = productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
        this.price = productCreatedEvent.getPrice();
    }

    @EventSourcingHandler
    public void on(ProductReserveEvent productReserveEvent){
     //   this.productId = productReserveEvent.getProductId();
        this.quantity -= productReserveEvent.getQuantity();
    }

    @CommandHandler
    public void handled(CancelReservationProductCommand command){
        ProductReservationCancelEvent cancelReservationProductEvent = ProductReservationCancelEvent.builder()
        .orderId(command.getOrderId())
        .productId(command.getProductId())
        .quantity(command.getQuantity())
        .reason(command.getReason())
        .userId(command.getUserId()).build();
        
        AggregateLifecycle.apply(cancelReservationProductEvent);

    }

    @EventSourcingHandler
    public void on(ProductReservationCancelEvent event){
        this.quantity += event.getQuantity();
    }
}
