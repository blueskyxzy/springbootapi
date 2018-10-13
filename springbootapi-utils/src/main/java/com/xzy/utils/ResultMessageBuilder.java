package com.xzy.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzy on 18/6/6  .
 */

public class ResultMessageBuilder {
    /**
     * create a successful message with give data object
     * @param data data
     * @param <T> data type
     * @return buildSuccess  message
     */
    public static <T> ResultMessage<T> success(T data) {
        return new ResultMessage<T>().setData(data);
    }

    /**
     * create an buildErrorMessage message with code and message
     * @param code buildErrorMessage code
     * @param message buildErrorMessage message
     * @param <T> data type
     * @return buildErrorMessage message
     */
    public static <T> ResultMessage<T> error(int code, String message) {
        return new ResultMessage<T>().setErrorCode(code)
                .setErrorMessage(message);
    }

    /**
     * create an buildErrorMessage message with buildErrorMessage message
     * @param message buildErrorMessage message
     * @param <T> data type
     * @return buildErrorMessage message
     */
    public static <T> ResultMessage<T> error(String message) {
        return new ResultMessage<T>().setErrorMessage(message);
    }

    public static <T> ResultMessage<T> meta(T data,int pageIndex, int pageSize) {
        return new ResultMessage<T>().setData(data).setMetaPageIndex(pageIndex).setMetaPageSize(pageSize);
    }

    /**
     * result message
     * @param <T>
     */
    public static class ResultMessage<T> {

        private T data = null;

        private ResultMessageErrorPart errors = null;

        private ResultMessageMetaPart meta = null;

        public ResultMessageMetaPart getMeta() {
            return meta;
        }

        public void setMeta(ResultMessageMetaPart meta) {
            this.meta = meta;
        }

        public ResultMessage() {}

        public Object getData() {
            return data;
        }

        public ResultMessage<T> setData(T data) {
            this.data = data;
            return this;
        }

        public ResultMessageErrorPart getErrors() {
            return errors;
        }

        public ResultMessage<T> setErrorCode(Integer code) {
            if (errors == null) errors = new ResultMessageErrorPart();
            errors.setCode(code);
            return this;
        }

        public ResultMessage<T> setErrorMessage(String message) {
            if (errors == null) errors = new ResultMessageErrorPart();
            errors.setMessage(message);
            return this;
        }

        public ResultMessage<T> setMetaPageIndex(Integer pageIndex){
            if (meta == null) meta = new ResultMessageMetaPart();
            meta.setPageIndex(pageIndex);
            return this;
        }

        public ResultMessage<T> setMetaPageSize(Integer pageSize){
            if (meta == null) meta = new ResultMessageMetaPart();
            meta.setPageSize(pageSize);
            return this;
        }

        private static class ResultMessageErrorPart {
            private Integer code = null;
            private String msg = null;


            public Integer getCode() {
                return code;
            }
            public void setCode(Integer code) {
                this.code = code;
            }

            public String getMessage() {
                return msg;
            }
            public void setMessage(String message) {
                this.msg = message;
            }
        }

        private static class ResultMessageMetaPart {

            public Integer getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(Integer pageIndex) {
                this.pageIndex = pageIndex;
            }

            public Integer getPageSize() {
                return pageSize;
            }

            public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
            }

            private Integer pageIndex;

            private Integer pageSize;

        }

    }
}
