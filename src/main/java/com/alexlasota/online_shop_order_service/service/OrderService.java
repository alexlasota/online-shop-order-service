package com.alexlasota.online_shop_order_service.service;

import com.alexlasota.online_shop_order_service.dto.OrderAttributeDTO;
import com.alexlasota.online_shop_order_service.dto.OrderDTO;
import com.alexlasota.online_shop_order_service.dto.OrderItemDTO;
import com.alexlasota.online_shop_order_service.exception.OrderNotFoundException;
import com.alexlasota.online_shop_order_service.invoice.Invoice;
import com.alexlasota.online_shop_order_service.invoice.InvoiceService;
import com.alexlasota.online_shop_order_service.mapper.OrderMapper;
import com.alexlasota.online_shop_order_service.model.Order;
import com.alexlasota.online_shop_order_service.model.OrderAttribute;
import com.alexlasota.online_shop_order_service.model.OrderItem;
import com.alexlasota.online_shop_order_service.model.OrderStatus;
import com.alexlasota.online_shop_order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InvoiceService invoiceService;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setItems(new ArrayList<>());
        order.setAttributes(new ArrayList<>());

        order = orderRepository.save(order);

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            OrderItem item = orderMapper.toEntity(itemDTO);
            item.setOrder(order);
            order.getItems().add(item);
        }

        for (OrderAttributeDTO attributeDTO : orderDTO.getAttributes()) {
            OrderAttribute attribute = orderMapper.toEntity(attributeDTO);
            attribute.setOrder(order);
            order.getAttributes().add(attribute);
        }

        order = orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    public Page<OrderDTO> getOrderHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::toDTO);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        order.setStatus(newStatus);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }
    public Order processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        order.setStatus(OrderStatus.PROCESSING);

        Invoice invoice = invoiceService.generateInvoice(order);
        order.setInvoice(invoice);

        return orderRepository.save(order);
    }
}