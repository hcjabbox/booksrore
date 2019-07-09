package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.BookRepository;
import com.xidian.bookstore.dao.CollectRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Collect;
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
public class CollectService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CollectRepository collectRepository;

    /**
     * 收藏图书
     * @param object
     * @return
     */
    public ResponseEntity<?> addCollect(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        Collect collect = new Collect();
        try{
            if (JSONPath.contains(object,"$.userId")){
                String userId = JSONPath.eval(object,"$.userId").toString();
                User user = userRepository.findByUserId(Long.valueOf(userId));
                if (user!=null){
                    collect.setUser(user);
                }else{
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.bookId")){
                Integer bookId = (Integer) JSONPath.eval(object,"$.bookId");
                Book book = bookRepository.findByBookId(bookId);
                if (book!=null){
                    collect.setBook(book);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.description")){
                String description = JSONPath.eval(object,"$.description").toString();
                collect.setDescription(description);
            }
            long count = collectRepository.count();
            collect.setCollectId((int) count+1);
            collect.setCreatime(new Timestamp(new Date().getTime()));
            collectRepository.save(collect);
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
     * 查询图书收藏
     * @param userId 用户id
     * @return
     */
    public ResponseEntity<?> queryCollect(String userId){
        ResponseEntity<?> responseEntity = null;
        try{
            User user = userRepository.findByUserId(Long.valueOf(userId));
            if (user!=null){
                List<Collect> collects = collectRepository.findAllByUser(user);
                Map<String,List<Map<String,String>>> map = new HashMap<>();
                List<Map<String,String>> list = new ArrayList<>();
                for (Collect collect : collects){
                    Map<String,String> collectMap = new HashMap<>();
                    collectMap.put("collectId",String.valueOf(collect.getCollectId()));
                    collectMap.put("bookId",String.valueOf(collect.getBook().getBookId()));
                    collectMap.put("createTime",String.valueOf(collect.getCreatime()));
                    collectMap.put("description",String.valueOf(collect.getDescription()));
                    list.add(collectMap);
                }
                map.put("collects",list);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                        ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 批量删除图书收藏
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteCollect(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.collectId")){
                List list = (List) JSONPath.eval(object,"$.collectId");
                for (Object id : list){
                   collectRepository.deleteByCollectId((int)id);
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
     * 更新收藏图书信息
     * @param object
     * @return
     */
    public ResponseEntity<?> updateCollect(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.collectId")){
                Integer collectId = (Integer) JSONPath.eval(object,"$.collectId");
                Collect collect = collectRepository.findByCollectId(collectId);
                if (collect!=null){
                    if (JSONPath.contains(object,"$.description")){
                        String description = JSONPath.eval(object,"$.description").toString();
                        collect.setDescription(description);
                        collectRepository.save(collect);
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                    }else {
                        return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
                    }
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                            ResponseCode.FAILED.getMsg()), HttpStatus.OK);
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
