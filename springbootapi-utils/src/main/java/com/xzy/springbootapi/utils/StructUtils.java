package com.xzy.springbootapi.utils;

import com.nmw.ourtoken.vo.BaseVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by laborc on 2018/4/26.
 */
public class StructUtils {
    private final static Logger logger = LoggerFactory.getLogger(StructUtils.class);
    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static JSONObject filterModel(Object model, JSONObject struct) {
        JSONObject jsonObject = new JSONObject();
        Iterator<Map.Entry<String, JSONObject>> iterator = struct.entrySet().iterator();
        Map<String, Field> fieldMap = getField(model.getClass());
        while (iterator.hasNext()) {
            Map.Entry<String, JSONObject> next = iterator.next();
            String key = next.getKey();
            Object objValue = next.getValue();
            try {
                if (model instanceof Map) {
                    if (((Map) model).containsKey(key)) {
                        Object mapObj = ((Map) model).get(key);
                        if (mapObj == null) {
                            jsonObject.put(key, "");
                        } else {
                            Class typeClass = mapObj.getClass();
                            if (List.class.isAssignableFrom(mapObj.getClass())) {
                                List list = (List) mapObj;
                                JSONObject value = next.getValue();
                                JSONArray array = new JSONArray();
                                for (Object obj : list) {
                                    if (obj instanceof String) {
                                        array.add(obj);
                                    } else {
                                        JSONObject jsonObj = filterModel(obj, value);
                                        array.add(jsonObj);
                                    }
                                }
                                jsonObject.put(key, array);
                            } else if (Date.class.isAssignableFrom(mapObj.getClass())) {
                                Date date = (Date) mapObj;
                                if(StringUtils.isEmpty(objValue)){
                                    jsonObject.put(key, DateUtil.dateToString(date, StructUtils.dateFormat));
                                }else{
                                    jsonObject.put(key, DateUtil.dateToString(date, objValue.toString()));
                                }
                            } else if (Map.class.isAssignableFrom(mapObj.getClass())) {
                                jsonObject.put(key, JSONObject.fromObject(mapObj));
                            } else if (BaseVO.class.isAssignableFrom(typeClass)) {
                                JSONObject value = next.getValue();
                                JSONObject jsonObj = filterModel(mapObj, value);
                                jsonObject.put(key, jsonObj);
                            } else {
                                jsonObject.put(key, mapObj);
                            }
                        }
                    }
                } else {
                    Field field = fieldMap.get(key);
                    if (field != null) {
                        field.setAccessible(true);
                        Class typeClass = field.getType();
                        if (typeClass.equals(Short.class)
                                || typeClass.equals(Boolean.class) || typeClass.equals(String.class)
                                || typeClass.equals(Double.class) || typeClass.equals(Integer.class)
                                || typeClass.equals(Long.class) || typeClass.equals(BigDecimal.class)) {
                            Object obj = field.get(model);
                            if (obj == null || "null".equals(obj)) {
                                jsonObject.put(key, "");
                            } else {
                                jsonObject.put(key, obj);
                            }

                        } else if (typeClass.equals(Date.class)) {
                            Date date = (Date) field.get(model);
                            if(StringUtils.isEmpty(objValue)){
                                jsonObject.put(key, DateUtil.dateToString(date, StructUtils.dateFormat));
                            }else{
                                jsonObject.put(key, DateUtil.dateToString(date, objValue.toString()));
                            }
                        } else if (typeClass.equals(List.class)) {
                            JSONObject value = next.getValue();
                            List list = (List) field.get(model);
                            if (list == null) {
                                continue;
                            }
                            JSONArray array = new JSONArray();
                            for (Object obj : list) {
                                JSONObject jsonObj = filterModel(obj, value);
                                array.add(jsonObj);
                            }
                            jsonObject.put(key, array);
                        } else if (BaseVO.class.isAssignableFrom(typeClass)) {
                            JSONObject value = next.getValue();
                            Object obj = field.get(model);
                            if (obj != null) {
                                JSONObject jsonObj = filterModel(obj, value);
                                jsonObject.put(key, jsonObj);
                            }
                        } else if (Map.class.isAssignableFrom(typeClass)) {
                            Object obj = field.get(model);
                            jsonObject.put(key, obj);
                        } else {
                            throw new Exception(key + " Type not defined");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return jsonObject;
    }

    public static Map<String, Field> getField(Class _class) {
        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        if (_class.getSuperclass() != null) {
            Map<String, Field> superFieldMap = getField(_class.getSuperclass());
            fieldMap.putAll(superFieldMap);
        }
        return fieldMap;
    }
}
