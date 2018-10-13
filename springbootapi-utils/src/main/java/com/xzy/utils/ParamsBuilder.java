package com.xzy.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laborc on 2016/11/1.
 */
public class ParamsBuilder {
    private Map<String, Object> params = new HashMap<>();

    private ParamsBuilder() {
    }

    public static ParamsBuilder newBuild() {
        return new ParamsBuilder();
    }

    public ParamsBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public ParamsBuilder rmParam(String key) {
        params.remove(key);
        return this;
    }

    public ParamsBuilder clearParam() {
        params.clear();
        return this;
    }

    public String toString() {
        return new Gson().toJson(this.params);
    }

    public Map<String, Object> build() {
        return params;
    }
}
