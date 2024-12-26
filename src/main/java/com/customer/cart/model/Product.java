package com.customer.cart.model;

import com.customer.cart.enumuration.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;

    private String description;

    private Category  category;

    private BigDecimal price;

    private Integer availableQuantity;

    private Boolean isAvailable;

}
