package com.skd.client.manager;


import com.skd.client.common.Event;
import com.skd.client.common.MonitorConfig;
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

    public void monitor() {
        String rootpath = monitorConfig.getRootPath();
        File rootFile = new File(rootpath);
        if (!rootFile.exists() || !rootFile.isDirectory())
		{
            return ;
        }
        rootpath = rootFile.getAbsolutePath();
        // 将文件路径设置为统一格式
        monitorConfig.setRootPath(rootpath);
        log.info("rootpath: " + rootpath );
        FileFilter fileFilter = FileFilterUtils.and(new MyFilter());
        FileAlterationObserver observer = new FileAlterationObserver(rootpath, fileFilter);
        observer.addListener(new FileAlterationListener() {

            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {

            }

            @Override
            public void onDirectoryCreate(File file) {
                log.info("onDirectoryCreate "+file.getAbsolutePath());
                sendEvent(Event.FILE_CREATE, file, true, false);
            }

            @Override
            public void onDirectoryChange(File file) {
                log.info("onDirectoryChange "+file.getAbsolutePath());
                sendEvent(Event.FILE_CHANGE, file, true, false);
            }

            @Override
            public void onDirectoryDelete(File file) {
                log.info("onDirectoryDelete "+file.getAbsolutePath());
                sendEvent(Event.FILE_DELETE, file,true, false);
            }
            
            @Override
            public void onFileCreate(File file) {
                log.info("onFileCreate"+file.getAbsolutePath());
                sendEvent(Event.FILE_CREATE, file, false, true);
            }

            @Override
            public void onFileChange(File file) {
                log.info("onFileChange"+file.getAbsolutePath());
                sendEvent(Event.FILE_CHANGE, file, false, true);
            }

            @Override
            public void onFileDelete(File file) {
                log.info("onFileDelete"+file.getAbsolutePath());
                sendEvent(Event.FILE_DELETE, file, false, false);
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
    
    private void sendEvent( Event event, File file, boolean isDir, boolean hasFile){
        httpManager.doPost(event, file, isDir, hasFile);
    }
}

