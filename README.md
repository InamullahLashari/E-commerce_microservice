# Microservices E-Commerce System

## Overview
A scalable E-Commerce backend built using Spring Boot Microservices architecture.  
The system uses API Gateway for centralized routing, Eureka for service discovery, JWT-based authentication for security, Kafka for asynchronous communication, and Redis for caching and performance optimization.

---

## Architecture

Client → API Gateway → Auth Service → Other Microservices  
Services communicate through REST APIs and Kafka events.  
Service registration and discovery are managed using Eureka Server.

### Core Components
- API Gateway
- Eureka Discovery Server
- Auth Service
- Product Service
- Order Service
- Payment Service
- Notification Service
- Kafka Message Broker
- Redis Cache

---

## Services

### API Gateway
- Central entry point for all client requests
- Request routing and load balancing
- JWT token validation
- Rate limiting and request filtering

### Auth Service
- User authentication and authorization
- JWT token generation and validation
- Role-based access control (RBAC)

### Product Service
- Product catalog management
- Inventory handling
- Product search and updates

### Order Service
- Order creation and management
- Communicates with Product and Payment services
- Publishes order events using Kafka

### Payment Service
- Payment processing and transaction handling
- Payment status management
- Kafka event publishing for payment updates

### Notification Service
- Handles email/SMS notifications
- Consumes Kafka events asynchronously

### Eureka Discovery Server
- Dynamic service registration and discovery
- Enables inter-service communication

### Redis
- Caching frequently accessed data
- Improves response time and system performance
- Session/token caching support

### Kafka
- Asynchronous event-driven communication
- Decouples services for better scalability

---

## Request Flow

1. Client sends request to API Gateway
2. API Gateway validates JWT token
3. Request is routed to the required microservice
4. Services communicate via REST APIs
5. Events are published to Kafka
6. Notification service consumes events asynchronously
7. Redis caches frequently used data for faster access

---

## Tech Stack

- Java
- Spring Boot
- Spring Cloud
- Spring Security
- Spring Cloud Gateway
- Eureka Server
- Apache Kafka
- Redis
- MySQL
- Maven

---

## Security

- JWT-based authentication
- Role-based access control (RBAC)
- API Gateway security filters
- Secure inter-service communication

---

## Features

- Scalable microservices architecture
- Centralized API Gateway
- Service discovery with Eureka
- Event-driven communication using Kafka
- Redis caching for high performance
- Secure authentication and authorization
- Independent service deployment

---

## Future Improvements

- Docker containerization
- Kubernetes orchestration
- CI/CD pipeline integration
- Distributed tracing with Zipkin
- Monitoring with Prometheus & Grafana
- Keycloak for advanced identity management
- Circuit breaker using Resilience4j
