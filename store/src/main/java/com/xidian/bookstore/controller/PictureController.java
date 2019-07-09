package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadPicture(@RequestBody JSONObject object){
        return pictureService.uploadPicture(object);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deletePicture(@RequestBody JSONObject object){
        return pictureService.deletePicture(object);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> queryPicture(@RequestParam String id){
        return pictureService.getPicture(id);
    }


}
