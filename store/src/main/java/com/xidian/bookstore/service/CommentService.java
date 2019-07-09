package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.BookRepository;
import com.xidian.bookstore.dao.CommentRepository;
import com.xidian.bookstore.dao.OrderRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Comment;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 评价订单
     * @param object
     * @return
     */
    public ResponseEntity<?> createComment(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Comment comment = new Comment();
            if (JSONPath.contains(object,"$.bookId")){
                Integer id = (Integer) JSONPath.eval(object,"$.bookId");
                Book book = bookRepository.findByBookId(id);
                if (book!=null){
                    comment.setBook(book);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.userId")){
                String id = JSONPath.eval(object,"$.userId").toString();
                User user = userRepository.findByUserId(Long.valueOf(id));
                if (user!=null){
                    comment.setUser(user);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.comment")){
                String com = JSONPath.eval(object,"$.comment").toString();
                comment.setComment(com);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            comment.setCommentId((int) (commentRepository.count()+1));
            comment.setCreateTime(new Timestamp(new Date().getTime()));
            if (JSONPath.contains(object,"$.orderId")){
                Integer id = (Integer) JSONPath.eval(object,"$.orderId");
                Order order = orderRepository.findByOrderId(id);
                if (order!=null){
                    comment.setOrder(order);
                    order.setStatus(0);
                    orderRepository.save(order);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ORDEER_NULL.getCode(),
                            ResponseCode.ORDEER_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
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
     * 通过用户id和图书id查询所有有关的评价
     * @param object
     * @return
     */
    public ResponseEntity<?> getComment(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.bookId")){
                Integer id = (Integer) JSONPath.eval(object,"$.bookId");
                Book book = bookRepository.findByBookId(id);
                Map<String,List<Map<String,String>>> commentMap = new HashMap<>();
                List<Map<String,String>> list = new ArrayList<>();
                if (book!=null){
                    List<Comment> comments = commentRepository.findAllByBook(book);
                    for (Comment comment : comments){
                        Map<String,String> map = new HashMap<>();
                        map.put("userId",String.valueOf(comment.getUser().getUserId()));
                        map.put("bookId",String.valueOf(comment.getBook().getBookId()));
                        map.put("comment",String.valueOf(comment.getComment()));
                        map.put("createTime",String.valueOf(comment.getCreateTime()));
                        list.add(map);
                    }
                    commentMap.put("comments",list);
                    responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg(),commentMap), HttpStatus.OK);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }
            if (JSONPath.contains(object,"$.userId")){
                String id = JSONPath.eval(object,"$.userId").toString();
                User user = userRepository.findByUserId(Long.valueOf(id));
                Map<String,List<Map<String,String>>> commentMap = new HashMap<>();
                List<Map<String,String>> list = new ArrayList<>();
                if (user!=null){
                    List<Comment> comments = commentRepository.findAllByUser(user);
                    for (Comment comment : comments){
                        Map<String,String> map = new HashMap<>();
                        map.put("userId",String.valueOf(comment.getUser().getUserId()));
                        map.put("bookId",String.valueOf(comment.getBook().getBookId()));
                        map.put("comment",String.valueOf(comment.getComment()));
                        map.put("createTime",String.valueOf(comment.getCreateTime()));
                        list.add(map);
                    }
                    commentMap.put("comments",list);
                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
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
