package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.Category;
import com.marcomnrq.ecommerce.domain.model.Product;
import com.marcomnrq.ecommerce.domain.repository.CategoryRepository;
import com.marcomnrq.ecommerce.domain.repository.ProductRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.ProductResource;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product updateProduct(Integer id, ProductResource productResource){
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));
        product.setName(productResource.getName());
        product.setStock(productResource.getStock());
        product.setPrice(productResource.getPrice());
        product.setEnabled(productResource.getEnabled());
        return productRepository.save(product);
    }

    public ResponseEntity<?> deleteProduct(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    public Product assignCategory(Integer productId, Integer categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException("Category not found"));
        return productRepository.findById(productId).map(product -> {
            if(!product.getCategories().contains(category)) {
                product.getCategories().add(category);
                return productRepository.save(product);
            }
            return product;
        }).orElseThrow(() -> new CustomException("Product not found"));
    }

    public Product unAssignCategory(Integer productId, Integer categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException("Category not found"));
        return productRepository.findById(productId).map(product -> {
            product.getCategories().remove(category);
            return productRepository.save(product);
        }).orElseThrow(() -> new CustomException("Product not found"));
    }
}
