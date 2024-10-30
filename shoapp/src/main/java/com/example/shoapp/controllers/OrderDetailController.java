package com.example.shoapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoapp.dtos.OrderDetailDTO;
import java.util.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("Them moi order detail thanh cong");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok("getOrderDetail with id " + id);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        // List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderId);
        return ResponseEntity.ok("get order detail with order is: " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailDTO) {
        return ResponseEntity.ok("update order detail with id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok("delete Order detail with id " + id);
    }
}
