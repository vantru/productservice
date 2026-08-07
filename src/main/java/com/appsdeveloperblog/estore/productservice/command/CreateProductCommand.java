package com.appsdeveloperblog.estore.productservice.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateProductCommand {
    
    @TargetAggregateIdentifier
    public String productId;
    public String title;
    public Integer quantity;
    public BigDecimal price;
}
