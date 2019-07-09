package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.BookRepository;
import com.xidian.bookstore.dao.CartRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.order.ShoppingCart;
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
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 将图书添加至购物车
     * @param object
     * @return
     */
    public ResponseEntity<?> addCart(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Book book = null;
            ShoppingCart cart = new ShoppingCart();
            if (JSONPath.contains(object,"$.bookId")){
                book = bookRepository.findByBookId((Integer) JSONPath.eval(object,"$.bookId"));
                if (book!=null){
                    cart.setBook(book);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.userId")){
                User user = userRepository.findByUserId(Long.valueOf(JSONPath.eval(object,"$.userId").toString()));
                if (user!=null){
                    cart.setUser(user);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.amount")){
                Integer amount  = (Integer) JSONPath.eval(object,"$.amount");
                Integer inventory = book.getInventory();
                if (amount<=inventory){
                    cart.setAmount(amount);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.INVENTORY_LESS.getCode(),
                            ResponseCode.INVENTORY_LESS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.price")){
                cart.setPrice((BigDecimal) JSONPath.eval(object,"$.price"));
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            long count = cartRepository.count();
            cart.setCartId((int) (count+1));
            cart.setCreatime(new Timestamp(new Date().getTime()));
            cartRepository.save(cart);
            Map<String,String> map = new HashMap<>();
            map.put("cartId",String.valueOf(count+1));
            responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 更新购物车的状态
     * @param cart 购物车实体
     * @return
     */
    public ResponseEntity<?> updateCart(ShoppingCart cart){
        ResponseEntity<?> responseEntity = null;
        try {
            if (cart!=null){
                Integer cartId = cart.getCartId();
                ShoppingCart cart1 = cartRepository.findByCartId(cartId);
                cart1.setPrice(cart.getPrice());
                cart1.setAmount(cart.getAmount());
                cartRepository.save(cart1);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 批量删除购物车
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteCart(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.cartId")){
                List list = (List) JSONPath.eval(object,"$.cartId");
                for (Object id : list){
                    cartRepository.deleteByCartId((Integer) id);
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询所有购物车信息
     * @param userId
     * @return
     */
    public ResponseEntity<?> getCart(String userId){
        ResponseEntity<?> responseEntity = null;
        try {
            if (userId!=null){
                User user = userRepository.findByUserId(Long.valueOf(userId));
                if (user!=null){
                    Set<ShoppingCart> carts = user.getCarts();
                    Map<String,List<Map<String,String>>> map = new HashMap<>();
                    List<Map<String,String>> list = new ArrayList<>();
                    for (ShoppingCart cart : carts){
                        Map<String,String> cartMap = new HashMap<>();
                        cartMap.put("cartId",String.valueOf(cart.getCartId()));
                        cartMap.put("bookId",String.valueOf(cart.getBook().getBookId()));
                        cartMap.put("amount",String.valueOf(cart.getAmount()));
                        cartMap.put("price",String.valueOf(cart.getPrice()));
                        cartMap.put("createTime",String.valueOf(cart.getCreatime()));
                        list.add(cartMap);
                    }
                    map.put("carts",list);
                    responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
