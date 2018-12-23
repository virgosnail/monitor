package com.skd.server.util;

import com.skd.server.common.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 14:21
 */
@Slf4j
public class FileUtil {
    /**
     * 将文件路径的文件分隔符转为当前系统的格式
     * @param oldPath 旧路径
     * @return
     */
    public static String convertFileSeparator(String oldPath){
        String newPath = oldPath;
        String os = System.getProperty(Constant.OS_NAME);
        boolean isWindows = os.toLowerCase().startsWith(Constant.OS_WINDOWS.toLowerCase());
        boolean isLinux = os.toLowerCase().startsWith(Constant.OS_LINUX.toLowerCase());
        boolean isMac = os.toLowerCase().startsWith(Constant.OS_MAC.toLowerCase());
        if ( isLinux || isMac){
            newPath = oldPath.replaceAll("\\\\","/");
        } else if ( isWindows){
            newPath = oldPath.replaceAll("/","\\\\");
        } else {
            log.error("sorry,current os type is not supported, os.name is :" + os);
        }
        return newPath;
    }

    /**
     * 根据相对路径获取绝对路径
     * @param relativePath
     * @return
     */
    public static String getAbsolutePath(String rootPath, String relativePath){
        String absolutePath = rootPath + File.separator + relativePath;
        return absolutePath;
    }

    /**
     * 将流中的数据写到指定文件，如果文件存在则先删除文件再写入文件
     * @param absolutePath
     * @param is
     * @return
     */
    public static boolean write(String absolutePath, InputStream is){
        try {
            File file = new File(absolutePath);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String buf = null;
            while ((buf = br.readLine())!= null){
                bw.write(buf);
            }
            bw.flush();
            close(bw,br);
        } catch (Exception e) {
            log.error("write",e);
            return false;
        }
        return true;
    }


    /**
     * 关闭流
     * @param streams
     */
    public static void close(Closeable...streams){
        for(Closeable stream : streams){
            if (null != stream){
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("close stream occur a exception",e);
                }
            }
        }
    }
}
