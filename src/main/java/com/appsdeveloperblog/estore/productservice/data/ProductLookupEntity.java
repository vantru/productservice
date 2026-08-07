package com.appsdeveloperblog.estore.productservice.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productlookup")
public class ProductLookupEntity implements Serializable {
    
    @Id
    private String productId;

    @Column(unique = true)
    private String title;
}
