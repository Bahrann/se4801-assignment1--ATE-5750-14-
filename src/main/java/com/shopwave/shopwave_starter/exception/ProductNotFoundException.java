// Student Number: ATE/5750/14

package com.shopwave.shopwave_starter.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with ID " + id + " not found.");
    }
}
