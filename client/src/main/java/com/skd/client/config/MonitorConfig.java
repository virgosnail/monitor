package com.skd.client.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config")
public class MonitorConfig {

    /**
     * 客户端监听的文件目录
     */
    private String clientPath;
    /**
     * 服务端监听的文件目录
     */
    private String serverPath;
    /**
     * 服务端IP
     */
    private String serverIp;
    /**
     * 服务端监听的端口
     */
    private String serverPort;
    /**
     * 轮询文件状态的周期
     */
    private Long interval;
    /**
     * 忽略的文件类型
     */
    private String ignoreTypes;
    /**
     * 是否同步监听目录下已经存在的文件
     */
    private boolean synExistedFiles;

}
