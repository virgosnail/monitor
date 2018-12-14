package com.skd.common;

import com.skd.util.ConfigUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@PropertySource("conf.properties")
@ConfigurationProperties(prefix = "server")
public class Config {

    public static Config config;

    private String rootPath;
    private String ip;
    private String port;
    private String url;

    public static Config getInstance(){
        if (null == config){
            config = new Config();
        }
        return config;
    }

    private Config() {
        init();
    }

    private void init(){
        ConfigUtil common = ConfigUtil.getInstance();
        String rootPath = common.getValue("server.rootPath");
        String ip = common.getValue("server.ip");
        String port = common.getValue("server.port");
        String url = common.getValue("server.url");
        this.rootPath = rootPath;
        this.ip = ip;
        this.port = port;
        this.url = url;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
