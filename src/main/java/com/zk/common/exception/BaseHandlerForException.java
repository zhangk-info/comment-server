package com.zk.common.exception;

import cn.hutool.core.exceptions.ValidateException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * 全局异常统一处理基础异常类
 */
@Slf4j
@ControllerAdvice
public class BaseHandlerForException {

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public Object validateExceptionHandler(HttpServletResponse response, ValidateException ex) {
        DataResp dataResp = new DataResp(402, ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object sqlExceptionHandler(HttpServletResponse response, SQLException ex) {
        DataResp dataResp = new DataResp(ErrorCode.SQL_ERROR.code, ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object serviceExceptionHandler(HttpServletResponse response, ServiceException ex) {
        DataResp dataResp = new DataResp(ex.getCode(), ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object fileNotFoundExceptionHandler(HttpServletResponse response, FileNotFoundException ex) {
        DataResp dataResp = new DataResp(ErrorCode.NOT_FOUND.code, ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object otherExceptionHandler(HttpServletResponse response, Exception ex) {
        DataResp dataResp = new DataResp(ErrorCode.INTERNAL_SERVER_ERROR.code, ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object authenticationExceptionHandler(HttpServletResponse response, Exception ex) {
        DataResp dataResp = new DataResp(ErrorCode.FORBIDDEN.code, ex.getMessage(), null);
        log.error(ex.getMessage(), ex);
        return dataResp;
    }
}
