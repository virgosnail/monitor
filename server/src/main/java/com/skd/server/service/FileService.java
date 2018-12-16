package com.skd.server.service;

import com.skd.server.entity.request.FileChange;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
public interface FileService {
    /**
     * 添加文件
     * @param fileInfo
     * @param file
     * @return
     */
    boolean add(FileChange fileInfo, MultipartFile file);

    /**
     * 修改文件
     * @param fileInfo
     * @param file
     * @return
     */
    boolean modify(FileChange fileInfo, MultipartFile file);

    /**
     * 删除文件
     * @param fileInfo
     * @return
     */
    boolean delete(FileChange fileInfo);
}
