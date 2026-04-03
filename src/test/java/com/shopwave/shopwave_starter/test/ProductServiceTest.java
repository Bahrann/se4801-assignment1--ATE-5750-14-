// ATE/5750/14
package com.shopwave.shopwave_starter.test;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.CategoryNotFoundException;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import com.shopwave.shopwave_starter.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private CreateProductRequest request;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new CreateProductRequest(
                "Laptop", "Gaming laptop",
                new BigDecimal("1200"), 10, 1L
        );

        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
    }

    @Test
    void createProduct_success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        ProductDTO result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.createProduct(request));

        verify(productRepository, never()).save(any());
    }
}