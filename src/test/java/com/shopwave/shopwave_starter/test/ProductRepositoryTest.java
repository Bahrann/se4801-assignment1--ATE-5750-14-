// ATE/5750/14
package com.shopwave.shopwave_starter.test;

import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_returnsCorrectResults() {
        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setDescription("Gaming laptop");
        p1.setPrice(new BigDecimal("1200"));
        p1.setStock(10);

        Product p2 = new Product();
        p2.setName("Phone");
        p2.setDescription("Smartphone");
        p2.setPrice(new BigDecimal("800"));
        p2.setStock(5);

        productRepository.save(p1);
        productRepository.save(p2);

        List<Product> results =
                productRepository.findByNameContainingIgnoreCase("lap");

        assertEquals(1, results.size());
        assertEquals("Laptop", results.get(0).getName());
    }
}