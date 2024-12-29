package com.example.shoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shoapp.dtos.OrderDetailDTO;
import com.example.shoapp.exceptions.DataNotFoundException;
import com.example.shoapp.models.Order;
import com.example.shoapp.models.OrderDetail;
import com.example.shoapp.models.Product;
import com.example.shoapp.repositories.OrderDetailRepository;
import com.example.shoapp.repositories.OrderRepository;
import com.example.shoapp.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOderDetailService {
        private final OrderRepository orderRepository;
        private final OrderDetailRepository orderDetailRepository;
        private final ProductRepository productRepository;

        @Override
        @Transactional
        public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetailDTO) throws Exception {
                // kiem tra order co ton tai khong
                Order order = orderRepository.findById(newOrderDetailDTO.getOrderId())
                                .orElseThrow(() -> new DataNotFoundException("Cannot find order"));
                // kiem tra product co ton tai khong
                Product product = productRepository.findById(newOrderDetailDTO.getProductId())
                                .orElseThrow(() -> new DataNotFoundException("Cannot find product"));

                OrderDetail orderDetail = new OrderDetail().builder()
                                .order(order)
                                .product(product)
                                .price(newOrderDetailDTO.getPrice())
                                .numberOfProducts(newOrderDetailDTO.getNumberOfProducts())
                                .totalMoney(newOrderDetailDTO.getTotalMoney())
                                .color(newOrderDetailDTO.getColor())
                                .build();
                return orderDetailRepository.save(orderDetail);
        }

        @Override
        public OrderDetail getOrderDetail(Long id) throws Exception {
                return orderDetailRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Cannot find orderDetail"));

        }

        @Override
        @Transactional
        public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception {
                // kiem tra orderdetail co ton tai khong
                OrderDetail orderDetail = orderDetailRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Cannot find orderdetail"));
                Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                                .orElseThrow(() -> new DataNotFoundException("Cannot find order"));
                Product product = productRepository.findById(orderDetailDTO.getProductId())
                                .orElseThrow(() -> new DataNotFoundException("Cannot find product"));

                orderDetail.setPrice(orderDetailDTO.getPrice());
                orderDetail.setColor(orderDetailDTO.getColor());
                orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
                orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
                orderDetail.setPrice(orderDetailDTO.getPrice());
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                return orderDetailRepository.save(orderDetail);

        }

        @Override
        @Transactional
        public void deleteOrderDetail(Long id) {
                orderDetailRepository.deleteById(id);
        }

        @Override
        public List<OrderDetail> findByOrderId(Long orderId) {
                return orderDetailRepository.findByOrderId(orderId);
        }

}
