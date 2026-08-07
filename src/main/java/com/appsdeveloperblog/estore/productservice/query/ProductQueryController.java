package com.appsdeveloperblog.estore.productservice.query;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    QueryGateway queryGateway;


    
    @GetMapping("")
    public List<ProductRestModel> getProducts() {
        FindProductQuery findProductQuery = new FindProductQuery();
        List<ProductRestModel> products = queryGateway.query(findProductQuery,
             ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
             return products;
    }
    
}
