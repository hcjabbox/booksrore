package com.xidian.bookstore.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseMsg {
    protected long respCode;
    protected String respMsg;
    public ResponseMsg(){};
    public ResponseMsg(long code, String msg)
    {
        this.respCode = code;
        this.respMsg = msg;
    }

}
