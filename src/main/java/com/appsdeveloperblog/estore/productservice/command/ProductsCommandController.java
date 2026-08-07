package com.appsdeveloperblog.estore.productservice.command;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.productservice.command.rest.CreateProductRestModel;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final CommandGateway commandGateway;
    @Autowired
    public ProductsCommandController(org.axonframework.commandhandling.gateway.CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/createProduct")
    public String CreateProduct(@RequestBody CreateProductRestModel createProductRestModel){
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
        .productId(UUID.randomUUID().toString())
        .title(createProductRestModel.getTitle())
        .quantity(createProductRestModel.getQuantity())
        .price(createProductRestModel.getPrice()).build();
        String rs;
        try{
           rs = commandGateway.sendAndWait(createProductCommand);
        }
        catch(Exception ex){
            rs = ex.getLocalizedMessage();
        }
        return rs;
    }
}
