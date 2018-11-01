package com.xzy.springbootapi.web.utils;


/**
 * result message builder
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
        return new ResultMessage<T>().setSuccess(false)
                .setErrorCode(code)
                .setErrorMessage(message);
    }

    /**
     * create an buildErrorMessage message with buildErrorMessage message
     * @param message buildErrorMessage message
     * @param <T> data type
     * @return buildErrorMessage message
     */
    public static <T> ResultMessage<T> error(String message) {
        return new ResultMessage<T>().setSuccess(false)
                .setErrorMessage(message);
    }


    /**
     * result message
     * @param <T>
     */
    public static class ResultMessage<T> {

        private boolean success = true;
        private T data = null;
        private ResultMessageErrorPart error = null;

        public ResultMessage() {}

        public boolean isSuccess() {
            return success;
        }

        public ResultMessage<T> setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Object getData() {
            return data;
        }

        public ResultMessage<T> setData(T data) {
            this.data = data;
            return this;
        }

        public ResultMessageErrorPart getError() {
            return error;
        }

        public ResultMessage<T> setErrorCode(Integer code) {
            if (error == null) error = new ResultMessageErrorPart();
            error.setCode(code);
            return this;
        }

        public ResultMessage<T> setErrorMessage(String message) {
            if (error == null) error = new ResultMessageErrorPart();
            error.setMessage(message);
            return this;
        }

        private static class ResultMessageErrorPart {
            private Integer code = null;
            private String message = null;

            @SuppressWarnings("unused")
            public Integer getCode() {
                return code;
            }
            public void setCode(Integer code) {
                this.code = code;
            }
            @SuppressWarnings("unused")
            public String getMessage() {
                return message;
            }
            public void setMessage(String message) {
                this.message = message;
            }
        }

    }
}
