package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.CategoryRepository;
import com.xidian.bookstore.dao.TagRepository;
import com.xidian.bookstore.entities.book.Category;
import com.xidian.bookstore.entities.book.Tag;
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
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TagRepository tagRepository;

    /**
     * 添加一级分类类别
     * @param category
     * @return
     */
    public ResponseEntity<?> createCategory(Category category){
        ResponseEntity<?> responseEntity = null;
        try {
            if (category!=null){
                long count = categoryRepository.count();
                category.setCategoryId((int) (count+1));
                category.setCreatime(new Timestamp(new Date().getTime()));
                categoryRepository.save(category);
                Map<String,String> map = new HashMap<>();
                map.put("categoryId",String.valueOf(count+1));
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

    /**
     * 删除第一级分类及其包括的第二级分类
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteCategory(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.categoryId")){
                List list = (List) JSONPath.eval(object,"$.categoryId");
                for (Object id : list){
                    categoryRepository.deleteByCategoryId((Integer) id);
                    Category category = categoryRepository.findByCategoryId((Integer) id);
                    Set<Tag> tags = category.getTags();
                    tagRepository.deleteInBatch(tags);
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
     * 查询分类类别的信息
     * @param categoryId
     * @return
     */
    public ResponseEntity<?> getCategory(String categoryId){
        ResponseEntity<?> responseEntity = null;
        try {
            Category category = categoryRepository.findByCategoryId(Integer.valueOf(categoryId));
            if (category!=null){
                Map<String,String> map = new HashMap<>();
                map.put("name",category.getName());
                map.put("description",category.getDescription());
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                        ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 查询所有category的id和其包括的所有的tag的id
     * @return
     */
    public ResponseEntity<?> getCategories(){
        ResponseEntity<?> responseEntity = null;
        try {
            Map<String,List<String>> map = new HashMap<>();
            List<Category> categories = categoryRepository.findAll();
            for (Category category : categories){
                Set<Tag> tags = category.getTags();
                List<String> list = new ArrayList<>();
                for (Tag tag : tags){
                    list.add(String.valueOf(tag.getTagId()));
                }
                map.put(String.valueOf(category.getCategoryId()),list);
            }
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
     * 更新类别的信息
     * @param object
     * @return
     */
    public ResponseEntity<?> updateCategory(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Category category = null;
            if (JSONPath.contains(object,"$.categoryId")){
                Integer categoryId = (Integer) JSONPath.eval(object,"$.categoryId");
                category = categoryRepository.findByCategoryId(categoryId);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.name")){
                if (category!=null){
                    category.setName(JSONPath.eval(object,"$.name").toString());
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                            ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
                }
            }
            if (JSONPath.contains(object,"$.description")){
                if (category!=null){
                    category.setDescription(JSONPath.eval(object,"$.description").toString());
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                            ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
                }
            }
            if (category!=null){
                categoryRepository.save(category);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                        ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
