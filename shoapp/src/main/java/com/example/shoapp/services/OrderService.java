package com.example.shoapp.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.shoapp.dtos.OrderDTO;
import com.example.shoapp.exceptions.DataNotFoundException;
import com.example.shoapp.models.Order;
import com.example.shoapp.models.OrderStatus;
import com.example.shoapp.models.User;
import com.example.shoapp.repositories.OrderRepository;
import com.example.shoapp.repositories.UserRepository;
import com.example.shoapp.responses.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // kiem tra user co ton tai khong
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user"));
        // convert orderDTO -> order
        // su dung thu vien model mapper
        // tao mot luong bang anh xa reng de kiem soat viec anh xa
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        // cap nhat cac truong cua don hang tu orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        // SHIPPING DATE PHAI > ORDER DATE
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order"));
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user"));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        // cap nhat cac truong cua don hang tu OrderDTO
        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setUser(existingUser);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(null);
        // khong nen xoa cung chi nen xoa mem
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

}
