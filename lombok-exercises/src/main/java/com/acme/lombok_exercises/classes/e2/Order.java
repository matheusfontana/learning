package com.acme.lombok_exercises.classes.e2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Getter
public class Order {
    private final String orderId;
    private final Customer customer;
    private final List<OrderItem> items;
    private OrderStatus status;

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class Customer {
        private final String customerId;
        private final String name;
        private String email;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class OrderItem {
        private final String productId;
        private final int quantity;
        private double unitPrice;
    }

    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED
    }
}