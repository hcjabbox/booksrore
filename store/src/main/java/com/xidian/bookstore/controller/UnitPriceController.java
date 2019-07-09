package com.xidian.bookstore.controller;

import com.xidian.bookstore.entities.book.UnitPrice;
import com.xidian.bookstore.service.UnitPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/unitPrice")
public class UnitPriceController {
    @Autowired
    UnitPriceService unitPriceService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addPrice(@RequestBody UnitPrice price){
        return unitPriceService.addPrice(price);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<?> queryPrice(){
        return unitPriceService.queryPrice();
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePrice(@RequestBody UnitPrice price){
        return unitPriceService.updatePrice(price);
    }
}
