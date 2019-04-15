package com.skd.server.common;

import com.skd.server.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Describe: 全局异常处理
 * @Author: chenfan
 * @Date: 2019/4/15 19:16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统内部异常
     *
     * @param e        异常对象
     * @param request  请求对象
     * @param response 返回信息
     * @return 返回异常信息
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        result.setResult(false);
        result.setErrorInfo(e.toString());
        return result;
    }
}
