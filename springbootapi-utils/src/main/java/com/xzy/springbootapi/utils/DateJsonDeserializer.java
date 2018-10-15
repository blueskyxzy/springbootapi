package com.xzy.springbootapi.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @remark: DateJsonDeserializer
 * 解决jason序列化中的Date 不支持yyyy-MM-dd HH:mm:ss的问题，DTO的字段上增加注解：@JsonDeserialize(using = DateJsonDeserializer.class)
 * @author: xiatl
 * @create: 2018-06-24 15:36
 **/
public class DateJsonDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(parser.getValueAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}