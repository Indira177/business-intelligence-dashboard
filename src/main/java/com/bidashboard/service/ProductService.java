package com.bidashboard.service;

import com.bidashboard.exception.ResourceNotFoundException;
import com.bidashboard.model.Product;
import com.bidashboard.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add a new product
    public Product createProduct(Product product) {

        validateProduct(product);

        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with ID: " + id
                        ));
    }

    // Search products by name
    public List<Product> searchProducts(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    // Filter products by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    // Filter products by supplier
    public List<Product> getProductsBySupplier(String supplier) {
        return productRepository.findBySupplierIgnoreCase(supplier);
    }

    // Get low-stock products
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }

    // Update product
    public Product updateProduct(String id, Product updatedProduct) {

        Product existingProduct = getProductById(id);

        validateProduct(updatedProduct);

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCostPrice(updatedProduct.getCostPrice());
        existingProduct.setSellingPrice(updatedProduct.getSellingPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setSupplier(updatedProduct.getSupplier());

        return productRepository.save(existingProduct);
    }

    // Delete product
    public void deleteProduct(String id) {

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Product not found with ID: " + id
            );
        }

        productRepository.deleteById(id);
    }

    // Validate product prices and stock
    private void validateProduct(Product product) {

        if (product.getCostPrice() < 0 ||
                product.getSellingPrice() < 0 ||
                product.getStockQuantity() < 0) {

            throw new IllegalArgumentException(
                    "Price and stock cannot be negative"
            );
        }
    }
}
