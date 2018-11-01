package com.xzy.springbootapi.web.utils;

/**
 * Created by laborc on 2018/6/23.
 */
public class RequestData<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
