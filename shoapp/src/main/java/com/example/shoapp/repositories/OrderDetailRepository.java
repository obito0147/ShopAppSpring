package com.example.shoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoapp.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long id);
}
