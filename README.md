# Product Pricing App

## Table of Contents
- Introduction
- Architecture and Design Choices
- Technologies Used
- API Overview
- Getting Started
- Prerequisites
- Running the Application
- Testing
- Interpretation and Decisions
- Assumptions Made
- Extending Discount Policies
- Future Improvements

## Introduction

The Product Pricing App is a REST API service designed as part of a shopping platform. It allows for calculating product prices with configurable discount policies based on quantity and percentage discounts. The API also supports adding and retrieving products and discount policies. This project is built with an emphasis on professional practices, ensuring high code quality, modularity, and testability.

## Architecture and Design Choices

This project is implemented using Spring Boot (Java 17) with a focus on best practices and modern Java features. Here are some key architectural choices:
Java 17: Utilized for its modern features, such as records and improved support for immutable data structures.
- Spring Boot: Chosen for its robust ecosystem, ease of setup, and the inclusion of Spring Data MongoDB for database operations.
- MongoDB: Used as the data store for products and discount policies. MongoDB is highly flexible and allows for easy indexing and searching of discount policies.
- Lombok: Used for minimizing boilerplate code, particularly for data models.
- MapStruct: Chosen for DTO to entity mapping, although its use was minimal in this project due to the simplicity of mappings.
- Testcontainers: Integrated to run MongoDB in Docker containers for integration tests, ensuring tests run in an isolated, consistent environment.
- Exception Handling: Utilized @RestControllerAdvice for global exception handling to keep error management centralized and clean.

## Technologies Used

- Java 17
-  Spring Boot 3
-  Spring Data MongoDB: For data persistence.
-  Lombok: To reduce boilerplate code.
-  MapStruct: For DTO to entity mapping.
-  Testcontainers: To create a MongoDB container for integration tests.
-  JUnit 5 and Mockito: For unit and integration testing.
-  Swagger (OpenAPI): For API documentation.

## API Overview

### Product API
- POST /api/products - Adds a new product. 
- GET /api/products/{productId} - Retrieves a product by its UUID. 
- POST /api/products/calculate-price - Calculates the total price for a product based on the provided quantity and applicable discount policies. 

### Discount Policy API

- POST /api/discounts - Creates a new discount policy.
- GET /api/discounts - Retrieves all discount policies.

## Getting Started
### Prerequisites
- Java 17
- Docker


### Running the Application

Build the application:

```agsl
mvn clean install
```

Run the application:
```agsl
docker-compose up --build
```

## Testing

- Unit Tests: Implemented using JUnit 5 and Mockito. Key tests include:
  - OneDiscountOnlyValidatorTest: Ensures that only one type of discount is allowed.
- Integration Tests: Use Testcontainers for MongoDB.

## Interpretation and Decisions

### Why Discount Policies Are Configurable

To fulfill the requirement of configurable discount policies, we designed the discount logic to support multiple policy types using an abstract DiscountPolicy class. The current implementation allows:

- Amount-Based Policy: Offers a fixed discount amount when a specified quantity threshold is met.
- Percentage-Based Policy: Provides a percentage discount based on quantity.

By using this structure, we can easily introduce new discount types in the future. For example, a "Buy One, Get One Free" policy could be added by creating a new subclass of DiscountPolicy.


### Why Use MongoDB for Discount Policies

We chose MongoDB due to its schema-less nature, which allows for easy expansion of discount policy types. This flexibility supports the storage of different types of policies without the need for complex schema migrations in a traditional SQL database.


### Assumptions Made

- Discount Type Selection: The system should enforce that a discount policy can have either an amount-based discount or a percentage-based discount, but not both. This decision led to the creation of a custom validator (OneDiscountOnlyValidator) to ensure data integrity.
- Product Currency: All products are assumed to use the same currency for simplicity. Future enhancements could include multi-currency support.
- Single Calculation Method: The application assumes that the discount calculation is performed once per order request. Dynamic updates to discounts based on real-time changes (e.g., flash sales) are not currently supported.
- API Security: For simplicity, authentication and authorization were not included. In a real-world scenario, we would integrate OAuth2 or JWT for securing endpoints.

### Extending Discount Policies

To make discount policies extendable, we implemented the DiscountPolicy abstract class as a base for specific policy types. The system currently supports:

- Amount-Based Discounts: Fixed discount amounts when a certain quantity threshold is met.
- Percentage-Based Discounts: Percentage-based discounts increasing with quantity.


