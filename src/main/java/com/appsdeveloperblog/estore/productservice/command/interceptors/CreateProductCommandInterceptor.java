package com.appsdeveloperblog.estore.productservice.command.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.command.CreateProductCommand;
import com.appsdeveloperblog.estore.productservice.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository){
        this.productLookupRepository = productLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        // TODO Auto-generated method stub
        return (index, command) ->{
            LOGGER.info("Intercepted command: " + command.getPayloadType());            if(CreateProductCommand.class.equals(command.getPayloadType())){
                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();
                ProductLookupEntity productLookup = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());
                if(productLookup != null){
                    throw new IllegalStateException(
                        String.format("Product with ProductId %s or title %s already exists", createProductCommand.getProductId(), createProductCommand.getTitle()));

                }
            }
            return command;
        };
    }
    
}
