package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/collect")
public class CollectController {
    @Autowired
    CollectService collectService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCollect(@RequestBody JSONObject object){
        return collectService.addCollect(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteCollect(@RequestBody JSONObject object){
        return collectService.deleteCollect(object);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> queryCollect(@RequestParam String userId){
        return collectService.queryCollect(userId);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCollect(@RequestBody JSONObject object){
        return collectService.updateCollect(object);
    }


}
