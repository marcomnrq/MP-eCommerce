package com.marcomnrq.ecommerce.controller;

import com.marcomnrq.ecommerce.domain.model.Product;
import com.marcomnrq.ecommerce.resource.ProductResource;
import com.marcomnrq.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    @PostMapping("/products")
    public ProductResource createProduct(@Valid @RequestBody ProductResource productResource){
        Product product = convertToEntity(productResource);
        return convertToResource(productService.createProduct(product));
    }

    @GetMapping("/products/{id}")
    public ProductResource getProductById(@PathVariable(name = "id") Integer productId){
        return convertToResource(productService.getProductById(productId));
    }

    @GetMapping("/products")
    public Page<ProductResource> getAllPosts(Pageable pageable){
        Page<Product> products = productService.getAllProducts(pageable);
        List<ProductResource> resources = products.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PutMapping("/products/{id}")
    public ProductResource updateProduct(@PathVariable(name = "id") Integer productId,
                                         @Valid @RequestBody ProductResource productResource){
        return convertToResource(productService.updateProduct(productId, productResource));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer productId){
        return productService.deleteProduct(productId);
    }

    @GetMapping("/categories/{id}/products")
    public Page<ProductResource> getAllProductsByCategoryId(@PathVariable(name = "id") Integer categoryId, Pageable pageable){
        Page<Product> products = productService.getAllProducts(pageable);
        List<ProductResource> resources = products.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/products/{id}/categories/{categoryId}")
    public ProductResource assignCategory(@PathVariable(name = "id") Integer productId,
                                          @PathVariable(name = "categoryId") Integer categoryId){
        return convertToResource(productService.assignCategory(productId, categoryId));
    }

    @DeleteMapping("/products/{id}/categories/{categoryId}")
    public ProductResource unAssignCategory(@PathVariable(name = "id") Integer productId,
                                          @PathVariable(name = "categoryId") Integer categoryId){
        return convertToResource(productService.unAssignCategory(productId, categoryId));
    }

    // Model Mapper

    private Product convertToEntity(ProductResource resource) {
        return modelMapper.map(resource, Product.class);
    }

    private ProductResource convertToResource(Product entity) {
        return modelMapper.map(entity, ProductResource.class);
    }

}
