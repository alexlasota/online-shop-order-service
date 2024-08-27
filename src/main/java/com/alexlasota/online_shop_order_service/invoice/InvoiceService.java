package com.alexlasota.online_shop_order_service.invoice;

import com.alexlasota.online_shop_order_service.model.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceService {
    public Invoice generateInvoice(Order order) {
        return Invoice.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalPrice())
                .generationDate(LocalDateTime.now())
                .build();
    }
}