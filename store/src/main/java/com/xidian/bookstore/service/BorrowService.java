package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.configuration.BorrowSettings;
import com.xidian.bookstore.dao.*;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Borrow;
import com.xidian.bookstore.entities.book.UnitPrice;
import com.xidian.bookstore.entities.payment.Money;
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
import java.util.*;

@Service
@Transactional
public class BorrowService {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BorrowSettings borrowSettings;
    @Autowired
    MoneyRepository moneyRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UnitPriceRepository priceRepository;

    /**
     * 借阅图书
     * @param object
     * @return
     */
    public ResponseEntity<?> addBorrow(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            Borrow borrow = new Borrow();
            if (JSONPath.contains(object,"$.addressId")){
                UserAddress address = addressRepository.findByAddressId((Integer) JSONPath.eval(object,"$.addressId"));
                if (address!=null){
                    borrow.setUserAddress(address);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ADDRESS_NULL.getCode(),
                            ResponseCode.ADDRESS_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.bookId")){
                Book book = bookRepository.findByBookId((Integer) JSONPath.eval(object,"$.bookId"));
                if (book!=null){
                    borrow.setBook(book);
                    if (JSONPath.contains(object,"$.amount")){
                        Integer amount = (Integer) JSONPath.eval(object,"$.amount");
                        if(book.getInventory()<amount){
                            return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.INVENTORY_LESS.getCode(),
                                    ResponseCode.INVENTORY_LESS.getMsg()), HttpStatus.OK);
                        }
                        borrow.setAmount(amount);
                    }else {
                        return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
                    }
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BOOK_NULL.getCode(),
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
                    borrow.setUser(user);
                    if (JSONPath.contains(object,"$.timeLimit")){
                        String limit = JSONPath.eval(object,"$.timeLimit").toString();
                        BigDecimal balance = user.getMoney().getBalance();
                        UnitPrice price = priceRepository.findByStatus("borrow");
                        if (price!=null){
                            BigDecimal unitPrice = price.getPrice();
                            BigDecimal timeLimit = new BigDecimal(limit);
                            BigDecimal decimal = unitPrice.multiply(timeLimit);
                            if (balance.compareTo(decimal)>=0){
                                Money money = user.getMoney();
                                money.setBalance(balance.subtract(decimal));
                                moneyRepository.save(money);
                            }else {
                                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.MONEY_LESS.getCode(),
                                        ResponseCode.MONEY_LESS.getMsg()), HttpStatus.OK);
                            }
                        }
                        borrow.setTimeLimit(limit);

                    }else {
                        return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
                    }
                }else {
                    return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
                }
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            long id = borrowRepository.count();
            borrow.setBorrowId((int) (id+1));
            borrow.setBorrowTime(new Date().getTime());
            borrow.setDamagedDegree(0);
            borrow.setStatus("在借");
            borrowRepository.save(borrow);
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 更新借阅图书的信息，即修改图书借阅状态为归还和归还书时通过选择损坏度来扣费
     * @param b
     * @return
     */
    public ResponseEntity<?> updateBorrow(Borrow b){
        ResponseEntity<?> responseEntity = null;
        try{
            if (b!=null&&b.getDamagedDegree()!=null&&b.getBorrowId()!=null){
                Borrow borrow = borrowRepository.findByBorrowId(b.getBorrowId());
                if (borrow!=null){
                    if (borrow.getDamagedDegree()<b.getDamagedDegree()){
                        Integer number = b.getDamagedDegree()-borrow.getDamagedDegree();
                        UnitPrice unitPrice= priceRepository.findByStatus("damage");
                        if (unitPrice!=null){
                            BigDecimal price = unitPrice.getPrice();
                            BigDecimal total = price.multiply(new BigDecimal(number));
                            Money balance = moneyRepository.findByUser(borrow.getUser());
                            if (balance.getBalance().compareTo(total)>0){
                                balance.setBalance(balance.getBalance().subtract(total));
                                moneyRepository.save(balance);
                            }else {
                                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.MONEY_LESS.getCode(),
                                        ResponseCode.MONEY_LESS.getMsg()), HttpStatus.OK);
                            }
                        }
                        borrow.setStatus("已还");
                        borrow.setDamagedDegree(b.getDamagedDegree());
                        borrowRepository.save(borrow);
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                    }else {
                        borrow.setStatus("已还");
                        borrow.setDamagedDegree(b.getDamagedDegree());
                        borrowRepository.save(borrow);
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
                    }
                }else {
                   return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.BORROW_NULL.getCode(),
                           ResponseCode.BORROW_NULL.getMsg()), HttpStatus.OK);
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

    /**
     * 删除借阅的图书
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteBorrow(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.borrowId")){
                List list = (List) JSONPath.eval(object,"$.borrowId");
                for (Object id : list){
                    borrowRepository.deleteByBorrowId((Integer) id);
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
     * 通过userId来查询借阅图书的信息
     * @param id
     * @return
     */
    public ResponseEntity<?> queryBorrow(String id){
        ResponseEntity<?> responseEntity = null;
        try{
            User user = userRepository.findByUserId(Long.valueOf(id));
            if (user!=null){
                List<Borrow> borrows = borrowRepository.findAllByUser(user);
                Map<String,List<Map<String,String>>> map = new HashMap<>();
                List<Map<String,String>> list = new ArrayList<>();
                for (Borrow borrow : borrows){
                    Map<String,String> borrowMap = new HashMap<>();
                    borrowMap.put("bookId",String.valueOf(borrow.getBook().getBookId()));
                    borrowMap.put("borrowId",String.valueOf(borrow.getBorrowId()));
                    borrowMap.put("amount",String.valueOf(borrow.getAmount()));
                    borrowMap.put("timeLimit",String.valueOf(borrow.getTimeLimit()));
                    borrowMap.put("borrowTime",String.valueOf(borrow.getBorrowTime()));
                    borrowMap.put("status",String.valueOf(borrow.getStatus()));
                    borrowMap.put("damagedDegree",String.valueOf(borrow.getDamagedDegree()));
                    borrowMap.put("addressId",String.valueOf(borrow.getUserAddress().getAddressId()));
                    list.add(borrowMap);
                }
                map.put("borrow",list);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                return  responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                        ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询所有用户借阅图书的信息
     * @return
     */
    public ResponseEntity<?> queryBorrows(){
        ResponseEntity<?> responseEntity = null;
        try{
            List<Borrow> all = borrowRepository.findAll();
            List<Long> userIds = new ArrayList<>();
            Map<String,Map<String,List<Map<String,String>>>> map = new HashMap<>();
            Map<String,List<Map<String,String>>> listMap = new HashMap<>();
            for (Borrow borrow : all){
                Long id = borrow.getUser().getUserId();
                if (!userIds.contains(id)){
                    userIds.add(id);
                }
            }
            if (userIds.size()>0){
                for (Long id : userIds){
                    List<Map<String,String>> list = new ArrayList<>();
                    for (Borrow borrow : all){
                        if (borrow.getUser().getUserId()==id){
                            Map<String,String> borrowMap = new HashMap<>();
                            borrowMap.put("bookId",String.valueOf(borrow.getBook().getBookId()));
                            borrowMap.put("borrowId",String.valueOf(borrow.getBorrowId()));
                            borrowMap.put("amount",String.valueOf(borrow.getAmount()));
                            borrowMap.put("timeLimit",String.valueOf(borrow.getTimeLimit()));
                            borrowMap.put("borrowTime",String.valueOf(borrow.getBorrowTime()));
                            borrowMap.put("status",String.valueOf(borrow.getStatus()));
                            borrowMap.put("damagedDegree",String.valueOf(borrow.getDamagedDegree()));
                            borrowMap.put("addressId",String.valueOf(borrow.getUserAddress().getAddressId()));
                            list.add(borrowMap);
                        }
                    }
                    listMap.put(String.valueOf(id),list);
                }
                map.put("borrows",listMap);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map), HttpStatus.OK);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                        ResponseCode.USER_NOTS.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
