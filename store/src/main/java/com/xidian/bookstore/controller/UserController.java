package com.xidian.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store/v1.0/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody JSONObject object){
        return userService.addUser(object);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JSONObject object){
        return userService.login(object);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateInformation(@RequestBody User user){
        return userService.updateUser(user);
    }

    @PostMapping(value = "/get")
    public ResponseEntity queryUser(@RequestBody User user){
        return userService.getUser(user);
    }

//    @DeleteMapping(value = "/delete")
//    public ResponseEntity deleteUser(@RequestParam("userId") String userId){
//        return userService.deleteUser(userId);
//    }
    @PostMapping(value = "/registerCode")
    public ResponseEntity<?> sendRegisterCode(@RequestBody JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.email")){
                String email = JSONPath.eval(object,"$.email").toString();
                responseEntity = userService.sendRegisterCode(email);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()),HttpStatus.OK);
        }
        return responseEntity;
    }
    @PostMapping(value = "/resetCode")
    public  ResponseEntity<?> sendResetCode(@RequestBody JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.email")){
                String email = JSONPath.eval(object,"$.email").toString();
                responseEntity = userService.sendResetCode(email);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()),HttpStatus.OK);
        }
        return responseEntity;
    }

    @PostMapping(value = "/modify")
    public ResponseEntity<?> modifyPassword(@RequestBody JSONObject object){
        return userService.modifyPassword(object);
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<?> resetPassword(@RequestBody JSONObject object){
        return userService.resetPassword(object);
    }
}
