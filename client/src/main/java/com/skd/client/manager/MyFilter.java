package com.skd.client.manager;


import com.skd.client.common.MonitorConfig;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @Description 过滤器是否接受此文件，在此过滤器里面可以筛选要处理的文件格式
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class MyFilter implements IOFileFilter {


    @Autowired
    private MonitorConfig monitorConfig;

    @Override
    public boolean accept(File file) {
        String filename = file.getName();

        if (!file.isDirectory()) {
            if (null == monitorConfig && Context.getContext().containsBean("monitorConfig")){
                monitorConfig = (MonitorConfig)Context.getContext().getBean("monitorConfig");
            }
            String[] ignoreTypes = monitorConfig.getIgnoreTypes().split(",");
            for (String type : ignoreTypes) {
                if (filename.toLowerCase().endsWith(type.toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *  监听指定目录的指定文件
     * @param dir 监听的目录
     * @param name 监听目录中的文件名
     * @return
     */
    @Override
    public boolean accept(File dir, String name) {
        return true;
    }

}
