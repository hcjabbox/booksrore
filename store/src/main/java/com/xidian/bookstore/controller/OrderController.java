package com.xidian.bookstore.controller;


import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@RequestBody JSONObject object){
        return orderService.createOrder(object);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOrder(@RequestBody JSONObject object){
        return orderService.updateOrder(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteOrder(@RequestBody JSONObject object){
        return orderService.deleteOrder(object);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> queryTag(@RequestParam String status){

        return orderService.getOrder(status);
    }

}
