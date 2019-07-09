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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 添加二级标签tag
     * @param object
     * @return
     */
    public ResponseEntity<?> createTag(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        Tag tag = new Tag();
        try {
            if (JSONPath.contains(object,"$.categoryId")) {
                Integer id = (Integer) JSONPath.eval(object, "$.categoryId");
                Category category = categoryRepository.findByCategoryId(id);
                tag.setCategory(category);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.description")){
                String description = JSONPath.eval(object, "$.description").toString();
                tag.setDescription(description);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.name")){
                String name = JSONPath.eval(object, "$.name").toString();
                tag.setName(name);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            tag.setCreatime(new Timestamp(new Date().getTime()));
            long count = tagRepository.count();
            tag.setTagId((int)count+1);
            tagRepository.save(tag);
            Map<String,String> map = new HashMap<>();
            map.put("tagId",String.valueOf((int)count+1));
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
     * 更新tag标签
     * @param object
     * @return
     */
    public ResponseEntity<?> updateTag(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Tag tag = null;
            if (JSONPath.contains(object,"$.tagId")){
                Integer tagId = (Integer) JSONPath.eval(object,"$.tagId");
                tag = tagRepository.findByTagId(tagId);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.name")){
                if (tag!=null){
                    String name = JSONPath.eval(object,"$.name").toString();
                    tag.setName(name);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.TAG_NULL.getCode(),
                            ResponseCode.TAG_NULL.getMsg()), HttpStatus.OK);
                }
            }
            if (JSONPath.contains(object,"$.description")){
                if (tag!=null){
                    String description = JSONPath.eval(object,"$.description").toString();
                    tag.setDescription(description);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.TAG_NULL.getCode(),
                            ResponseCode.TAG_NULL.getMsg()), HttpStatus.OK);
                }
            }
            tagRepository.save(tag);
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
     * 删除tag标签
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteTag(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.tagId")){
                List list = (List) JSONPath.eval(object,"$.tagId");
                for (Object id : list){
                    tagRepository.deleteByTagId((Integer) id);
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
     * 查询标签的信息
     * @param tagId
     * @return
     */
    public ResponseEntity<?> getTag(String tagId){
        ResponseEntity<?> responseEntity = null;
        try {
            Tag tag = tagRepository.findByTagId(Integer.valueOf(tagId));
            if (tag!=null){
                Map<String,Object> map = new HashMap<>();
                map.put("name",tag.getName());
                map.put("description",tag.getDescription());
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.TAG_NULL.getCode(),
                        ResponseCode.TAG_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
