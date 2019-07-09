package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.entities.order.Logistics;
import com.xidian.bookstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/pay")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPayment(@RequestBody JSONObject object){
        return paymentService.createPayment(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deletePayment(@RequestBody JSONObject object){
        return paymentService.deletePayment(object);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getList(){

        return paymentService.getList();
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getPayment(@RequestParam String id){

        return paymentService.getPayment(id);
    }
}
