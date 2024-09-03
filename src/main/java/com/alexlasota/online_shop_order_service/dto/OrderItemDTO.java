package com.alexlasota.online_shop_order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
