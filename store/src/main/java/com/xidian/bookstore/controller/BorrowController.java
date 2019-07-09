package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.entities.book.Borrow;
import com.xidian.bookstore.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/borrow")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBorrow(@RequestBody JSONObject object){
        return borrowService.addBorrow(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteBorrow(@RequestBody JSONObject object){
        return borrowService.deleteBorrow(object);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> queryBorrow(@RequestParam(value = "userId") String id){
        return borrowService.queryBorrow(id);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<?> queryBorrows(){
        return borrowService.queryBorrows();
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateBorrow(@RequestBody Borrow borrow){
        return borrowService.updateBorrow(borrow);
    }
}
