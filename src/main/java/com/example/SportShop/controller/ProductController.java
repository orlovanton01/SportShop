package com.example.SportShop.controller;

import com.example.SportShop.model.Product;
import com.example.SportShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public String addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return "Product is created";
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PutMapping
    public String updateProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return "Product is updated";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        if (productService.getProductById(id) != null) {
            productService.deleteProductById(id);
            return "Product is deleted";
        }
        return "Product not found";
    }
}
