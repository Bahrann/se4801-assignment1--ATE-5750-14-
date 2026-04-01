// Student Number: ATE/5750/14

package com.shopwave.shopwave_starter.mapper;

import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.dto.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .build();
    }

    public static void updateStock(Product product, int delta) {
        int newStock = product.getStock() + delta;
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        product.setStock(newStock);
    }
}