package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.*;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Category;
import com.xidian.bookstore.entities.book.Collect;
import com.xidian.bookstore.entities.book.Tag;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TagRepository tagRepository;
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 搜索图书
     * @param object
     * @return
     */
    public ResponseEntity<?> searchBook(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            StringBuilder jpql = new StringBuilder();
            StringBuilder jpqlCount = new StringBuilder();
            StringBuilder jpqlData = new StringBuilder();
            jpqlCount.append("select count(1)");
            jpqlData.append("select b");
            jpql.append(" from Book b where");
            if (JSONPath.contains(object,"$.clause")){
                Map<String,String> clause = (Map<String, String>) JSONPath.eval(object,"$.clause");
                if (clause.size()>0){
                    if (clause.containsKey("categoryId")){
                        String categoryId = clause.get("categoryId");
                        Category category = categoryRepository.findByCategoryId(Integer.valueOf(categoryId));
                        jpql.append(String.format(" b.category.categoryId = '%s'",Integer.valueOf(categoryId)));
                    }
                    if (clause.containsKey("tagId")){
                        String tagId = clause.get("tagId");
                        if (clause.containsKey("categoryId")){
                            jpql.append(String.format(" and b.tag.tagId = '%s'",Integer.valueOf(tagId)));
                        }else {
                            jpql.append(String.format(" b.tag.tagId = '%s'",Integer.valueOf(tagId)));
                        }
                    }
                    if (clause.containsKey("bookName")){
                        if (clause.containsKey("categoryId")||clause.containsKey("tagId")){
                            jpql.append(String.format(" and b.bookName like '%c%s%c'",'%',clause.get("bookName"),'%'));
                        }else {
                            jpql.append(String.format(" b.bookName like '%c%s%c'",'%',clause.get("bookName"),'%'));
                        }
                    }
                }
            }
            if (JSONPath.contains(object,"$.like")){

            }
            int page = 0;
            int size = 100;
            if (JSONPath.contains(object,"$.filter")){
                Map<String,String> filter = (Map<String, String>) JSONPath.eval(object,"$.filter");
                if (filter.size()>0){
                    if (filter.containsKey("page")){
                        page = Integer.parseInt(filter.get("page"));
                    }
                    if (filter.containsKey("size")){
                        page = Integer.parseInt(filter.get("size"));
                    }
                }
            }
            Pageable pageable = PageRequest.of(page,size);
            String sqlCount = jpqlCount.append(jpql).toString();
            Query queryCount= entityManager.createQuery(sqlCount);//查询符合条件的所有记录数
            Number number = (Number)queryCount.getSingleResult();
            long totalCount = number.longValue();
            if (totalCount==0){
                PageImpl p = new PageImpl(new ArrayList(),pageable,0);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                        ResponseCode.BOOK_NULL.getMsg()),HttpStatus.OK);
            }else {
                String field = "price";
                String order = "asc";
                if (JSONPath.contains(object,"$.orderBy")){
                    Map<String,String> orderBy = (Map<String, String>) JSONPath.eval(object,"$.orderBy");
                    if (orderBy.size()>0){
                        if(orderBy.containsKey("field")){
                            field = orderBy.get("field").toLowerCase();
                        }
                        if(orderBy.containsKey("order")){
                            order = orderBy.get("order").toLowerCase();
                        }
                    }
                }
                jpql.append(String.format(" order by b.%s %s",field,order));
                int offset = (int) pageable.getOffset();
                int limit = page*size + size;
                String sql = jpqlData.append(jpql).toString();
                Query query= entityManager.createQuery(sql);
                query.setFirstResult(offset);
                query.setMaxResults(limit);
                List list = query.getResultList();
                PageImpl pageImpl = new PageImpl(list, pageable,totalCount);
                Map<String,Object> map = new HashMap<>();
                map.put("totalCount",totalCount);
                map.put("totalPage",pageImpl.getTotalPages());
                map.put("currentCount",size);
                map.put("currentPage",page);
                map.put("books",list);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
            }


        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 上传图书
     * @param object
     * @return
     */
    public ResponseEntity<?> upLoadBook(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        Book book = new Book();
        try{
            if (JSONPath.contains(object,"$.bookName")){
                String bookName = JSONPath.eval(object,"$.bookName").toString();
                book.setBookName(bookName);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.price")){
                String price = JSONPath.eval(object,"$.price").toString();
                BigDecimal price1 = new BigDecimal(price);
                book.setPrice(price1);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.publisherName")){
                String publisherName = JSONPath.eval(object,"$.publisherName").toString();
                book.setPublisherName(publisherName);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.author")){
                String author = JSONPath.eval(object,"$.author").toString();
                book.setAuthor(author);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.inventory")){
                Integer inventory = (Integer) JSONPath.eval(object,"$.inventory");
                book.setInventory(inventory);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.intro")){
                String intro = JSONPath.eval(object,"$.intro").toString();
                book.setIntro(intro);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.categoryId")){
                Integer categoryId = (Integer) JSONPath.eval(object,"$.categoryId");
                Category category = categoryRepository.findByCategoryId(categoryId);
                if (category!=null){
                    book.setCategory(category);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                            ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.tagId")){
                Integer tagId = (Integer) JSONPath.eval(object,"$.tagId");
                Tag tag = tagRepository.findByTagId(tagId);
                if (tag!=null){
                    book.setTag(tag);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.TAG_NULL.getCode(),
                            ResponseCode.TAG_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long count = bookRepository.count();
        book.setBookId((int) (count+1));
        Timestamp timestamp = new Timestamp(new Date().getTime());
        book.setCreatime(timestamp);
        book.setBrowseNumber(0);
        book.setPurchaseNumber(0);
        bookRepository.save(book);
        Map<String,String> map = new HashMap<>();
        map.put("bookId",String.valueOf((int) (count+1)));
        responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 更新图书
     * @param object
     * @return
     */
    public ResponseEntity<?> updateBook(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.bookId")){
                Integer bookId = (Integer) JSONPath.eval(object,"$.bookId");
                Book book = bookRepository.findByBookId(bookId);
                if (book!=null){
                    if (JSONPath.contains(object,"$.bookName")){
                        String bookName = JSONPath.eval(object,"$.bookName").toString();
                        book.setBookName(bookName);
                    }
                    if (JSONPath.contains(object,"$.price")){
                        BigDecimal price = (BigDecimal) JSONPath.eval(object,"$.price");
                        book.setPrice(price);
                    }
                    if (JSONPath.contains(object,"$.publisherName")){
                        String publisherName = JSONPath.eval(object,"$.publisherName").toString();
                        book.setPublisherName(publisherName);
                    }
                    if (JSONPath.contains(object,"$.author")){
                        String author = JSONPath.eval(object,"$.author").toString();
                        book.setAuthor(author);
                    }
                    if (JSONPath.contains(object,"$.inventory")){
                        Integer inventory = (Integer) JSONPath.eval(object,"$.inventory");
                        book.setInventory(inventory);
                    }
                    if (JSONPath.contains(object,"$.intro")){
                        String intro = JSONPath.eval(object,"$.intro").toString();
                        book.setIntro(intro);
                    }
                    if (JSONPath.contains(object,"$.categoryId")){
                        Integer categoryId = (Integer) JSONPath.eval(object,"$.categoryId");
                        Category category = categoryRepository.findByCategoryId(categoryId);
                        if (category!=null){
                            book.setCategory(category);
                        }else {
                            return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CATEGORY_NULL.getCode(),
                                    ResponseCode.CATEGORY_NULL.getMsg()), HttpStatus.OK);
                        }
                    }
                    if (JSONPath.contains(object,"$.tagId")){
                        Integer tagId = (Integer) JSONPath.eval(object,"$.tagId");
                        Tag tag = tagRepository.findByTagId(tagId);
                        if (tag!=null){
                            book.setTag(tag);
                        }else {
                            return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.TAG_NULL.getCode(),
                                    ResponseCode.TAG_NULL.getMsg()), HttpStatus.OK);
                        }
                    }
                    if (JSONPath.contains(object,"$.browseNumber")){
                        Integer browseNumber = (Integer) JSONPath.eval(object,"$.browseNumber");
                        book.setBrowseNumber(browseNumber);
                    }
                    if (JSONPath.contains(object,"$.purchaseNumber")){
                        Integer purchaseNumber = (Integer) JSONPath.eval(object,"$.purchaseNumber");
                        book.setPurchaseNumber(purchaseNumber);
                    }
                    bookRepository.save(book);
                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除图书
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteBook(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.bookId")){
                List ids = (List) JSONPath.eval(object,"$.bookId");
                for (Object id : ids){
                    bookRepository.deleteByBookId((Integer) id);
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
