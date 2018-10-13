package com.xzy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by xzy on 18/6/6  .
 */

public class HttpRestUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRestUtils.class);

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    /**
     * Send the string message back
     *
     * @param response http response
     * @param content string
     */
    public static void writeString(HttpServletResponse response, String content) {
        responseDisableCache(response);
        responseSupportsCORS(response);
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = getWriter(response);
        if (writer == null || content == null)
            return;

        try {
            writer.write(content);
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * Send the ajax JSON response back to the client side.
     * NOTE: The content-type of the response will be set to "application/json".
     *
     * @param response http response
     * @param rawJson raw json string
     */
    public static void writeRawJson(HttpServletResponse response, String rawJson) {
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        writeString(response, rawJson);
    }

    /**
     * Send the ajax response back to the client side.
     *
     * @param writer
     * @param jsonObj
     */
    public static void writeJson(PrintWriter writer, Object jsonObj) {
        if (writer == null || jsonObj == null)
            return;

        try {
            JSON.writeJSONStringTo(jsonObj, writer, SerializerFeature.DisableCircularReferenceDetect);
        } finally {
            writer.close();
        }
    }

    /**
     * Send the ajax response back to the client side (Date object will be formatted as per the given
     * <code>dateFormat</code>).
     *
     * @param writer
     * @param jsonObj
     * @param dateFormat
     */
    public static void writeJsonWithDateFormat(PrintWriter writer, Object jsonObj, String dateFormat) {
        if (writer == null || jsonObj == null || dateFormat == null)
            return;

        try {
            writer.write(JSON.toJSONStringWithDateFormat(jsonObj, dateFormat, SerializerFeature.DisableCircularReferenceDetect));
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * Send the ajax response back to the client side.
     *
     * @param response http response
     * @param jsonObj
     */
    public static void writeJsonObject(HttpServletResponse response, Object jsonObj) {
        responseDisableCache(response);
        responseSupportsCORS(response);
        responseSetJsonEncoding(response);

        PrintWriter writer = getWriter(response);
        writeJson(writer, jsonObj);
    }

    /**
     * Send the ajax response back to the client side (Date object will be formatted as per the given
     * <code>dateFormat</code>).
     *
     * @param response
     * @param responseObj
     * @param dateFormat
     */
    public static void writeJsonWithDateFormat(HttpServletResponse response, Object responseObj, String dateFormat) {
        responseDisableCache(response);
        responseSupportsCORS(response);
        responseSetJsonEncoding(response);

        PrintWriter writer = getWriter(response);
        if (dateFormat != null)
            writeJsonWithDateFormat(writer, responseObj, dateFormat);
        else
            writeJson(writer, responseObj);
    }

    /**
     * Send the response with the given <code>statusCode</code>.
     *
     * @param response
     * @param statusCode
     */
    public static void writeStatusCode(HttpServletResponse response, int statusCode) {
        responseDisableCache(response);
        responseSupportsCORS(response);
        responseSetJsonEncoding(response);

        response.setStatus(statusCode);
    }

    public static PrintWriter getWriter(HttpServletResponse response) {
        if (response == null)
            return null;

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (Exception e) {
            LOGGER.error("Failed to write response", e);
        }
        return writer;
    }

    private static void responseDisableCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies.
    }

    private static void responseSupportsCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    private static void responseSetJsonEncoding(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
    }

}
