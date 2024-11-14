package com.example.shoapp.services;

import java.util.List;

import com.example.shoapp.dtos.OrderDTO;
import com.example.shoapp.models.Order;
import com.example.shoapp.responses.OrderResponse;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders(Long userId);
}
