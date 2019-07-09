package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.MoneyRepository;
import com.xidian.bookstore.dao.OrderRepository;
import com.xidian.bookstore.dao.PaymentRepository;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.payment.Money;
import com.xidian.bookstore.entities.payment.Pay;
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
import java.util.*;

@Service
@Transactional
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MoneyRepository moneyRepository;

    /**
     * 生成一个支付实体
     * @param object
     * @return
     */
    public ResponseEntity<?> createPayment (JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            Pay pay = new Pay();
            Order order = null;
            if (JSONPath.contains(object,"$.orderId")){
                 order = orderRepository.findByOrderId((Integer) JSONPath.eval(object,"$.orderId"));
                if (order!=null){
                    pay.setOrder(order);
                    order.setStatus(2);
                    BigDecimal price = order.getPrice();
                    BigDecimal balance = moneyRepository.findByUser(order.getUser()).getBalance();
                    if (balance.compareTo(price)>=0){
                        BigDecimal subtract = balance.subtract(price);
                        Money money = moneyRepository.findByUser(order.getUser());
                        money.setBalance(subtract);
                        moneyRepository.save(money);
                    }else {
                        return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.MONEY_LESS.getCode(),
                                ResponseCode.MONEY_LESS.getMsg()), HttpStatus.OK);
                    }
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ORDEER_NULL.getCode(),
                            ResponseCode.ORDEER_NULL.getMsg()), HttpStatus.OK);
                }

            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.type")){
                String type =  JSONPath.eval(object,"$.type").toString();
                pay.setType(type);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            pay.setPayTime(new Timestamp(new Date().getTime()));
            long count = paymentRepository.count();
            pay.setPaymentId((int) (count+1));
            String payNumber = UUID.randomUUID().toString().replaceAll("-","");
            pay.setPaymentNumber(payNumber);
            paymentRepository.save(pay);
            if (order!=null){
                order.setPay(pay);
                orderRepository.save(order);
            }
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }

    /**
     * 批量删除支付实体
     * @param object
     * @return
     */
    public ResponseEntity<?> deletePayment(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.paymentId")){
                List list = (List) JSONPath.eval(object,"$.paymentId");
                for (Object id : list){
                    paymentRepository.deleteByPaymentId((Integer) id);
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                 responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }

    /**
     * 查询单个支付实体的信息
     * @param id 支付实体的编号
     * @return
     */
    public ResponseEntity<?> getPayment(String id){
        ResponseEntity<?> responseEntity = null;
        try{
            if(id!=null){
                Map<String,String> map = new HashMap<>();
                Pay pay = paymentRepository.findByPaymentId(Integer.valueOf(id));
                if (pay!=null){
                    map.put("type",pay.getType());
                    map.put("paymentNumber",pay.getPaymentNumber());
                    map.put("payTime",String.valueOf(pay.getPayTime()));
                }
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }

        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }

    /**
     * 获取所有支付实体的id编号
     * @return
     */
    public ResponseEntity<?> getList(){
        ResponseEntity<?> responseEntity = null;
        try{
            List<Pay> all = paymentRepository.findAll();
            Map<String,List<String>> map = new HashMap<>();
            List<String> list = new ArrayList<>();
            if (all.size()>0) {
                for (Pay pay : all) {
                    list.add(String.valueOf(pay.getPaymentId()));
                }
            }
            map.put("paymentId",list);
            responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }
}
