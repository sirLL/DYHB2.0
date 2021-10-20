package com.wareroom.lib_http.response;


import com.google.gson.annotations.SerializedName;

public class Response<T> {
    private String code;    // 返回的code
    @SerializedName("returndata")
    private T data;      // 具体的数据结果
    private String msg;  // message 可用来返回接口的说明

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
