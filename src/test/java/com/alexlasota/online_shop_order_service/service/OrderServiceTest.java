package com.alexlasota.online_shop_order_service.service;

import com.alexlasota.online_shop_order_service.dto.OrderDTO;
import com.alexlasota.online_shop_order_service.dto.OrderItemDTO;
import com.alexlasota.online_shop_order_service.dto.OrderAttributeDTO;
import com.alexlasota.online_shop_order_service.exception.OrderNotFoundException;
import com.alexlasota.online_shop_order_service.mapper.OrderMapper;
import com.alexlasota.online_shop_order_service.model.Order;
import com.alexlasota.online_shop_order_service.model.OrderAttribute;
import com.alexlasota.online_shop_order_service.model.OrderItem;
import com.alexlasota.online_shop_order_service.model.OrderStatus;
import com.alexlasota.online_shop_order_service.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    void createOrder_ValidOrderDTO_OrderCreated() {
        OrderDTO inputOrderDTO = new OrderDTO();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        OrderAttributeDTO orderAttributeDTO = new OrderAttributeDTO();
        inputOrderDTO.setItems(List.of(orderItemDTO));
        inputOrderDTO.setAttributes(List.of(orderAttributeDTO));

        Order order = new Order();
        order.setId(1L);
        OrderItem orderItem = new OrderItem();
        OrderAttribute orderAttribute = new OrderAttribute();

        when(orderMapper.toEntity(inputOrderDTO)).thenReturn(order);
        when(orderMapper.toEntity(orderItemDTO)).thenReturn(orderItem);
        when(orderMapper.toEntity(orderAttributeDTO)).thenReturn(orderAttribute);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(inputOrderDTO);

        OrderDTO result = orderService.createOrder(inputOrderDTO);

        assertNotNull(result);
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(orderMapper).toEntity(orderItemDTO);
        verify(orderMapper).toEntity(orderAttributeDTO);
    }

    @Test
    void getOrderHistory_ValidPageAndSize_ReturnsPageOfOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        Page<Order> orderPage = new PageImpl<>(orders);

        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(orderPage);
        when(orderMapper.toDTO(any(Order.class))).thenReturn(new OrderDTO());

        Page<OrderDTO> result = orderService.getOrderHistory(0, 10);

        assertEquals(2, result.getContent().size());
        verify(orderRepository).findAll(any(PageRequest.class));
    }

    @Test
    void getOrderById_ExistingId_ReturnsOrder() {
        Order order = new Order();
        order.setId(1L);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getOrderById_NonExistingId_ThrowsOrderNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void updateOrderStatus_ExistingOrder_StatusUpdated() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);

        assertNotNull(result);
        assertEquals(OrderStatus.SHIPPED, result.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_NonExistingOrder_ThrowsOrderNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus(1L, OrderStatus.SHIPPED));
    }
}