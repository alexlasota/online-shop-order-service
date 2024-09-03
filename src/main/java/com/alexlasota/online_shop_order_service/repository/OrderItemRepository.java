package com.alexlasota.online_shop_order_service.repository;

import com.alexlasota.online_shop_order_service.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}