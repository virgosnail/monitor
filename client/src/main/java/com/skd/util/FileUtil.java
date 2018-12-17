package com.skd.util;

import com.skd.common.Config;
import java.io.File;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class FileUtil {

    public static String getURL(boolean hasFile)
    {
        Config config = Config.getInstance();
        if (hasFile){
            return "http://" + config.getIp() + ":" + config.getPort() + config.getFileUrl();
        } else {
            return "http://" + config.getIp() + ":" + config.getPort() + config.getJsonUrl();
        }

    }

    /**
     * 获取文件的相对路径
     * @return
     */
    public static String getRelativePath(String absolutePath)
    {

        Config config = Config.getInstance();
        String rootPath = config.getRootPath();
        String relativePath = absolutePath.replace(rootPath + File.separator," ").trim();
        return relativePath ;
    }


}
