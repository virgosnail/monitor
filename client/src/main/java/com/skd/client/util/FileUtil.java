package com.skd.client.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/23 11:21
 */

public class FileUtil {

    /**
     * 客户端监听目录下已存在的文件列表
     */
    public static List<File> existFileList = new ArrayList();

    public static void initExistFileList(File file) {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            int num = files.length;
            if (num > 0){
                for (int i = 0; i < num; i++) {
                    File tempFile = files[i];
                    if (tempFile.isDirectory()){
                        existFileList.add(tempFile);
                        initExistFileList(tempFile);
                    }else {
                        existFileList.add(tempFile);
                    }
                }

            } else {
                existFileList.add(file);
            }
        } else {
            existFileList.add(file);
        }
    }

    public static void main(String[] args) throws Exception{
        initExistFileList(new File("D:/monitor/a"));
        for (int i = 0; i < existFileList.size(); i++) {
            System.out.println(existFileList.get(i).getAbsolutePath());
        }
    }
}
