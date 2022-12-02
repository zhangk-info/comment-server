package com.zk.common.exception;

public enum ErrorCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),

    //权限相关
    NO_DATA_ACCESS_POWER(1000, "无数据访问权限"),
    NO_API_ACCESS_POWER(1001, "无接口访问权限"),
    //数据相关
    DATA_NOT_EXIST(100, "%s数据已清除"),
    DATA_EXIST(101, "数据已存在"),
    SERVER_ERROR(112, "服务器错误"),
    NO_LOGIN(113, "登录信息过期或者未登录"),
    PARAMS_ERR(700, "%s参数错误"),
    SQL_ERROR(401, "SQL执行错误%s"),
    //评论
    ORDER_SERVICE_TIMEOUT(3000, "当前评论服务繁忙，请稍后重试");
    public Integer code;
    public String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
