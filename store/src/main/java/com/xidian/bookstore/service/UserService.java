package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.MoneyRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.dao.VerificationCodeRepository;
import com.xidian.bookstore.entities.payment.Money;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.VerificationCode;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import com.xidian.bookstore.utils.SendEmailUtil;
import com.xidian.bookstore.utils.TimeUtil;
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
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationCodeRepository codeRepository;
    @Autowired
    SendEmailUtil sendEmailUtil;
    @Autowired
    MoneyRepository moneyRepository;

    /**
     * 用户注册
     * @param object Json
     * @return 返回体
     */
    public ResponseEntity<?> addUser(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.email")){
                String email = JSONPath.eval(object,"$.email").toString();
                User user = userRepository.getByEmail(email);
                if(user!=null){
                    responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_EXISTED.getCode(),ResponseCode.USER_EXISTED.getMsg()),HttpStatus.OK);
                }else{
                    if(JSONPath.contains(object,"$.code")){
                        String code = JSONPath.eval(object,"$.code").toString();
                        VerificationCode verificationCode = codeRepository.getByEmail(email);
                        if(verificationCode!=null&&verificationCode.getCode().equals(code)){
                            if(JSONPath.contains(object,"$.receiveTime")){
                                String receiveTime = JSONPath.eval(object,"$.receiveTime").toString();
                                long time = Long.valueOf(receiveTime)-verificationCode.getCreateTime();
                                if(time<=90000){
                                    if(JSONPath.contains(object,"$.password")){
                                        String password = JSONPath.eval(object,"$.password").toString();
                                        User u = new User();
                                        long count = userRepository.count();//查询数据库中有多少个user
                                        u.setUserId(count+1);//将新添加的用户的编号设为数据库中最大用户编号加一
                                        u.setPassword(password);
                                        Date date = new Date();
                                        Timestamp timestamp = new Timestamp(date.getTime());
                                        u.setRegTime(timestamp);
                                        u.setEmail(email);
                                        Money money = new Money();
                                        money.setBalance(new BigDecimal("0"));
                                        money.setMoneyId((int) (moneyRepository.count()+1));
                                        money.setUpdateTime(timestamp);
                                        money.setUser(u);
                                        u.setMoney(money);
                                        moneyRepository.save(money);
                                        userRepository.save(u);
                                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
                                        codeRepository.deleteByEmail(email);
                                    }else {
                                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                                    }
                                }else {
                                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CODE_EXPIRED.getCode(),ResponseCode.CODE_EXPIRED.getMsg()),HttpStatus.OK);
                                }
                            }else {
                                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                            }
                        }else {
                            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CODE_WRONG.getCode(),ResponseCode.CODE_WRONG.getMsg()),HttpStatus.OK);
                        }
                    }else{
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                    }
                }
            }else{
                responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        } catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getMsg()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 用户登录
     * @param object JSON对象
     * @return 返回体
     */
    public ResponseEntity<?> login(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.email")){
                String email = JSONPath.eval(object,"$.email").toString();
                User user = userRepository.getByEmail(email);
                if(user==null){
                    responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                            ResponseCode.USER_NOTS.getMsg()),HttpStatus.OK);
                }else {
                    if(JSONPath.contains(object,"$.password")){
                        String password = JSONPath.eval(object,"$.password").toString();
                        if(user.getPassword().equals(password)){
                            Map<String,Object> map = new HashMap<>();
                            map.put("userId",user.getUserId());
                            responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                                    ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
                        }else {
                            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PASSWORD_WRONG.getCode(),
                                    ResponseCode.PASSWORD_WRONG.getMsg()),HttpStatus.OK);
                        }
                    }else{
                        responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                    }
                }
            }else {
                responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 更新用户的信息
     * @param user 用户
     * @return 返回体
     */
    public ResponseEntity<?> updateUser(User user){
        ResponseEntity<?> responseEntity = null;
        try {
            User user1 = userRepository.findByUserId(user.getUserId());//查询出要更新信息的user
            if(user1!=null){
                if(user.getImage()!=null){
                    user1.setImage(user.getImage());
                }
                if(user.getMobile()!=null){
                    user1.setMobile(user.getMobile());
                }
                if(user.getSex()!=null){
                    user1.setSex(user.getSex());
                }
                if(user.getUname()!=null){
                    user1.setUname(user.getUname());
                }
                userRepository.save(user1);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
            }else{
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),
                        ResponseCode.USER_NOTS.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity =   new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    /**
     * 查询用户信息
     * @param u
     * @return 返回体
     */
    public ResponseEntity<?> getUser(User u){
        ResponseEntity<?> responseEntity = null;
        try {
            User user = null;
            if(u!=null){
                if (u.getUserId()!=null){
                    user = userRepository.findByUserId(u.getUserId());
                    Map<String,Object> map = new HashMap<>();
                    map.put("userId",user.getUserId());
                    map.put("uname",user.getUname());
                    map.put("email",user.getEmail());
                    map.put("mobile",user.getMobile());
                    map.put("sex",user.getSex());
                    map.put("image",user.getImage());
                    map.put("regTime",user.getRegTime());
                    responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                            ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
                }else {
                    if (u.getEmail()!=null){
                        user = userRepository.getByEmail(u.getEmail());
                        Map<String,Object> map = new HashMap<>();
                        map.put("userId",user.getUserId());
                        map.put("uname",user.getUname());
                        map.put("email",user.getEmail());
                        map.put("mobile",user.getMobile());
                        map.put("sex",user.getSex());
                        map.put("image",user.getImage());
                        map.put("regTime",user.getRegTime());
                        responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                                ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
                    }
                }
            }else{
                responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getMsg()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

//    /**
//     * 返回一个用户列表集合
//     * @return 返回体
//     */
//    public ResponseEntity<?> getUsers(){
//        ResponseEntity<?> responseEntity = null;
//        List<User> users = new ArrayList<>();
//        try{
//            users =  userRepository.findAll();
//            responseEntity = new ResponseEntity<>(resultMap,HttpStatus.OK);
//        }catch (Exception e){
//            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//          return responseEntity;
//    }

//    /**
//     * 删除用户
//     * @param userId 用户id
//     * @return 返回体
//     */
//    public ResponseEntity<?> deleteUser(String userId){
//        ResponseEntity<?> responseEntity = null;
//        try{
//            userRepository.deleteByUserId(Integer.valueOf(userId));
//            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
//                    ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
//        }catch(Exception e){
//            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(), ResponseCode.SUCCESS.getMsg()),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return responseEntity;
//    }

    /**
     * 修改密码
     * @param object JSON对象
     * @return 返回体
     */
    public ResponseEntity<?> modifyPassword(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.userId")){
                String userId = JSONPath.eval(object,"$.userId").toString();
                User user = userRepository.findByUserId(Long.valueOf(userId));
                if(user!=null){
                    if(JSONPath.contains(object,"$.oldPassword")){
                        String password =JSONPath.eval(object,"$.oldPassword").toString();
                        if(user.getPassword().equals(password)){
                            if(JSONPath.contains(object,"$.newPassword")){
                                String newPassword =JSONPath.eval(object,"$.newPassword").toString();
                                user.setPassword(newPassword);
                                userRepository.save(user);
                                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                        ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
                            }else {
                                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                            }

                        }else{
                            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PASSWORD_WRONG.getCode(),
                                    ResponseCode.PASSWORD_WRONG.getMsg()),HttpStatus.OK);
                        }
                    }else {
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                    }
                }else{
                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                            ResponseCode.FAILED.getMsg()),HttpStatus.OK);
                }
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 重置密码
     * @param object Json对象
     * @return
     */
    public ResponseEntity<?> resetPassword(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if(JSONPath.contains(object,"$.email")){
                String email = JSONPath.eval(object,"$.email").toString();
                User user = userRepository.getByEmail(email);
                if(user==null){
                    responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),ResponseCode.USER_NOTS.getMsg()),HttpStatus.OK);
                }else{
                    if(JSONPath.contains(object,"$.code")){
                        String code = JSONPath.eval(object,"$.code").toString();
                        VerificationCode verificationCode = codeRepository.getByEmail(email);
                        if(verificationCode!=null&&verificationCode.getCode().equals(code)){
                            if(JSONPath.contains(object,"$.receiveTime")){
                                String receiveTime = JSONPath.eval(object,"$.receiveTime").toString();
                                long time = verificationCode.getCreateTime()-Long.valueOf(receiveTime);
                                if(time<=60){
                                    if(JSONPath.contains(object,"$.password")){
                                        String password = JSONPath.eval(object,"$.password").toString();
                                        user.setPassword(password);
                                        userRepository.save(user);
                                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                                                ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
                                        codeRepository.deleteByEmail(email);
                                    }else {
                                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                                ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                                    }
                                }else {
                                    responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CODE_EXPIRED.getCode(),
                                            ResponseCode.CODE_EXPIRED.getMsg()),HttpStatus.OK);
                                }
                            }else {
                                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                            }
                        }else {
                            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.CODE_WRONG.getCode(),
                                    ResponseCode.CODE_WRONG.getMsg()),HttpStatus.OK);
                        }
                    }else{
                        responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                                ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
                    }
                }
            }else{
                responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity =  new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 发送重置密码邮箱验证码
     * @param email 收件人的邮箱
     * @return 返回体
     */
    public ResponseEntity<?> sendResetCode(String email){
        ResponseEntity<?> responseEntity = null;
        try{
            User user = userRepository.getByEmail(email);
            if(user!=null){
                VerificationCode code = new VerificationCode();
                String randomCode = SendEmailUtil.getVerificationCode();
                final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
                sb.append("<h2>"+"亲爱的"+email+"您好！</h2>").append("<p style='text-align: center; font-size: 24px; font-weight: bold'>" +
                        "您的重置密码验证码为:"+randomCode+" "+"</p>");
                sendEmailUtil.sendMail("验证码","网上书城",true,sb.toString(),true,email);
                code.setCode(randomCode);
                code.setCreateTime(TimeUtil.getLongTime(new Date()));
                code.setEmail(email);
                codeRepository.save(code);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);

            }else{
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_NOTS.getCode(),ResponseCode.USER_NOTS.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 发送注册邮箱验证码
     * @param email 收件人的邮箱
     * @return 返回体
     */
    public ResponseEntity<?> sendRegisterCode(String email){
        ResponseEntity<?> responseEntity = null;
        try{
            User user = userRepository.getByEmail(email);
            if(user==null){
                VerificationCode code = new VerificationCode();
                String randomCode = SendEmailUtil.getVerificationCode();
                final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
                sb.append("<h2>"+"亲爱的"+email+"您好！</h2>").append("<p style='text-align: center; font-size: 24px; font-weight: bold'>" +
                        "您的注册验证码为:"+randomCode+" "+"</p>");
                sendEmailUtil.sendMail("验证码","网上书城",true,sb.toString(),true,email);
                code.setCode(randomCode);
                code.setCreateTime(TimeUtil.getLongTime(new Date()));
                code.setEmail(email);
                codeRepository.save(code);

                responseEntity = new ResponseEntity<>(new  ResponseMsg(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);

            }else{
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.USER_EXISTED.getCode(),ResponseCode.USER_EXISTED.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getMsg()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
