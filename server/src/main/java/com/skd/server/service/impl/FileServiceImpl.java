package com.skd.server.service.impl;

import com.skd.server.config.ServerConfig;
import com.skd.server.model.request.FileChangedReq;
import com.skd.server.service.FileService;
import com.skd.server.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ServerConfig config;

    @Override
    public boolean addFile(FileChangedReq fileInfo, MultipartFile file) {
        boolean result = false;
        String relativePath = fileInfo.getRelativePath();
        String rootPath = config.getRootPath();
        String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
        if (fileInfo.getIsDir()) {
            File tempFile = new File(absolutePath);
            if (!tempFile.exists()) {
                result = tempFile.mkdirs();
            }
        } else {
            result = FileUtil.write(absolutePath, file);
        }
        log.info("addFile file :" + absolutePath);
        return result;
    }

    @Override
    public boolean modifyFile(FileChangedReq fileInfo, MultipartFile file) {
        boolean result;
        String relativePath = fileInfo.getRelativePath();
        String rootPath = config.getRootPath();
        String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
        if (fileInfo.getIsDir()) {
            result = true;
        } else {
            result = FileUtil.write(absolutePath, file);
        }
        log.info("modifyFile file :" + absolutePath);
        return result;
    }

    @Override
    public boolean deleteFile(FileChangedReq fileInfo) {
        boolean delete;
        String relativePath = fileInfo.getRelativePath();
        String rootPath = config.getRootPath();
        String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
        File file = new File(absolutePath);
        if (file.exists()) {
            delete = file.delete();
        } else {
            delete = true;
            log.warn("file not exist :" + absolutePath);
        }
        log.info("deleteFile file :" + absolutePath);
        return delete;
    }

}
