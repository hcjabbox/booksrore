package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.entities.order.ShoppingCart;
import com.xidian.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCart(@RequestBody JSONObject object){
        return cartService.addCart(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteTag(@RequestBody JSONObject object){
        return cartService.deleteCart(object);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> queryTag(@RequestParam String id){

        return cartService.getCart(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateTag(@RequestBody ShoppingCart cart){
        return cartService.updateCart(cart);
    }
}
