package com.appsdeveloperblog.estore.productservice.kafka;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.trutran.estore.core.commands.ReserveProductCommand;
import com.trutran.estore.core.events.OrderCreatedEvent;
import com.trutran.estore.core.kafka.ProductReservePubSub;

//@Service
public class OrderCreatedConsumerService{

    private final CommandGateway commandGateway;

    @Autowired
      public OrderCreatedConsumerService( 
        CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
        System.out.println("KafkaConsumerService created");
    }


    // @KafkaListener(topics= "order-events", groupId= "my-first-group")
    // public void consume(ProductReservePubSub command){
    //      ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
    //     .orderId(command.getOrderId())
    //     .productId(command.getProductId())
    //     .quantity(command.getQuantity())
    //     .userId(command.getUserId())
    //     .build();

    //    this.commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

    //         @Override
    //         public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
    //                 CommandResultMessage<?> commandResultMessage) {
    //             if(commandResultMessage.isExceptional()){
    //                 commandResultMessage.exceptionResult().printStackTrace();
    //                 //start compensating transaction
    //             }
    //         }
            
    //     });

    //     System.out.println("Received message:" + command.getProductId() + " quantity: " + command.getQuantity());
    // }
}