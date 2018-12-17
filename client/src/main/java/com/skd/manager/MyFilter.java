package com.skd.manager;

import com.skd.util.ConfigUtil;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Description 过滤器是否接受此文件，在此过滤器里面可以筛选要处理的文件格式
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class MyFilter implements IOFileFilter {
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public boolean accept(File file) {
        String filename = file.getName();

        if (!file.isDirectory()) {
            String[] ignoreTypes = ConfigUtil.getInstance().getValue("ignoreTypes").split(",");
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
