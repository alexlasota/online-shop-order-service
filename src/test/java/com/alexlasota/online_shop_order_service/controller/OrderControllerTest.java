package com.alexlasota.online_shop_order_service.controller;

import com.alexlasota.online_shop_order_service.dto.OrderDTO;
import com.alexlasota.online_shop_order_service.model.OrderStatus;
import com.alexlasota.online_shop_order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test
    void createOrder_OrderCreated_OrderReturned() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setUserId(1L);
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void getOrderHistory_OrdersExist_OrdersReturned() throws Exception {
        OrderDTO order1 = new OrderDTO();
        order1.setId(1L);
        OrderDTO order2 = new OrderDTO();
        order2.setId(2L);
        when(orderService.getOrderHistory(0, 10))
                .thenReturn(new PageImpl<>(List.of(order1, order2)));

        mockMvc.perform(get("/api/v1/orders")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

    @Test
    void getOrderById_OrderExists_OrderReturned() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setUserId(1L);
        when(orderService.getOrderById(1L)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/v1/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L));
    }

//    @Test
//    void updateOrderStatus_OrderExists_UpdatedOrderReturned() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);
//        orderDTO.setStatus(OrderStatus.SHIPPED);
//        when(orderService.updateOrderStatus(1L, OrderStatus.SHIPPED)).thenReturn(orderDTO);
//
//        mockMvc.perform(put("/api/v1/orders/{id}/status", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("\"SHIPPED\""))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.status").value("SHIPPED"));
//    }
}