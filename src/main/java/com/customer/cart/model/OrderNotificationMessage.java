package com.customer.cart.model;

import com.customer.cart.enumuration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderNotificationMessage implements Serializable {

    private Long orderId;

    private String userId;

    private Long cartId;

    private List<OrderItems> orderItems;

    private OrderStatus orderStatus;

    private BigDecimal totalCost;

    private LocalDate orderDate;
}
