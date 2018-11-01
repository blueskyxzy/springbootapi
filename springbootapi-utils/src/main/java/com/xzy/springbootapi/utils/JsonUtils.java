package com.xzy.springbootapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by xzy on 18/6/6  .
 */

public class JsonUtils {
    static Logger logger = LogManager.getLogger("");

    /**
     * convert object to json string
     * @param json json object
     * @param <T> json object type
     * @return json string
     */
    public static <T> String toJson(T json) {
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteEnumUsingToString);
    }


    /**
     * parse given array json to object list with given type
     * @param json string in json array format, "[]"
     * @param type type class
     * @param <T> class type
     * @return null if parsing failed
     */
    public static <T> List<T> parseJsonArray(String json, Class<T> type) {

        try {
            return JSON.parseArray(json, type);
        } catch( Exception exp ) {
            logger.error("failed to parse json array:" + json, exp);
            return null;
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if(json == null || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    public static Object parseOjbect(String json) {
        if(json ==  null) {
            return null;
        }
        return JSON.parse(json);
    }
}
