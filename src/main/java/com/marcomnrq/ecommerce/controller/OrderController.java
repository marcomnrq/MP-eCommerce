package com.marcomnrq.ecommerce.controller;

import com.marcomnrq.ecommerce.resource.IpnResource;
import com.marcomnrq.ecommerce.resource.MercadopagoResource;
import com.marcomnrq.ecommerce.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public MercadopagoResource createOrder(){
        return orderService.createOrder();
    }

    @PostMapping("/notifications")
    public ResponseEntity paymentNotification(@RequestBody IpnResource ipnResource){
        return ResponseEntity.ok(200);
    }
}
