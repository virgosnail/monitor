package com.skd.util;

import com.skd.common.Config;

public class ServerUtil {

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

        return "http://" ;
    }
}
