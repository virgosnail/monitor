package com.skd.util;

import com.skd.common.Config;
import java.io.File;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class FileUtil {

    public static String getURL()
    {
        Config config = Config.getInstance();
        return "http://" + config.getIp() + ":" + config.getPort() + config.getUrl();
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
