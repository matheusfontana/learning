package com.acme.lombok_exercises.classes.e2;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tasks:
 * Create a complete order using static factory methods
 * Create the same order using builders
 * Update the order status using toBuilder
 * Add a new item to existing order using toBuilder
 */
class OrderTest {
    @Test
    void shouldCreateCompleteOrderUsingStaticFactory(){
        Order order = Order.of(
                "2025_MK001",
                Order.Customer.of("MKSSN", "Michael Kayne", "mkayne@acme.com"),
                List.of(Order.OrderItem.of(
                            "ALIENWARE_AURORA",
                            1,
                            1999.99
                        )
                ),
                Order.OrderStatus.CONFIRMED
        );

        assertThat(order).isNotNull();
        assertThat(order.getOrderId()).isEqualTo("2025_MK001");
        assertThat(order.getCustomer().getCustomerId()).isEqualTo("MKSSN");
        assertThat(order.getCustomer().getName()).isEqualTo("Michael Kayne");
        assertThat(order.getCustomer().getEmail()).isEqualTo("mkayne@acme.com");
        assertThat(order.getItems()).hasSize(1);
        Order.OrderItem item = order.getItems().getFirst();
        assertThat(item.getProductId()).isEqualTo("ALIENWARE_AURORA");
        assertThat(item.getQuantity()).isEqualTo(1);
        assertThat(item.getUnitPrice()).isEqualTo(1999.99);
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.CONFIRMED);
    }

    @Test
    void shouldCreateCompleteOrderUsingBuilder(){
        final Order order = generateOrder();

        assertThat(order).isNotNull();
        assertThat(order.getOrderId()).isEqualTo("2025_MK001");
        assertThat(order.getCustomer().getCustomerId()).isEqualTo("MKSSN");
        assertThat(order.getCustomer().getName()).isEqualTo("Michael Kayne");
        assertThat(order.getCustomer().getEmail()).isEqualTo("mkayne@acme.com");
        assertThat(order.getItems()).hasSize(1);
        Order.OrderItem item = order.getItems().getFirst();
        assertThat(item.getProductId()).isEqualTo("ALIENWARE_AURORA");
        assertThat(item.getQuantity()).isEqualTo(1);
        assertThat(item.getUnitPrice()).isEqualTo(1999.99);
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.CONFIRMED);
    }

    @Test
    void shouldUpdateOrderStatusUsingToBuilder() {
        final Order order = generateOrder();
        Order orderDelivered = order.toBuilder().status(Order.OrderStatus.DELIVERED).build();

        assertThat(orderDelivered).isNotNull();
        assertThat(orderDelivered)
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(order);

        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.CONFIRMED);
        assertThat(orderDelivered.getStatus()).isEqualTo(Order.OrderStatus.DELIVERED);
    }

    @Test
    void shouldAddNewItemToExistingOrder() {
        final Order order = generateOrder();
        final Order.OrderItem orderItemNew = Order.OrderItem.builder()
                .productId("MACBOOK_AIR_M4_13")
                .quantity(2)
                .unitPrice(999.00)
                .build();
        final List<Order.OrderItem> orderItemList = List.of(order.getItems().getFirst(), orderItemNew);

        Order orderUpdated = order.toBuilder().items(orderItemList).build();

        assertThat(orderUpdated).isNotNull();
        assertThat(orderUpdated.getItems()).hasSize(2);
        assertThat(orderUpdated.getItems()).contains(order.getItems().getFirst(), orderItemNew);
        assertThat(orderUpdated)
                .usingRecursiveComparison()
                .ignoringFields("items")
                .isEqualTo(order);
    }

    private Order generateOrder(){
        return Order.builder()
                .orderId("2025_MK001")
                .customer(Order.Customer.builder()
                        .customerId("MKSSN")
                        .name("Michael Kayne")
                        .email("mkayne@acme.com")
                        .build()
                )
                .items(List.of(Order.OrderItem.builder()
                        .productId("ALIENWARE_AURORA")
                        .quantity(1)
                        .unitPrice(1999.99)
                        .build()
                ))
                .status(Order.OrderStatus.CONFIRMED)
                .build();
    }
}