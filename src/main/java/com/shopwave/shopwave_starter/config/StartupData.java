
// Student Number: ATE/5750/14
package com.shopwave.shopwave_starter.config;

import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class StartupData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("Phones, laptops, and gadgets");
            categoryRepository.save(electronics);

            Category clothing = new Category();
            clothing.setName("Clothing");
            clothing.setDescription("Shirts, shoes, and accessories");
            categoryRepository.save(clothing);

            Category books = new Category();
            books.setName("Books");
            books.setDescription("Novels, textbooks, and comics");
            categoryRepository.save(books);

            productRepository.save(Product.builder()
                    .name("Laptop Pro 15")
                    .description("High performance laptop for developers")
                    .price(new BigDecimal("1299.99"))
                    .stock(25)
                    .category(electronics)
                    .build());

            productRepository.save(Product.builder()
                    .name("Wireless Headphones")
                    .description("Noise-cancelling over-ear headphones")
                    .price(new BigDecimal("249.99"))
                    .stock(100)
                    .category(electronics)
                    .build());

            productRepository.save(Product.builder()
                    .name("Running Shoes")
                    .description("Lightweight shoes for long distance running")
                    .price(new BigDecimal("89.99"))
                    .stock(60)
                    .category(clothing)
                    .build());
        }
    }
}