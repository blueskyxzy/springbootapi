package com.xzy.springbootapi.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzy.springbootapi.utils.HttpRestUtils;
import com.xzy.springbootapi.utils.ResultMessageBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by xzy on 18/6/6  .
 */

public abstract class BaseRestCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRestCtrl.class);

    public static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd";
    public static final String REQUEST_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static Map<String, JSONObject> structCache = new HashMap<>();

    protected String getJsonPath() {
        String name = this.getClass().getSimpleName();
        name = name.replace(".", "/");
        return "/com/nmw/ourtoken/json/" + name + ".json";
    }

    protected String buildUrl(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        path = path.replace(contextPath, "");
        StringBuffer sb = new StringBuffer();

        //过滤请求后缀如.json .xml等
        if (path.contains(".")) {
            path = path.split("\\.")[0];
        }
        //过滤数字
        String[] pathArray = path.split("/");
        for (String p : pathArray) {
            if (!StringUtils.isBlank(p)) {
                sb.append("/");
                Pattern pattern = Pattern.compile("[0-9]*");
                if (pattern.matcher(p).matches()) {
                    sb.append("*");
                } else {
                    sb.append(p);
                }
            }
        }
        String method = request.getMethod();
        String _method = request.getParameter("_method");
        sb.append(":");
        if (!StringUtils.isBlank(_method)) {
            sb.append(_method.toLowerCase());
        } else {
            sb.append(method.toLowerCase());
        }
        return sb.toString();
    }

    /**
     * convert object to json string
     *
     * @param json json object
     * @param <T>  json object type
     * @return json string
     */
    public static <T> String toJson(T json) {
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * build success json with given data
     *
     * @param data data
     * @param <T>  data type
     * @return success message string
     */
    public static <T> String buildSuccessMessage(T data) {

        return toJson(ResultMessageBuilder.success(data));
    }

    /**
     * build error json with buildErrorMessage code and buildErrorMessage message
     *
     * @param code    buildErrorMessage code
     * @param message error message
     * @return buildErrorMessage message string
     */
    public static String buildErrorMessage(int code, String message) {
        return toJson(ResultMessageBuilder.error(code, message));
    }

    /**
     * build error json with buildErrorMessage message
     *
     * @param message error message
     * @return error message string
     */
    public static String buildErrorMessage(String message) {
        return toJson(ResultMessageBuilder.error(message));
    }

    /**
     * write success message to response
     *
     * @param response http response
     * @param data     data
     * @param <T>      data type
     */
    public static <T> void writeSuccess(HttpServletResponse response, T data) {
        HttpRestUtils.writeJsonObject(response, ResultMessageBuilder.success(data));
    }

    /**
     * write error message to response
     *
     * @param response http response
     * @param code     error code
     * @param message  error message
     */
    public static void writeError(HttpServletResponse response, int code, String message) {
        HttpRestUtils.writeJsonObject(response, ResultMessageBuilder.error(code, message));
    }

    public static <T> void writeMeta(HttpServletResponse response, T data, int pageIndex, int pageSize) {
        HttpRestUtils.writeJsonObject(response, ResultMessageBuilder.meta(data, pageIndex, pageSize));
    }

    /**
     * write error message to response
     *
     * @param response http response
     * @param message  error message
     */
    public static void writeError(HttpServletResponse response, String message) {
        HttpRestUtils.writeJsonObject(response, ResultMessageBuilder.error(message));
    }

    /**
     * parse given array json to object list with given type
     *
     * @param json string in json array format, "[]"
     * @param type type class
     * @param <T>  class type
     * @return null if parsing failed
     */
    protected final <T> List<T> parseJsonArray(String json, Class<T> type) {

        try {
            return JSON.parseArray(json, type);
        } catch (Exception exp) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            if (logger != null) {
                logger.error("failed to parse json array:" + json, exp);
            }
            return null;
        }
    }

    /**
     * parse given array json to object list with given type
     *
     * @param json string in json array format, "[]"
     * @param type type class
     * @param <T>  class type
     * @return null if parsing failed
     */
    protected final <T> List<T> parseJsonArray(String json, Class<T> type, FieldValidator<T> validator) {

        List<T> dataList = parseJsonArray(json, type);

        if (dataList == null) {
            return null;
        }

        boolean passed = true;
        for (T t : dataList) {
            if (!validator.validate(t)) {
                passed = false;
                break;
            }
        }

        return passed ? dataList : null;
    }

    protected interface FieldValidator<T> {

        boolean validate(T t);
    }


    /**
     * split the complex string with separator
     *
     * @param complex   source string eg: 1,2,3
     * @param separator separator string eg: , | ; : etc
     * @param converter converter
     * @param validator validator
     * @param <T>       target type
     * @return object list with given type
     */
    protected static final <T> List<T> splitString(
            String complex,
            String separator,
            Converter<String, T> converter,
            FieldValidator<T> validator
    ) {
        List<T> dataList = new ArrayList<>();
        if (StringUtils.isBlank(complex)) {
            T t = converter.convert(complex);
            if (validator.validate(t)) {
                dataList.add(t);
            }

            return dataList;
        }

        String[] arr = StringUtils.split(complex, separator);
        for (String str : arr) {

            T t = converter.convert(str);
            if (validator.validate(t)) {
                dataList.add(t);
            } else {
                dataList.clear();
                break;
            }
        }

        return dataList;
    }

    public static Date parseDate(String strDate) {
        try {
            return new SimpleDateFormat(REQUEST_DATE_FORMAT).parse(strDate);
        } catch (Exception e) {
            LOGGER.error("Error when parsing date [{}]", strDate);
            return null;
        }
    }

    public static Date parseDate(String strDate, HttpServletResponse response, int errorCode, String errorMessage) {
        try {
            return new SimpleDateFormat(REQUEST_DATE_FORMAT).parse(strDate);
        } catch (Exception e) {
            writeError(response, errorCode, errorMessage);
            LOGGER.error("Error when parsing date [{}]", strDate);
            return null;
        }
    }

    public static Date parseDateTime(String strDate) {
        try {
            return new SimpleDateFormat(REQUEST_DATE_TIME_FORMAT).parse(strDate);
        } catch (Exception e) {
            LOGGER.error("Error when parsing date [{}]", strDate);
            return null;
        }
    }

    public static Date parseDateTime(String strDate, HttpServletResponse response, int errorCode, String errorMessage) {
        try {
            return new SimpleDateFormat(REQUEST_DATE_TIME_FORMAT).parse(strDate);
        } catch (Exception e) {
            writeError(response, errorCode, errorMessage);
            LOGGER.error("Error when parsing date [{}]", strDate);
            return null;
        }
    }

    public static BigDecimal parseBigDecimal(String strValue) {
        try {
            return new BigDecimal(strValue);
        } catch (Exception e) {
            LOGGER.error("Error when parsing big decimal [{}]", strValue);
            return null;
        }
    }

    public static BigDecimal parseBigDecimal(String strValue, HttpServletResponse response, int errorCode, String errorMessage) {
        try {
            return new BigDecimal(strValue);
        } catch (Exception e) {
            writeError(response, errorCode, errorMessage);
            LOGGER.error("Error when parsing big decimal [{}]", strValue);
            return null;
        }
    }

    public static boolean allNull(Object... parameters) {
        if (parameters == null) {
            return true;
        }
        for (Object obj : parameters) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }


    /**
     * 校验数据必填
     *
     * @param obj      dto
     * @param response res
     * @return bool
     */
    protected <T> boolean checkData(T obj, HttpServletResponse response) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(obj) == null) {
                    writeError(response, 900, "请求参数不对");
                    return false;
                }
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
                writeError(response, 900, "请求参数不对");
                return false;
            }
        }
        return true;
    }

    /**
     * 国际化消息
     *
     * @param message message
     * @return str
     */
    protected String getMessage(String message) {
        return getMessage(message, null);
    }

    /**
     * 国际化消息
     *
     * @param message message
     * @return str
     */
    protected String getMessage(String message, Object[] params) {
        return messageSource.getMessage(message, params, LocaleContextHolder.getLocale());
    }

    @Autowired
    private MessageSource messageSource;

}
