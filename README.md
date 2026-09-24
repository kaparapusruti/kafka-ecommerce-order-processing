# Event-Driven Order Processing System

## Technologies Used
- Java
- Spring Boot
- Apache Kafka
- MySQL
- Docker
- Postman
- Maven

## Microservices

### 1. Order Service
Port: 8081

Responsibilities:
- Accept order request
- Save order
- Publish ORDER_CREATED event

Kafka Topic:
- order-created


### 2. Payment Service
Port: 8082

Responsibilities:
- Consume ORDER_CREATED
- Process payment
- Save payment
- Publish PAYMENT_SUCCESS or PAYMENT_FAILED

Kafka Topics:
- payment-success
- payment-failed


### 3. Delivery Service
Port: 8083

Responsibilities:
- Consume PAYMENT_SUCCESS
- Create delivery
- Generate tracking number
- Save delivery
- Publish DELIVERY_CREATED

Kafka Topic:
- delivery-created


### 4. Notification Service
Port: 8084

Responsibilities:
- Listen to:
  - ORDER_CREATED
  - PAYMENT_SUCCESS
  - PAYMENT_FAILED
  - DELIVERY_CREATED
- Print notification information


## Kafka Topics

- order-created
- payment-success
- payment-failed
- delivery-created
- payment-success.DLT


## Consumer Groups

- payment-service-group
- delivery-service-group
- notification-service-group


## Architecture

Postman
   |
   v
Order Service
   |
   v
order-created
   |
   v
Payment Service
   |
   |---- FAILED ----> payment-failed
   |
   |---- SUCCESS ---> payment-success
                          |
                          v
                    Delivery Service
                          |
                          v
                    delivery-created

Notification Service listens to all events.


## Payment Logic

Amount <= 50000
→ PAYMENT_SUCCESS

Amount > 50000
→ PAYMENT_FAILED


## Kafka Features Implemented

### Message Key
orderId is used as Kafka message key.

### Consumer Groups
Different services use independent consumer groups.

### Idempotency
Payment Service checks eventId before processing.
Duplicate events are ignored.

### Retry
Delivery Service retries failed message processing.

### Dead Letter Topic
After retry attempts fail, the event is sent to:

payment-success.DLT


## Successful Flow

ORDER_CREATED
→ PAYMENT_SUCCESS
→ DELIVERY_CREATED


## Failed Payment Flow

ORDER_CREATED
→ PAYMENT_FAILED
→ No delivery created


## Running Kafka

Start ZooKeeper:

docker start zookeeper

Start Kafka:

docker start kafka

Check containers:

docker ps


## List Kafka Topics

docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list


## Test Topics

Order Created:

docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic order-created

Payment Success:

docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic payment-success

Payment Failed:

docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic payment-failed

Delivery Created:

docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic delivery-created

DLT:

docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic payment-success.DLT


## Sample Successful Request

POST /order/create

{
  "customerId": 501,
  "customerName": "Akshya",
  "productId": 546,
  "productName": "Mobile",
  "quantity": 1,
  "amount": 45000,
  "deliveryAddress": "Bangalore"
}


## Sample Failed Payment Request

{
  "customerId": 502,
  "customerName": "Akshya",
  "productId": 546,
  "productName": "Mobile",
  "quantity": 1,
  "amount": 75000,
  "deliveryAddress": "Bangalore"
}


## DLT Test

Use:

"deliveryAddress": "DLT_TEST"

Delivery Service intentionally throws an exception.

After retries the event is published to:

payment-success.DLT
