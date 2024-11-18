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
import com.example.shoapp.models.OrderDetail;
import com.example.shoapp.responses.OrderDetailResponse;
import com.example.shoapp.services.IOderDetailService;

import java.util.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOderDetailService oderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = oderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws Exception {
        OrderDetail orderDetail = oderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = oderDetailService.findByOrderId(orderId);
        // khi stream xong de tra ve gia tri cu thi phai tolist()
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(orderDetail -> OrderDetailResponse
                        .fromOrderDetail(orderDetail))
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailDTO) {
        try {
            OrderDetail orderDetail = oderDetailService.updateOrderDetail(id, newOrderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        oderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("delete order detail with id : " + id);
    }
}
