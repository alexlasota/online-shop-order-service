package com.alexlasota.online_shop_order_service.dto;

import com.alexlasota.online_shop_order_service.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private Long userId;
    private List<OrderItemDTO> items;
    private List<OrderAttributeDTO> attributes;
}
