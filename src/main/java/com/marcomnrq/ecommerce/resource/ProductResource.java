package com.marcomnrq.ecommerce.resource;

import lombok.Data;

@Data
public class ProductResource {
    private String name;
    private Integer stock;
    private Float price;
    private Boolean enabled;
}
