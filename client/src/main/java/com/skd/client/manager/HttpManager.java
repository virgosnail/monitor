package com.skd.client.manager;


import com.skd.client.common.Constant;
import com.skd.client.common.EventType;
import com.skd.client.config.MonitorConfig;
import com.skd.client.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
@Slf4j
@Component
public class HttpManager {
    /**
     * org.apache.http.client.HttpClient;
     * 创建一个httpclient对象
     */
    private HttpClient httpclient = HttpClients.createDefault();

    @Autowired
    private MonitorConfig monitorConfig;

    /**
     * 向服务端发送服务端的同步目录
     *
     * @param serverPath 服务端的同步目录
     */
    public void sendServerPath(String serverPath) {
        log.info(" enter sendServerPath");
        String url = "http://" + monitorConfig.getServerIp() + ":" + monitorConfig.getServerPort() + Constant.FILE_SERVER_PATH;
        log.info("url:" + url);
        HttpPost httppost = new HttpPost(url);
        Map map = new HashMap(1);
        map.put(Constant.SERVER_PATH, serverPath);
        log.info(map.toString());
        HttpEntity httpEntity = HttpUtil.createStringEntity(map);
        httppost.setEntity(httpEntity);
        sendPost(httppost);
    }

    /**
     * 将文件的变化事件发送到服务端
     *
     * @param event 0:删除  1：新增  2：修改
     * @return
     * @Author Administrator
     * @Date 2018年11月11日
     */
    public void sendFileEvent(EventType event, File file, boolean isDir, boolean hasFile) {
        // 创建http的POST发送方式对象
        String url = HttpUtil.getURL(hasFile, monitorConfig.getServerIp(), monitorConfig.getServerPort());
        HttpPost httppost = new HttpPost(url);
        log.info(" URL: " + url);
        HttpEntity httpEntity = createFileHttpEntity(event, file, isDir, hasFile);
        httppost.setEntity(httpEntity);
        // 执行httppost对象并获得response
        sendPost(httppost);

    }

    private void sendPost(HttpPost httppost) {
        try {
            httppost.setConfig(RequestConfig.custom()
                    // 设置连接超时时间，单位毫秒。
                    .setConnectTimeout(5000)
                    // 设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。
                    .setConnectionRequestTimeout(1000)
                    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
                    .setSocketTimeout(5000).build());
            // 执行httppost对象并获得response
            HttpResponse response = httpclient.execute(httppost);
            // 状态码
            HttpEntity resEntity = response.getEntity();
            // 获得返回来的信息，转化为字符串string
            String respopnse = EntityUtils.toString(resEntity);
            log.info("respopnse body: " + respopnse);
        } catch (Exception e) {
            log.error("sendPost occur a exception", e);
        }
    }

    /**
     * 创建 HttpEntity 对象
     *
     * @param event   事件类型
     * @param file    文件对象
     * @param isDir   是否为目录
     * @param hasFile 是否有文件
     * @return HttpEntity
     */
    public HttpEntity createFileHttpEntity(EventType event, File file, boolean isDir, boolean hasFile) {
        HttpEntity httpEntity;
        Map map = new HashMap(10);
        map.put(Constant.TYPE, event.getValue());
        map.put(Constant.IS_DIR, isDir);
        String relativePath = getRelativePath(file.getAbsolutePath());
        map.put(Constant.RELATIVE_PATH, relativePath);
        log.info(map.toString());
        if (hasFile) {
            httpEntity = HttpUtil.createFileEntity(file, map);
        } else {
            httpEntity = HttpUtil.createStringEntity(map);
        }
        return httpEntity;
    }

    /**
     * 获取文件的相对路径
     *
     * @param absolutePath 绝对路径
     * @return relativePath 相对路径
     */
    public String getRelativePath(String absolutePath) {
        String rootPath = monitorConfig.getClientPath();
        String relativePath = absolutePath.replace(rootPath + File.separator, " ").trim();
        return relativePath;
    }

}
