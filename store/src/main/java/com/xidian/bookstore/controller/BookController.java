package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteBook(@RequestBody JSONObject object){
        return bookService.deleteBook(object);
    }

    @PostMapping(value = "/fuzzysearch")
    public ResponseEntity<?> searchBook(@RequestBody JSONObject object){
        return bookService.searchBook(object);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateBook(@RequestBody JSONObject object){
        return bookService.updateBook(object);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadBook(@RequestBody JSONObject object){
        return bookService.updateBook(object);
    }
}
