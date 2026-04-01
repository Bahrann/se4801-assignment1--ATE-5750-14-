# ShopWave Starter

This is a project setup with Spring Boot 3.5.13.

## Tech Stack
- Java 21
- Spring Boot 3.5.13
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Actuator

## How to Run
1. Open project in IntelliJ
2. Run ShopwaveStarterApplication
3. Application runs on http://localhost:8080

## Notes
- Configured via application.properties



# C2: Domain Model



In this section, I implemented the core entities for the ShopWave system using Spring Boot, JPA, and Lombok. These entities represent the main parts of the application such as products, categories, and orders.

---

## Entities

* **Category**: Stores product categories with name and description.
* **Product**: Represents items for sale, including price, stock, and category.
* **Order**: Represents a customer order, including status, total amount, and order items.
* **OrderItem**: Represents individual products within an order.
* **OrderStatus**: Enum for order states (PENDING, SHIPPED, DELIVERED, CANCELLED).

---

## Relationships

* A category can have many products.
* A product belongs to one category.
* An order can have many order items.
* Each order item is linked to one product and one order.

---

## Key Features

* Used JPA annotations for mapping (`@Entity`, `@OneToMany`, `@ManyToOne`, etc.)
* Added validation (`@NotBlank`, `@Positive`, `@Min`)
* Used `@CreationTimestamp` for automatic date tracking
* Implemented `addItem()` method in Order to handle adding items and calculating total
* Enabled `orphanRemoval` to automatically delete removed order items

---

## Conclusion

The domain model is fully implemented with proper structure, relationships, and validation, providing a solid base for the rest of the system.


## C3: Repository & Service Layer

C3 implements the repository and service layer for products.

- ProductRepository: Extends JpaRepository<Product, Long> with custom queries:
    - findByCategoryId(Long categoryId)
    - findByPriceLessThanEqual(BigDecimal maxPrice)
    - findByNameContainingIgnoreCase(String keyword)
    - findTopByOrderByPriceDesc() (most expensive product)

- ProductService: Handles product operations with proper transaction management:
    - Create products (createProduct)
    - Retrieve all products (getAllProducts)
    - Get a product by ID (getProductById)
    - Search products by keyword and/or price (searchProducts)
    - Update stock with validation (updateStock)

- ProductMapper: Converts between Product entities and DTOs.

- ProductNotFoundException: Custom runtime exception with descriptive messages.


