package com.alexlasota.online_shop_order_service.repository;

import com.alexlasota.online_shop_order_service.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByOrderByOrderDateDesc(Pageable pageable);

    List<Order> findByUserId(Long userId);
}