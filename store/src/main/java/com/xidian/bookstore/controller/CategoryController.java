package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.entities.book.Category;
import com.xidian.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody JSONObject object){
        return categoryService.deleteCategory(object);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> queryCategory(@RequestParam String id){
        return categoryService.getCategory(id);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> queryCategories(){
        return categoryService.getCategories();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCategory(@RequestBody JSONObject object){
        return categoryService.updateCategory(object);
    }
}
