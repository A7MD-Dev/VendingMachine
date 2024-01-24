
# Vending Machine API

This project creates a RESTful API for a simulated vending machine. It allows two types of users: "seller" and "buyer." Sellers can manage products (add, update, remove), while buyers can deposit coins, purchase products, and reset their deposit.




## Features

- Product management (CRUD) with seller authentication
- User management (CRUD) with role-based access control
- Coin deposit for buyers with supported denominations (5, 10, 20, 50, 100 cents)
- Product purchase with deposit balance and detailed purchase response
- Deposit reset for buyers


## API Reference
**User Endpoints**

#### Get all users

```http
GET /api/users
```

| Parameter | Type | Description |
|-----------|------|-------------|
| None | | Returns a list of all users. |

#### Get user by ID

```http
GET /api/users/{userId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `userId` | Long | **Required**. The ID of the user to retrieve. |

#### Create a user

```http
POST /api/users/create
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `username` | String | **Required**. The user's username. |
| `password` | String | **Required**. The user's password. |
| `deposit` | Integer | The user's initial deposit (optional, defaults to 0). |
| `role` | String | **Required**. The user's role ("BUYER" or "SELLER"). |

#### Update a user

```http
PUT /api/users/{userId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `userId` | Long | **Required**. The ID of the user to update. |
| `username` | String | The updated username (optional). |
| `password` | String | The updated password (optional). |
| `deposit` | Integer | The updated deposit (optional). |
| `role` | String | The updated role (optional). |

#### Delete a user

```http
DELETE /api/users/{userId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `userId` | Long | **Required**. The ID of the user to delete. |

**Product Endpoints**

#### Get all products

```http
GET /api/products
```

| Parameter | Type | Description |
|-----------|------|-------------|
| None | | Returns a list of all products. |

#### Get product by ID

```http
GET /api/products/{productId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `productId` | Long | **Required**. The ID of the product to retrieve. |

#### Create a product (seller only)

```http
POST /api/products/create
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `productName` | String | **Required**. The name of the product. |
| `cost` | Integer | **Required**. The cost of the product in cents. |
| `amountAvailable` | Integer | **Required**. The quantity of the product available. |

#### Update a product (seller only)

```http
PUT /api/products/{productId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `productId` | Long | **Required**. The ID of the product to update. |
| `productName` | String | The updated product name (optional). |
| `cost` | Integer | The updated cost (optional). |
| `amountAvailable` | Integer | The updated quantity available (optional). |

#### Delete a product (seller only)

```http
DELETE /api/products/{productId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `productId` | Long | **Required**. The ID of the product to delete. |

**Deposit Endpoint**

#### Deposit coins (buyer only)

```http
POST /api/deposit/{userId}
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `userId` | Long | **Required**. The ID of the user depositing coins. |
| `amount` | Integer | **Required**. The amount to deposit in cents (must be 5, 10, 20, 50, or 100). |


**Purchase Endpoint**

#### Buy a product (buyer only)

```http
POST /api/buy
```

| Parameter | Type | Description |
|---|---|---|
| `userId` | Long | **Required.** The ID of the buyer. |
| `productId` | Long | **Required.** The ID of the product to purchase. |
| `quantity` | Integer | **Required.** The quantity of the product to purchase. |

**Response Body:**

```json
{
  "purchase": {
    "id": 123,
    "userId": 1,
    "productId": 2,
    "quantity": 1,
    "totalPrice": 50,
    "purchaseDate": "2024-01-24T23:57:33Z"
  },
  "change": {
    "5": 2,
    "10": 1
  }
}
```

**Description:**

- This endpoint allows buyers to purchase products with their deposited funds.
- The request body requires user ID, product ID, and quantity of the product to buy.
- The response includes details of the purchase and the change returned to the buyer, broken down by coin denominations.

**Error Handling:**

- User not found error (404) if the provided user ID is invalid.
- Product not found error (404) if the provided product ID is invalid.
- Insufficient funds error (400) if the user's deposit is not enough to cover the purchase cost.
- Out of stock error (400) if the requested quantity exceeds the available product amount.
- Invalid quantity error (400) if the quantity is negative or zero.

**Reset Deposit Endpoint**

#### Reset deposit (buyer only)

```http
POST /api/deposit/reset/{userId}
```

| Parameter | Type | Description |
|---|---|---|
| `userId` | Long | **Required.** The ID of the buyer. |

**Reqest Body:**

```json
50
```

**Description:**

- This endpoint allows buyers to reset their deposit to zero.
- Only buyer users can access this endpoint.
- Upon successful reset, the user's deposit is set to 0.
- An empty response is returned upon successful reset.

**Error Handling:**

- User not found error (404) if the provided user ID is invalid.
- Access denied error (403) if the user attempting to reset is not a buyer.


## Deployment

**Prerequisites:**

- Java Development Kit (JDK) 8 or later
- Maven Tool
- DBMS

**Database Init**

1. Create a database with a name of your choice.
2. Import the `db.sql` file into the newly created database:



**Installation Steps:**

1. Clone the repository:
   ```bash
   git clone 
   ```
2. Install dependencies:
   - Maven: `mvn install`
  

**Configuration:**

1. **Database Configuration (if using a database):**
   - Modify the `application.properties` file in the `src/main/resources` directory:
     - Set the `spring.datasource.url` property to the JDBC URL of your database.
     - Set the `spring.datasource.username` and `spring.datasource.password` properties to your database credentials.


2. **Running the API Server:**
 Maven: `mvn spring-boot:run`


3. **Accessing the API:**
- Once the server is running, the API endpoints will be accessible at: http://localhost:8080/api/
