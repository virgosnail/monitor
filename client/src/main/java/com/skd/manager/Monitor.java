package com.skd.manager;


import com.skd.common.Config;
import com.skd.common.Event;
import com.skd.util.HttpUtil2;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;


public class Monitor {

	private static Logger log = LoggerFactory.getLogger(Monitor.class);

	private static Monitor monitor;

	public static Monitor getInstance(){
	    if (null == monitor){
	        monitor = new Monitor();
        }
        return monitor;
    }

    public void monitor() {
    	
        String filepath = Config.getInstance().getRootPath();
        log.info("rootpath: " + filepath);
        
    	if (null == filepath || "".equals(filepath))
		{
			return ;
		}
    	
        FileFilter fileFilter = FileFilterUtils.and(new MyFilter());
        FileAlterationObserver observer = new FileAlterationObserver(filepath, fileFilter);
        observer.addListener(new FileAlterationListener() {

            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {
//                log.info("onStart");
            }

            @Override
            public void onDirectoryCreate(File file) {
                log.info("onDirectoryCreate "+file.getAbsolutePath());
            }

            @Override
            public void onDirectoryChange(File file) {
                log.info("onDirectoryChange "+file.getAbsolutePath());
            }

            @Override
            public void onDirectoryDelete(File file) {
                log.info("onDirectoryDelete "+file.getAbsolutePath());
            }

            @Override
            public void onFileDelete(File file) {
                log.info("onFileDelete"+file.getAbsolutePath());
                sendEvent(Event.FILE_DELETE, file);
            }
            
            @Override
            public void onFileCreate(File file) {
                log.info("onFileCreate"+file.getAbsolutePath());
                sendEvent(Event.FILE_CREATE, file);
            }

            @Override
            public void onFileChange(File file) {
                log.info("onFileChange"+file.getAbsolutePath());
                sendEvent(Event.FILE_CHANGE, file);
            }

            @Override
            public void onStop(FileAlterationObserver fileAlterationObserver) {
//                log.info("onStop");
            }
        });
        
        FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception e) {
            log.error("monitor.start()  occur a exception", e);
        }

    }
    
    private void sendEvent(Event event, File file){
//    	HttpUtil.doPostMethod(event, file);
    	HttpUtil2.doPost(event, file);
    }
}

