package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.MoneyRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.payment.Money;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MoneyService {
    @Autowired
    MoneyRepository moneyRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> updateBalance(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.userId")){
                User user = userRepository.findByUserId(Long.valueOf(JSONPath.eval(object,"$.userId").toString()));
                if (user!=null){
                    Money money = moneyRepository.findByUser(user);
//                    if (JSONPath.contains(object,"$.card")&&JSONPath.contains(object,"$.password")){
//
//                    }
                    if (JSONPath.contains(object,"$.money")){
                        BigDecimal balance = money.getBalance();
                        money.setBalance(balance.add(new BigDecimal(String.valueOf(JSONPath.eval(object,"$.money")))));
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                    }else {
                        return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
                    }
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    public ResponseEntity<?> getBalance(String id){
        ResponseEntity<?> responseEntity = null;
        try{
            if (id!=null&&!id.equals("")){
                User user = userRepository.findByUserId(Long.valueOf(id));
                if (user!=null){
                    Money money = moneyRepository.findByUser(user);
                    Map<String,Money> map = new HashMap<>();
                    map.put("balance",money);
                    responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
