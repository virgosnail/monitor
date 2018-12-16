package com.skd.server.service.impl;

import com.skd.server.config.Config;
import com.skd.server.entity.request.FileChange;
import com.skd.server.service.FileService;
import com.skd.server.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public boolean add(FileChange fileInfo, MultipartFile file) {
        boolean result;
        try {
            String relativePath = fileInfo.getRelativePath();
            String rootPath = config.getRootPath();
            String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
            result = FileUtil.write(absolutePath, file.getInputStream());
            log.info("add file :" + relativePath);
        } catch (IOException e) {
            log.error("", e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean modify(FileChange fileInfo, MultipartFile file) {
        boolean result;
        try {
            String relativePath = fileInfo.getRelativePath();
            String rootPath = config.getRootPath();
            String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
            result = FileUtil.write(absolutePath, file.getInputStream());
            log.info("modify file :" + relativePath);
        } catch (IOException e) {
            log.error("", e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean delete(FileChange fileInfo) {
        String relativePath = fileInfo.getRelativePath();
        String rootPath = config.getRootPath();
        String absolutePath = FileUtil.getAbsolutePath(rootPath, relativePath);
        boolean delete = FileUtil.delete(absolutePath);
        log.info("delete file :" + relativePath);
        return delete;
    }
}
