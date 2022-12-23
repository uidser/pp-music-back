package com.uidser.ppmusic.common.r;

public class R<T> {

    private Integer code;
    private T data;
    private String msg;
    private Boolean flag;

    public R setCode(Integer code) {
        this.code = code;
        return this;
    }

    public R setDate(T date) {
        this.data = date;
        return this;
    }


    public R setFlag(Boolean flag) {
        this.flag = flag;
        return this;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R ok() {
        this.msg = "success";
        this.code = 200;
        this.flag = true;
        return this;
    }

    public R<T> ok(T data) {
        this.msg = "success";
        this.code = 200;
        this.flag = true;
        this.data = data;
        return this;
    }

    public R error() {
        this.msg = "error";
        this.code = 201;
        this.flag = false;
        return this;
    }

    public Integer getCode() {
        return code;
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

    public Boolean getFlag() {
        return flag;
    }
}
