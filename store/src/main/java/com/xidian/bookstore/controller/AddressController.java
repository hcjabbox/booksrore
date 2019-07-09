package com.xidian.bookstore.controller;


import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.entities.user.UserAddress;
import com.xidian.bookstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addAdress(@RequestBody JSONObject request){
        return addressService.addAddress(request);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getAddress(@RequestParam("userId") String userId){
        return addressService.getAdresses(userId);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateAddress(@RequestBody UserAddress userAddress){
        return addressService.updateAddress(userAddress);

    }
    @PostMapping(value = "/delete")
    public ResponseEntity deleteAddress(@RequestBody JSONObject object){
        return addressService.deleteAddress(object);

    }
}
