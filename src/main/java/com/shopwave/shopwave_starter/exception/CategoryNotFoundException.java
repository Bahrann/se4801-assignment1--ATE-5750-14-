// ATE/5750/14
package com.shopwave.shopwave_starter.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id + " not found.");
    }
}
