package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.xidian.bookstore.dao.UnitPriceRepository;
import com.xidian.bookstore.entities.book.UnitPrice;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UnitPriceService {
    @Autowired
    UnitPriceRepository priceRepository;

    public ResponseEntity<?> addPrice(UnitPrice unitPrice){
        ResponseEntity<?> responseEntity = null;
        try {
            if (unitPrice!=null){
                unitPrice.setPriceId((int) (priceRepository.count()+1));
                unitPrice.setUpdateTime(new Timestamp(new Date().getTime()));
                priceRepository.save(unitPrice);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    public ResponseEntity<?> updatePrice(UnitPrice price){
        ResponseEntity<?> responseEntity = null;
        try {
            if (price!=null){
                UnitPrice unitPrice = priceRepository.findByPriceId(price.getPriceId());
                if (unitPrice!=null){
                    unitPrice.setPrice(price.getPrice());
                    priceRepository.save(unitPrice);
                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.UNITPRICE_NULL.getCode(),
                            ResponseCode.UNITPRICE_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    public ResponseEntity<?> queryPrice(){
        ResponseEntity<?> responseEntity = null;
        try {
            List<UnitPrice> prices = priceRepository.findAll();
            Map<String,List<UnitPrice>> map = new HashMap<>();
            if (prices.size()>0){
                map.put("unitPrice",prices);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                UnitPrice price = new UnitPrice();
                price.setPrice(new BigDecimal("0"));
                price.setUpdateTime(new Timestamp(new Date().getTime()));
                prices.add(price);
                map.put("unitPrice",prices);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
