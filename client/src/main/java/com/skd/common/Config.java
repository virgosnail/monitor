package com.skd.common;

import com.skd.util.ConfigUtil;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */

public class Config {

    public static Config config;

    private String rootPath;
    private String ip;
    private String port;
    private String jsonUrl;
    private String fileUrl;

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
        String jsonUrl = common.getValue("server.jsonUrl");
        String fileUrl = common.getValue("server.fileUrl");
        this.rootPath = rootPath;
        this.ip = ip;
        this.port = port;
        this.jsonUrl = jsonUrl;
        this.fileUrl = fileUrl;
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

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
