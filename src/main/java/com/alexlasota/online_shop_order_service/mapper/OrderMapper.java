package com.alexlasota.online_shop_order_service.mapper;

import com.alexlasota.online_shop_order_service.dto.OrderAttributeDTO;
import com.alexlasota.online_shop_order_service.dto.OrderDTO;
import com.alexlasota.online_shop_order_service.dto.OrderItemDTO;
import com.alexlasota.online_shop_order_service.model.Order;
import com.alexlasota.online_shop_order_service.model.OrderAttribute;
import com.alexlasota.online_shop_order_service.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    OrderAttributeDTO toDTO(OrderAttribute orderAttribute);

    @Mapping(target = "order", ignore = true)
    OrderAttribute toEntity(OrderAttributeDTO orderAttributeDTO);

    List<OrderDTO> toDTOList(List<Order> orders);
}
