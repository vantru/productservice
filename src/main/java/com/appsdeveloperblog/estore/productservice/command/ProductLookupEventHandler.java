package com.appsdeveloperblog.estore.productservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.core.event.ProductCreatedEvent;
import com.appsdeveloperblog.estore.productservice.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductLookupRepository;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {

    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEventHandler(ProductLookupRepository productLookupRepository)
    {
        this.productLookupRepository = productLookupRepository;
    }
    
    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }

    @ResetHandler
    public void reset(){
        this.productLookupRepository.deleteAll();
    }
}
