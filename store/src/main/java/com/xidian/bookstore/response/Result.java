package com.xidian.bookstore.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Result extends ResponseMsg {
    protected Map<String,Object> data;
    public Result(){

    }
    public Result(long code,String msg,Map data){
        this.respCode = code;
        this.respMsg = msg;
        this.data = data;
    }
}
