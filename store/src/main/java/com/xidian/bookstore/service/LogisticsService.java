package com.xidian.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xidian.bookstore.dao.LogisticsRepository;
import com.xidian.bookstore.dao.OrderRepository;
import com.xidian.bookstore.entities.order.Logistics;
import com.xidian.bookstore.entities.order.Order;
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
public class LogisticsService {
    @Autowired
    LogisticsRepository logisticsRepository;
    @Autowired
    OrderRepository orderRepository;

    /**
     * 给已付款的订单创建物流实体，绑定物流信息
     * @param object
     * @return
     */
    public ResponseEntity<?> createLogistics(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            Logistics logistics = new Logistics();
            Order order = null;
            if (JSONPath.contains(object,"$.orderId")){
                Integer id = (Integer) JSONPath.eval(object,"$.orderId");
                order = orderRepository.findByOrderId(id);
                if (order!=null){
                    logistics.setOrder(order);
                    order.setStatus(3);
                }else {
                    return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.ORDEER_NULL.getCode(),
                            ResponseCode.ORDEER_NULL.getMsg()), HttpStatus.OK);
                }
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.companyName")){
                String companyName = JSONPath.eval(object,"$.companyName").toString();
                logistics.setCompanyNme(companyName);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.logisticsNumber")){
                String logisticsNumber = JSONPath.eval(object,"$.logisticsNumber").toString();
                logistics.setLogisticsNumber(logisticsNumber);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.deliverTime")){
                String deliverTime = JSONPath.eval(object,"$.deliverTime").toString();
                logistics.setDeliverTime(deliverTime);
            }else {
                return responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.PARAM_NULL.getCode(),
                        ResponseCode.PARAM_NULL.getMsg()), HttpStatus.OK);
            }
            if (JSONPath.contains(object,"$.description")){
                String description = JSONPath.eval(object,"$.description").toString();
                logistics.setDescription(description);
            }
            logistics.setLogisticsId((int) (logisticsRepository.count()+1));
            logistics.setCreateTime(new Timestamp(new Date().getTime()));
            if (order!=null){
                order.setLogistics(logistics);
                orderRepository.save(order);
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
     * 批量删除物流实体
     * @param object
     * @return
     */
    public ResponseEntity<?> deleteLogistics(JSONObject object){
        ResponseEntity<?> responseEntity = null;
        try{
            if (JSONPath.contains(object,"$.logisticsId")){
                List list = (List) JSONPath.eval(object,"$.logisticsId");
                for (Object id : list){
                    logisticsRepository.deleteByLogisticsId((Integer) id);
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
     * 查询物流信息
     * @return
     */
    public ResponseEntity<?> getLogistics(String logisticsId){
        ResponseEntity<?> responseEntity = null;
        try{
            Logistics logistics = logisticsRepository.findByLogisticsId(Integer.valueOf(logisticsId));
            Map<String,String> map = new HashMap<>();
            if (logistics!=null){
                map.put("logisticsId",String.valueOf(logistics.getLogisticsId()));
                map.put("logisticsNumber",logistics.getLogisticsNumber());
                map.put("deliverTime",logistics.getDeliverTime());
                map.put("companyName",logistics.getCompanyNme());
                map.put("description",logistics.getDescription());
                map.put("createTime",String.valueOf(logistics.getCreateTime()));
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
     * 获取所有物流实体的编号id
     * @return
     */
    public ResponseEntity<?> getList(){
        ResponseEntity<?> responseEntity = null;
        try{
            List<Logistics> all = logisticsRepository.findAll();
            Map<String,List<String>> map = new HashMap<>();
            List<String> list = new ArrayList<>();
            for (Logistics logistics : all){
               list.add(String.valueOf(logistics.getLogisticsId()));
            }
            map.put("logisticsId",list);
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
     * 更新物流信息
     * @param logistics
     * @return
     */
    public ResponseEntity<?> updateLogistics(Logistics logistics){
        ResponseEntity<?> responseEntity = null;
        try{
            Logistics log = logisticsRepository.findByLogisticsId(logistics.getLogisticsId());
            if (log!=null){
                if (logistics.getDescription()!=null){
                    log.setDescription(logistics.getDescription());
                }
                if (logistics.getDeliverTime()!=null){
                    log.setDeliverTime(logistics.getDeliverTime());
                }
                if (logistics.getCompanyNme()!=null){
                    log.setCompanyNme(logistics.getCompanyNme());
                }
                if (logistics.getLogisticsNumber()!=null){
                    log.setLogisticsNumber(logistics.getLogisticsNumber());
                }
                logisticsRepository.save(log);
                responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getMsg()), HttpStatus.OK);
            }else {
                 responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.LOGISTICS_NULL.getCode(),
                        ResponseCode.LOGISTICS_NULL.getMsg()), HttpStatus.OK);
            }
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(new ResponseMsg(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;

    }
}
