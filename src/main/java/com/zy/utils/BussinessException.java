package com.zy.utils;

public class BussinessException extends RuntimeException{

    private String code;

    private String msg;

    public BussinessException(String code,String msg){
         super(code+":"+msg);

         this.code=code;
         this.msg=msg;

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
