package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.BookRepository;
import com.xidian.bookstore.dao.PictureRepositiory;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Picture;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PictureService {
    @Autowired
    PictureRepositiory pictureRepositiory;
    @Autowired
    BookRepository bookRepository;

    /**
     * 上传图书的图片，可上传多个转成base64字符串的图片
     * @param object
     * @return
     */
    public ResponseEntity<?> uploadPicture(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            Book book = null;
            if (JSONPath.contains(object,"$.bookId")){
               book = bookRepository.findByBookId((Integer) JSONPath.eval(object,"$.bookId"));
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.pictureString")){
                List list = (List) JSONPath.eval(object,"$.pictureString");
                for (Object s : list){
                    String base64String = s.toString();
                    Picture picture = new Picture();
                    picture.setPictureString(base64String);
                    picture.setPictureId((int) (pictureRepositiory.count()+1));
                    if (book!=null){
                        picture.setBook(book);
                    }else {
                        return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                                ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                    }
                    pictureRepositiory.save(picture);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                        ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
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
     * 批量删除图书的图片
     * @param object
     * @return
     */
    public ResponseEntity<?> deletePicture(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.pictureId")){
                List list = (List) JSONPath.eval(object,"$.pictureId");
                for (Object id : list){
                    pictureRepositiory.deleteByPictureId((Integer) id);
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                        ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 查询图书的图片信息
     * @param bookId
     * @return
     */
    public ResponseEntity<?> getPicture(String bookId){
        ResponseEntity<?> responseEntity = null;
        try {
            if (bookId!=null&&bookId!=""){
                Book book = bookRepository.findByBookId(Integer.valueOf(bookId));
                if (book!=null){
                    List<Picture> pictures = pictureRepositiory.findAllByBook(book);
                    Map<String,String> map = new HashMap<>();
                    for (Picture picture : pictures){
                        map.put(String.valueOf(picture.getPictureId()),picture.getPictureString());
                    }
                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
                            ResponseCode.BOOK_NULL.getMsg()), HttpStatus.OK);
                }

            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
