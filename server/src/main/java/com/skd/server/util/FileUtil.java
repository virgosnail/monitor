package com.skd.server.util;

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
            File tempFile = new File(absolutePath);
            if (tempFile.exists()){
                tempFile.delete();
            }
            tempFile.createNewFile();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
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
     * 删除指定路径的文件
     * @param absolutePath
     * @return
     */
    public static boolean delete(String absolutePath){
        File tempFile = new File(absolutePath);
        if (tempFile.exists()) {
            tempFile.delete();
        } else {
            log.warn("deleted file not exist");
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
