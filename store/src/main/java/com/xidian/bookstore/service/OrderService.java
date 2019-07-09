package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.AddressRepository;
import com.xidian.bookstore.dao.BookRepository;
import com.xidian.bookstore.dao.OrderRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.UserAddress;
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
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * 创建订单
     * @param object
     * @return
     */
    public ResponseEntity<?> createOrder(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Order order = new Order();
            Book book = null;
            if (JSONPath.contains(object,"$.bookId")){
                Integer id = (Integer) JSONPath.eval(object,"$.bookId");
                book = bookRepository.findByBookId(id);
                if (book!=null){
                    order.setBook(book);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.amount")){
                Integer amount  = (Integer) JSONPath.eval(object,"$.amount");
                Integer inventory = book.getInventory();
                if (amount<=inventory){
                    order.setAmount(amount);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.INVENTORY_LESS.getCode(),
                            ResponseCode.INVENTORY_LESS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.price")){
                BigDecimal price = (BigDecimal) JSONPath.eval(object,"$.price");
                order.setPrice(price);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.addressId")){
               Integer id = (Integer) JSONPath.eval(object,"$.addressId");
                UserAddress address = addressRepository.findByAddressId(id);
                if (address!=null){
                    order.setUserAddress(address);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ADDRESS_NULL.getCode(),
                            ResponseCode.ADDRESS_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.userId")){
                String id = JSONPath.eval(object,"$.userId").toString();
                User user = userRepository.findByUserId(Long.valueOf(id));
                if (user!=null){
                    order.setUser(user);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.description")){
                String des = JSONPath.eval(object,"$.description").toString();
                order.setDescription(des);
            }
            order.setCreatime(new Timestamp(new Date().getTime()));
            long count = orderRepository.count();
            order.setOrderId((int) (count+1));
            order.setStatus(1);//1表示未付款，订单的初始状态为未付款
            orderRepository.save(order);
            Map<String,String> map = new HashMap<>();
            map.put("orderId",String.valueOf(count+1));
            responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 删除订单，支持批量删除
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteOrder(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.orderId")){
                List list = (List) JSONPath.eval(object,"$.orderId");
                for (Object id : list){
                    orderRepository.deleteByOrderId((Integer) id);
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
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

    /**
     * 根据订单的状态查询订单，状态有0（全部）、1（待付款）、2（待发货）、3（待收货）、4（待评价）
     * @param status
     * @return
     */
    public ResponseEntity<?> getOrder(String status){
        ResponseEntity<?> responseEntity = null;
        try {
            if (status!=null){
                List<Order> orders = orderRepository.findAllByStatus(Integer.valueOf(status));
                Map<String,List<Map<String,String>>> map = new HashMap<>();
                List<Map<String,String>> list = new ArrayList<>();
                for (Order order : orders){
                    Map<String,String> orderMap = new HashMap<>();
                    orderMap.put("orderId",String.valueOf(order.getOrderId()));
                    orderMap.put("bookId",String.valueOf(order.getBook().getBookId()));
                    orderMap.put("addressId",String.valueOf(order.getUserAddress().getAddressId()));
                    orderMap.put("logisticsId",String.valueOf(order.getLogistics().getLogisticsId()));
                    orderMap.put("commentId",String.valueOf(order.getComment().getCommentId()));
                    orderMap.put("paymentId",String.valueOf(order.getPay().getPaymentId()));
                    orderMap.put("amount",String.valueOf(order.getAmount()));
                    orderMap.put("price",String.valueOf(order.getPrice()));
                    orderMap.put("createTime",String.valueOf(order.getCreatime()));
                    orderMap.put("completeTime",String.valueOf(order.getCompleteTime()));
                    orderMap.put("status",String.valueOf(order.getStatus()));
                    list.add(orderMap);
                }
                map.put("orders",list);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
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

    public ResponseEntity<?> updateOrder(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Order order = null;
            if (JSONPath.contains(object,"$.orderId")){
               Integer id = (Integer) JSONPath.eval(object,"$.orderId");
               order = orderRepository.findByOrderId(id);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (order!=null){
                order.setStatus(4);
                orderRepository.save(order);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ORDEER_NULL.getCode(),
                        ResponseCode.ORDEER_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
