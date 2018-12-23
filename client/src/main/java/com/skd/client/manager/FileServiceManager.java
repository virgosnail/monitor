package com.skd.client.manager;

import com.skd.client.common.EventType;
import com.skd.client.config.MonitorConfig;
import com.skd.client.monitor.Monitor;
import com.skd.client.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/22 10:48
 */
@Slf4j
@Component
public class FileServiceManager {

    @Autowired
    private MonitorConfig monitorConfig;

    @Autowired
    private HttpManager httpManager;

    @Autowired
    private Monitor monitor;

    public void start() {
        // 验证客户端的监听目录
        String clientPath = monitorConfig.getClientPath();
        File rootFile = new File(clientPath);
        if (!rootFile.exists() || !rootFile.isDirectory()) {
            log.error("client path is not exist or is not a dir");
            return;
        }
        clientPath = rootFile.getAbsolutePath();
        // 将文件路径设置为统一格式
        monitorConfig.setClientPath(clientPath);
        log.info("clientPath: " + clientPath);
        // 发送服务端同步目录
        String serverPath = monitorConfig.getServerPath();
        httpManager.sendServerPath(serverPath);
        // 是否同步已存在的文件
        boolean synExistedFiles = monitorConfig.isSynExistedFiles();
        if (synExistedFiles) {
            FileUtil.initExistFileList(rootFile);
            List<File> existFileList = FileUtil.existFileList;
            for (File file : existFileList) {
                if (file.isDirectory()) {
                    httpManager.sendFileEvent(EventType.FILE_CREATE, file, true, false);
                } else {
                    httpManager.sendFileEvent(EventType.FILE_CREATE, file, false, true);
                }
            }
        }
        monitor.monitor();
    }
}
