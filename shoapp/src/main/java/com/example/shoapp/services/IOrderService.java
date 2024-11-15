package com.example.shoapp.services;

import java.util.List;

import com.example.shoapp.dtos.OrderDTO;
import com.example.shoapp.models.Order;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;

    void deleteOrder(Long id);

    List<Order> findByUser(Long userId);
}
