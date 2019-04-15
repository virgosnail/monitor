package com.skd.server.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/22 10:36
 */
@Component
@Data
public class ServerConfig {
    /** 服务端文件的保目录 */
    private String rootPath;

}
