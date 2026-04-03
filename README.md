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


# Shopwave Starter – C4 Assignment

Spring Boot REST API for managing products with categories:
- Create product
- List products (paginated)
- Get product by ID
- Search by keyword/max price
- Update stock

Tech: Java 21, Spring Boot 3.5, Maven, H2, Lombok, Validation

## B. Setup & Run
1. Clone repo:
   git clone <repo-url>
   cd shopwave-starter
2. Run with Maven Wrapper:
   ./mvnw spring-boot:run      # Linux/Mac
   mvnw.cmd spring-boot:run    # Windows
3. Seeded categories: Electronics, Clothing, Books

## C. API Endpoints

The API manages products with these endpoints: GET /api/products for paginated products, GET /api/products/{id} for a single product, POST /api/products to create a product, GET /api/products/search to filter by keyword or maxPrice, and PATCH /api/products/{id}/stock to update stock. All endpoints return JSON and proper HTTP status codes (200, 201, 400, 404).

## D. Sample Requests
POST /api/products
{
"name": "Laptop Pro",
"description": "High-end laptop",
"price": 1299.99,
"stock": 10,
"categoryId": 1
}

GET /api/products/search?keyword=laptop&maxPrice=1500

PATCH /api/products/1/stock
{
"delta": -2
}

## E. Tests & Verification
Run tests:
mvn test
Check:
- Application starts without errors
- All tests pass
- API responses match expected outputs


## C5 – Testing

This project includes comprehensive testing for the Product module using different testing strategies in Spring Boot.

### 1. ProductServiceTest (Unit Test with Mockito)

* Uses Mockito to mock dependencies (`ProductRepository`, `CategoryRepository`)
* Tests:

    *  Successful product creation (happy path)
    *  Exception when category is not found

### 2. ProductControllerTest (Web Layer Test)

* Uses `@WebMvcTest` to test only the controller layer
* Uses a mocked `ProductService` (without deprecated `@MockBean`)
* Tests:

    *  `GET /api/products` returns **200 OK** with paginated response
    *  `GET /api/products/999` returns **404 Not Found** with error JSON

### 3. ProductRepositoryTest (Data Layer Test with H2)

* Uses `@DataJpaTest` with in-memory H2 database
* Tests:

    * `findByNameContainingIgnoreCase` returns correct filtered results

### Test Execution

Run all tests using:

```
mvn test
```

### Test Results

* All tests pass successfully:

    * No failures
    *  No errors
    *  Build successful

---

### Notes

* Modern Spring Boot practices were followed
* Deprecated features like `@MockBean` were avoided
* Clean separation of test layers:

    * Unit (Service)
    * Web (Controller)
    * Data (Repository)
