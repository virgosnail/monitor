package com.skd.server.service;

import com.skd.server.entity.request.FileChangeReq;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
public interface FileService {
    /**
     * 添加文件，文件夹
     * @param fileInfo
     * @param file
     * @return
     */
    boolean addFile(FileChangeReq fileInfo, MultipartFile file);

    /**
     * 修改文件，文件夹
     * @param fileInfo
     * @param file
     * @return
     */
    boolean modifyFile(FileChangeReq fileInfo, MultipartFile file);

    /**
     * 删除文件，文件夹
     * @param fileInfo
     * @return
     */
    boolean deleteFile(FileChangeReq fileInfo);

}
