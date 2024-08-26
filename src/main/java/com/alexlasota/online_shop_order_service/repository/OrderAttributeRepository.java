package com.alexlasota.online_shop_order_service.repository;

import com.alexlasota.online_shop_order_service.model.OrderAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAttributeRepository extends JpaRepository<OrderAttribute, Long> {
}
