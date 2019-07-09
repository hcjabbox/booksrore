package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.dao.LogisticsRepository;
import com.xidian.bookstore.entities.order.Logistics;
import com.xidian.bookstore.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/logistics")
public class LogisticsController {
    @Autowired
    LogisticsService logisticsService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createLogistics(@RequestBody JSONObject object){
        return logisticsService.createLogistics(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteLogistics(@RequestBody JSONObject object){
        return logisticsService.deleteLogistics(object);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getList(){

        return logisticsService.getList();
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getLogistics(@RequestParam String id){

        return logisticsService.getLogistics(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateLogistics(@RequestBody Logistics logistics){
        return logisticsService.updateLogistics(logistics);
    }
}
