# Microservices E-Commerce System

## Overview
Spring Boot microservices project with API Gateway, Eureka, Auth Service, Kafka, Product, Order, Payment services.

## Architecture
Client → API Gateway → Microservices  
Service Discovery via Eureka  
Kafka for async communication  

## Services
- API Gateway: routing + JWT validation  
- Auth Service: login + JWT generation  
- Product Service: product management  
- Order Service: order handling  
- Payment Service: payment processing  
- Eureka: service registry  
- Kafka: event streaming  

## Flow
Client → Auth → JWT → Gateway → Services → Kafka events

## Tech Stack
Java, Spring Boot, Spring Cloud, Kafka, MySQL

## Security
- JWT authentication  
- Role-based access control  
- API Gateway validation  

## Future Improvements
Docker, Kubernetes, Redis, Keycloak
