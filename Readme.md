# Orca PLus

This is an application that generates budgets. An user logs in and register your products and generates budgets to your clients.  

### Technologies
1) Spring Boot
2) Spring Security
3) Postgres
4) Docker
5) JWT

### Business rule

1) User needs to register on the application. 
2) A registered user can log in to the application. He receives a JWT token that he must use in the next requests.
3) A logged user can create, update, delete and retrieve your budgets, your customers and your products.

### Endpoints

* Authentication

```
   POST auth/register - Register with email and password.
   POST auth/login - Login in to the app.
```

* Products

```
    POST /product - Create a new Product.
    GET /product - Get all products from a authenticated user.
    GET /product/{productId} - Get one product from a authenticated user by productId.
    DELETE /product/{productId} - Delete a product from a authenticated user.
    PUT /product/{productId} - Update a product from a authenticated user.
```

* Clients

```
    POST /client - Create a new client from a authenticated user.
    GET /client - Get all clients from a authenticated user.
    GET /client/{clientId} - Get one client from a authenticated user by clientId.
    DELETE /client/{clientId} - Delete a client from a authenticated user.
    PUT /client/{clientId} - Update a product from a authenticated user.
```

* Budgets

```
    POST /budget - Create a new budget.
    GET /budget - Get all budgets from a authenticated user.
    GET /budget/{budgetId} - Get one budget from a authenticated user by budgetId.
    DELETE /budget/{budgetId} - Delete a budget from a authenticated user.
```

### Models
* **User**
    * id: UUID
    * Name: String
    * Email: String
    * Password: String
    * Address: String
    * Contact: String

* **Client**
    * id: UUID
    * Name: String
    * Address: String
    * Contact: String

* **Product**
  * id: UUID
  * Name: String
  * Price: Double

* **Budget**
  * ID: UUID;
  * date: Date
  * total_price: Double
  * client_name: String
  * client_address: String
  * client_contact: String
  * products: List<Product>
  * user_id: UUID
 
    