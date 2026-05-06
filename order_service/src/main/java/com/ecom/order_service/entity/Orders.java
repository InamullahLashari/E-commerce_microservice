package com.ecom.order_service.entity;


import com.ecom.order_service.dto.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {
    @Id
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private Double totalAmount;

    public Orders(String orderId, String customerId, LocalDateTime now, double totalAmount, OrderStatus orderStatus) {
    }

    public OrderStatus getStatus() {
        return null;
    }

    public void setStatus(OrderStatus orderStatus) {
        return;
    }
}