package com.example.shoapp.services;

import java.util.List;

import com.example.shoapp.dtos.OrderDetailDTO;
import com.example.shoapp.models.OrderDetail;

public interface IOderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetailDTO) throws Exception;

    OrderDetail getOrderDetail(Long id) throws Exception;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;

    void deleteOrderDetail(Long id);

    List<OrderDetail> findByOrderId(Long orderId);
}
