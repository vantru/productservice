package com.appsdeveloperblog.estore.productservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.productservice.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.data.ProductRepository;

@Component
public class ProductQueryHandler {
    
    private final ProductRepository productRepository;
    public ProductQueryHandler(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> findProduct(FindProductQuery query){
        List<ProductRestModel> productsRest = new ArrayList<>();

        var productsEntity = productRepository.findAll();

        for(ProductEntity productEntity : productsEntity){

            ProductRestModel productRest = new ProductRestModel();
            BeanUtils.copyProperties(productEntity, productRest);
            productsRest.add(productRest);
        }

        return productsRest;
    }
}
