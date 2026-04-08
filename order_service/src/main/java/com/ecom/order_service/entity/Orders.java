package com.ecom.order_service.entity;


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
public class Orders {
    @Id
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private Double totalAmount;

}