package com.example.SportShop.service;

import com.example.SportShop.model.Product;
import com.example.SportShop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? productOptional.get() : null;
    }

    public void saveProduct(Product product) { productRepository.save(product); }

    public void deleteProductById(Integer id) { productRepository.deleteById(id); }

}
