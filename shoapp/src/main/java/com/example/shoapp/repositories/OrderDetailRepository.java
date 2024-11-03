package com.example.shoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoapp.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
