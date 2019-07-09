package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.AddressRepository;
import com.xidian.bookstore.dao.UserRepository;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.UserAddress;
import com.xidian.bookstore.response.ResponseCode;
import com.xidian.bookstore.response.ResponseMsg;
import com.xidian.bookstore.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    /**
     *  添加一个用户收货地址
     * @param request JSON对象
     * @return 返回体
     */
    public ResponseEntity<?> addAddress(JSONObject request){
        ResponseEntity<?> responseEntity = null;
        UserAddress address =  new UserAddress();
        try{
            User user = null;
            if(JSONPath.contains(request,"$.userId")){
               String id = JSONPath.eval(request, "$.userId").toString();
                user = userRepository.findByUserId(Long.valueOf(id));//根据userId查询出会员
                if(user!=null){
                    address.setUser(user);//将用户和用户地址绑定
                }else {
                   return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                            ResponseCode.FAILED.getMsg()),HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.province")){
                address.setProvince(JSONPath.eval(request, "$.province").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.region")){
                address.setRegion(JSONPath.eval(request, "$.region").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.addr")){
                address.setAddr(JSONPath.eval(request, "$.addr").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.city")){
                address.setCity(JSONPath.eval(request, "$.city").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.receiver")){
                address.setReceiver(JSONPath.eval(request, "$.receiver").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.mobile")){
                address.setMobile(JSONPath.eval(request, "$.mobile").toString());
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            if (JSONPath.contains(request,"$.flag")){
                String flag = JSONPath.eval(request, "$.flag").toString();
                if (flag.equals("true")){
                    UserAddress userAddress = addressRepository.findByFlag("true");
                    if(userAddress!=null){
                        userAddress.setFlag("false");
                        addressRepository.save(userAddress);
                        address.setFlag(flag);
                    }else {
                        address.setFlag(flag);
                    }
                }else {
                    address.setFlag(flag);
                }
            }else{
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            address.setCreatime(timestamp);
            long count = addressRepository.count();//查询数据库中有多少个address
            address.setAddressId((int) count+1);//将新添加的address的编号设为数据库中最大address编号加一
            addressRepository.save(address);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("addressId",(Integer.valueOf((int) count)+1));
            responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMsg(),resultMap),HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }



    /**
     *  删除一个会员地址
     * @param object JSON对象
     * @return 返回体
     */
    public ResponseEntity<?> deleteAddress(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try {
            if (JSONPath.contains(object,"$.addressId")){
                List list = (List) JSONPath.eval(object,"$.addressId");
                for(Object addressId : list){
                    String id = addressId.toString();
                    addressRepository.deleteByAddressId(Integer.valueOf(id));
                }
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()),HttpStatus.OK);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()),HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
     * 更新用户地址
     * @param address 收货地址
     * @return 返回体
     */
    public ResponseEntity<?> updateAddress(UserAddress address){
        ResponseEntity<?> responseEntity = null;
        UserAddress addr = null;
        try {
            if (address.getAddressId()!=null){
                addr = addressRepository.findByAddressId(address.getAddressId());
            }
            if (addr!=null){
                if (address.getMobile()!=null){
                    addr.setMobile(address.getMobile());
                }
                if (address.getAddr()!=null){
                    addr.setAddr(address.getAddr());
                }
                if (address.getReceiver()!=null){
                    addr.setReceiver(address.getReceiver());
                }
                if (address.getRegion()!=null){
                    addr.setRegion(address.getRegion());
                }
                if (address.getCity()!=null){
                    addr.setCity(address.getCity());
                }
                if (address.getProvince()!=null){
                    addr.setProvince(address.getProvince());
                }
                if (address.getFlag()!=null){
                    if (address.getFlag().equals("true")){
                        UserAddress userAddress = addressRepository.findByFlag("true");
                        if(userAddress!=null){
                            userAddress.setFlag("false");
                            addressRepository.save(userAddress);
                            addr.setFlag(address.getFlag());
                        }else {
                            addr.setFlag(address.getFlag());
                        }
                    }else {
                        addr.setFlag(address.getFlag());
                    }
                }
                addressRepository.save(addr);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ADDRESS_NULL.getCode(),
                    ResponseCode.ADDRESS_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }

    /**
     * 查询用户收货地址
     * @param userId 用户编号id
     * @return 返回体
     */
    public ResponseEntity<?> getAdresses(String userId){
        ResponseEntity<?> responseEntity = null;
        try {
            List<UserAddress> addresses = new ArrayList<>();
            Map<String,List<UserAddress>> map = new HashMap<>();
            User user = userRepository.findByUserId(Long.valueOf(userId));
            addresses = addressRepository.findUserAddressByUser(user);
            if (addresses!=null){
                map.put("addresses",addresses);
                responseEntity = new ResponseEntity<>(new Result(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg(),map),HttpStatus.OK);
            }else {
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ADDRESS_NULL.getCode(),
                        ResponseCode.ADDRESS_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
}
