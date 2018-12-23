package com.skd.client.monitor;


import com.skd.client.common.EventType;
import com.skd.client.config.MonitorConfig;
import com.skd.client.manager.HttpManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;

/**
 * @Description     监听文件的各类事件
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */

@Slf4j
@Component
public class Monitor {

	@Autowired
	private MonitorConfig monitorConfig;

	@Autowired
    private HttpManager httpManager;

	@Autowired
    private MyFilter myFilter;

    public void monitor() {

        String clientPath = monitorConfig.getClientPath();
        FileFilter fileFilter = FileFilterUtils.and(myFilter);
        FileAlterationObserver observer = new FileAlterationObserver(clientPath, fileFilter);
        observer.addListener(new FileAlterationListener() {

            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {

            }

            @Override
            public void onDirectoryCreate(File file) {
                log.info("onDirectoryCreate "+file.getAbsolutePath());
                sendEvent(EventType.FILE_CREATE, file, true, false);
            }

            @Override
            public void onDirectoryChange(File file) {
                log.info("onDirectoryChange "+file.getAbsolutePath());
                sendEvent(EventType.FILE_CHANGE, file, true, false);
            }

            @Override
            public void onDirectoryDelete(File file) {
                log.info("onDirectoryDelete "+file.getAbsolutePath());
                sendEvent(EventType.FILE_DELETE, file,true, false);
            }
            
            @Override
            public void onFileCreate(File file) {
                log.info("onFileCreate"+file.getAbsolutePath());
                sendEvent(EventType.FILE_CREATE, file, false, true);
            }

            @Override
            public void onFileChange(File file) {
                log.info("onFileChange"+file.getAbsolutePath());
                sendEvent(EventType.FILE_CHANGE, file, false, true);
            }

            @Override
            public void onFileDelete(File file) {
                log.info("onFileDelete"+file.getAbsolutePath());
                sendEvent(EventType.FILE_DELETE, file, false, false);
            }

            @Override
            public void onStop(FileAlterationObserver fileAlterationObserver) {

            }
        });
        Long interval = monitorConfig.getInterval();
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception e) {
            log.error("monitor.start()  occur a exception", e);
        }

    }
    
    private void sendEvent(EventType event, File file, boolean isDir, boolean hasFile){
        httpManager.sendFileEvent(event, file, isDir, hasFile);
    }
}

