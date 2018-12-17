package com.skd.client.common;


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

    private String rootPath;
    private String serverIp;
    private String serverPort;
    private String jsonUrl;
    private String fileUrl;
    private Long interval;
    private String ignoreTypes;

}