### How to Extend

To add new discount types in the future:

- Create a New Policy Class: Extend DiscountPolicy with the new discount type logic.
- Update the Service: Add the new policy type handling in DiscountPolicyService.
- Add Validation: Introduce new validation rules if necessary.

- For example, a "Buy One Get One" discount could be added by creating a BuyOneGetOneDiscountPolicy class and updating the discount calculation logic accordingly.

### Future Improvements
- Caching: Implement a caching mechanism (e.g., Redis) to store frequently accessed product prices and discount calculations.
- Security: Add Spring Security to handle authentication and authorization for API endpoints.
- Advanced Discount Policies: Introduce combined discounts or conditional discounts based on customer loyalty.
- Monitoring: Integrate logging and monitoring tools (e.g., Prometheus, Grafana) to monitor application performance.


## Manual Testing

This section describes how to manually test each API endpoint using tools like Swagger UI or Postman. Below are the detailed steps for testing all available options in the application.

1. Test Adding a Product (POST /api/products)
   Objective: Add a new product to the system.

- Step 1: Open Swagger UI or Postman and select the POST method for the /api/products endpoint.
- Step 2: Enter the following JSON data:

```json
{
  "name": "Sample Product",
  "price": 150.50
}
```


- Step 3: Execute the request.
- Expected Result: A response with HTTP status 201 Created should be returned, along with the details of the created product:

```json
{
  "id": "generated-uuid",
  "name": "Sample Product",
  "price": 150.50
}
```

2. Test Retrieving a Product (GET /api/products/{productId})
   Objective: Retrieve product details using its UUID.

- Step 1: Copy the UUID of the product added in the previous test.
- Step 2: In Swagger UI or Postman, select the GET method for the /api/products/{productId} endpoint and insert the UUID into the path.
- Step 3: Execute the request.
- Expected Result: A response with HTTP status 200 OK should be returned, containing the product details:

```json
{
  "id": "generated-uuid",
  "name": "Sample Product",
  "price": 150.50
}
```

3. Test Calculating Product Price (POST /api/products/calculate-price)
   Objective: Calculate the product's price based on the quantity and applicable discount policies.

- Step 1: In Swagger UI or Postman, select the POST method for the /api/products/calculate-price endpoint.
- Step 2: Enter the following JSON data:


```json
{
  "productId": "generated-uuid",
  "quantity": 20
}
```
- Step 3: Execute the request.
- Expected Result: A response with HTTP status 200 OK should be returned, displaying the total price and applied discount:

```json
{
  "totalPrice": 2700.00,
  "discountApplied": 300.00
}
```

4. Test Adding a Discount Policy (POST /api/discounts)
   Objective: Add a new discount policy to the system.

- Step 1: Select the POST method for the /api/discounts endpoint in Swagger UI or Postman.
- Step 2: Enter the JSON data for an amount-based discount:

```json
{
  "quantityThreshold": 10,
  "discountAmount": 5.00,
  "discountPercentage": null
}
```
Or, for a percentage-based discount:

```json
{
  "quantityThreshold": 20,
  "discountAmount": null,
  "discountPercentage": 10.00
}
```

- Step 3: Execute the request.
- Expected Result: A response with HTTP status 201 Created should be returned, along with the details of the created discount policy:

```json
{
  "id": "generated-uuid",
  "quantityThreshold": 10,
  "discountAmount": 5.00,
  "discountPercentage": null
}
```

5. Test Retrieving All Discount Policies (GET /api/discounts)
   Objective: Retrieve a list of all added discount policies.

- Step 1: Select the GET method for the /api/discounts endpoint in Swagger UI or Postman.
- Step 2: Execute the request.
- Expected Result: A response with HTTP status 200 OK should be returned, containing a list of all discount policies:

```json
[
  {
    "id": "generated-uuid",
    "quantityThreshold": 10,
    "discountAmount": 5.00,
    "discountPercentage": null
  },
  {
    "id": "generated-uuid",
    "quantityThreshold": 20,
    "discountAmount": null,
    "discountPercentage": 10.00
  }
]
```

Notes for Manual Testing

- All the tests can be performed using Swagger UI available at http://localhost:8080/swagger-ui.html.
- Each step has been designed to test the key functionalities of the application, including handling of valid data, validation scenarios, and error handling.



