package com.skd.server.model.response;

import lombok.Data;

/**
 * @Describe:
 * @Author: chenfan
 * @Date: 2019/4/15 19:20
 */
@Data
public class ResponseResult {

    private Boolean result;

    private String errorInfo;

    private Object data;
}
