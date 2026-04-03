//Student Number: ATE/5750/14
package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.CategoryNotFoundException;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.mapper.ProductMapper;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDTO createProduct(CreateProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {

        List<Product> results;

        if (keyword != null && !keyword.isBlank() && maxPrice != null) {
            results = productRepository.findByNameContainingIgnoreCase(keyword)
                    .stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .toList();

        } else if (keyword != null && !keyword.isBlank()) {
            results = productRepository.findByNameContainingIgnoreCase(keyword);

        } else if (maxPrice != null) {
            results = productRepository.findByPriceLessThanEqual(maxPrice);

        } else {
            results = productRepository.findAll();
        }

        return results.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public ProductDTO updateStock(Long id, int delta) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newStock = product.getStock() + delta;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot go negative");
        }

        product.setStock(newStock);

        return ProductMapper.toDTO(productRepository.save(product));
    }
}