package com.alexlasota.online_shop_order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderAttributeDTO {
    private Long id;
    private String name;
    private String attributeValue;
}