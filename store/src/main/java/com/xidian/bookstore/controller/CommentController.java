package com.xidian.bookstore.controller;


import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> createCommennt(@RequestBody JSONObject object){
        return commentService.createComment(object);
    }

    @PostMapping(value = "/list")
    public ResponseEntity<?> queryComment(@RequestBody JSONObject object){
        return commentService.getComment(object);
    }
}
