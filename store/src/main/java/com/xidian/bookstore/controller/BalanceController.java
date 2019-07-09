package com.xidian.bookstore.controller;


import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/balance")
public class BalanceController {
    @Autowired
    MoneyService moneyService;

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateBalance(@RequestBody JSONObject object){
        return moneyService.updateBalance(object);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?>queryBalance(@RequestParam String id){
        return moneyService.getBalance(id);
    }
}
