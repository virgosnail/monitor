package com.skd.server.model.request;

import com.skd.server.common.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Data
@Slf4j
public class FileChangedReq {

    private String relativePath;
    private Boolean isDir;
    private String type;

    /**
     *  对请求当中的文件分割符进行转换
     *
     * @param relativePath  文件路径
     */
    public void setRelativePath(String relativePath) {
        String os = System.getProperty(Constant.OS_NAME);
        boolean isWindows = os.toLowerCase().startsWith(Constant.OS_WINDOWS.toLowerCase());
        boolean isLinux = os.toLowerCase().startsWith(Constant.OS_LINUX.toLowerCase());
        boolean isMac = os.toLowerCase().startsWith(Constant.OS_MAC.toLowerCase());
        if ( isLinux || isMac){
            this.relativePath = relativePath.replaceAll("\\\\","/");
        } else if ( isWindows){
            this.relativePath = relativePath.replaceAll("/","\\\\");
        } else {
            log.warn("sorry,current os type is not supported, os.name is :{}", os);
        }
    }
}
