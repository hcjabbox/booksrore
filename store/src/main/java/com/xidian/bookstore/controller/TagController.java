package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createTag(@RequestBody JSONObject object){
        return tagService.createTag(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteTag(@RequestBody JSONObject object){
        return tagService.deleteTag(object);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> queryTag(@RequestParam String id){
        return tagService.getTag(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateTag(@RequestBody JSONObject object){
        return tagService.updateTag(object);
    }
}
