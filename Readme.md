# Orca PLus

This is an application that generates budgets. An user logs in and register your products and generates budgets to your clients.  

### Endpoints

* Authentication

```
    auth/register - Register with email and password.
    auth/login - Login in to the app.
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
 
    