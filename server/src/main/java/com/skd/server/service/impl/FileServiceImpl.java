package com.skd.server.service.impl;

import com.skd.server.config.Config;
import com.skd.server.entity.request.FileChangeReq;
import com.skd.server.service.FileService;
import com.skd.server.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private Config config;

    @Override
    public boolean addFile(FileChangeReq fileInfo, MultipartFile file) {
        boolean result = false;
        try {
            String relativePath = fileInfo.getRelativePath();
            String rootPath = config.getRootPath();
            String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
            if (fileInfo.getIsDir()){
                File tempFile = new File(absolutePath);
                if (!tempFile.exists()){
                    result = tempFile.mkdirs();
                }
            } else {
                result = FileUtil.write(absolutePath, file.getInputStream());
            }
            log.info("addFile file :" + absolutePath);
        } catch (IOException e) {
            log.error("addFile occur a exception", e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean modifyFile(FileChangeReq fileInfo, MultipartFile file) {
        boolean result;
        try {
            String relativePath = fileInfo.getRelativePath();
            String rootPath = config.getRootPath();
            String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
            if (fileInfo.getIsDir()){
                result = true;
            } else {
                result = FileUtil.write(absolutePath, file.getInputStream());
            }
            log.info("modifyFile file :" + absolutePath);
        } catch (IOException e) {
            log.error("modifyFile occur a exception", e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean deleteFile(FileChangeReq fileInfo) {
        boolean delete;
        String relativePath = fileInfo.getRelativePath();
        String rootPath = config.getRootPath();
        String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
        File file = new File(absolutePath);
        if (file.exists()){
            delete = file.delete();
        } else {
            delete = true;
            log.warn("file not exist :" + absolutePath);
        }
        log.info("deleteFile file :" + absolutePath);
        return delete;
    }

}
