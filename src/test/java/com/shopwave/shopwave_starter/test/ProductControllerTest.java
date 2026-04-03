// ATE/5750/14
package com.shopwave.shopwave_starter.test;

import com.shopwave.shopwave_starter.controller.ProductController;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.TestConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    private ProductDTO sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new ProductDTO(
                1L,
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1200"),
                10,
                1L
        );
    }

    static class TestConfig {
        @Bean
        public ProductService productService() {
            return mock(ProductService.class);
        }
    }

    @Test
    void getAllProducts_returns200() throws Exception {
        when(productService.getAllProducts(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleProduct)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Laptop"));
    }

    @Test
    void getProductById_notFound_returns404() throws Exception {
        when(productService.getProductById(999L))
                .thenThrow(new ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Product with ID 999 not found."));
    }
}