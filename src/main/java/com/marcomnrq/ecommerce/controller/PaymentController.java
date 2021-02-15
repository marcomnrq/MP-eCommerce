package com.marcomnrq.ecommerce.controller;

import com.marcomnrq.ecommerce.resource.MercadopagoResource;
import com.marcomnrq.ecommerce.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public MercadopagoResource generatePreference(){
        return paymentService.generatePreference();
    }

    @PostMapping("/notifications")
    public ResponseEntity paymentNotification(@RequestParam String parameters, @RequestBody String body){
        paymentService.paymentNotification(parameters, body);
        return ResponseEntity.ok(200);
    }
}
