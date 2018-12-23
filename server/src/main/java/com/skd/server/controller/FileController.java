package com.skd.server.controller;

import com.skd.server.config.Config;
import com.skd.server.entity.request.FileChangeReq;
import com.skd.server.entity.request.ServerPathReq;
import com.skd.server.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private Config config;

    @PostMapping("/serverPath")
    public Boolean serverPath(@RequestBody ServerPathReq request) {
        String serverPath = request.getServerPath();
        config.setRootPath(serverPath);
        File file = new File(serverPath);
        if (file.exists()){
            log.info("receive serverPath is " + serverPath);
        } else {
            file.mkdirs();
            log.info("dir is not exist, create dir :" + serverPath);
        }
        return true;
    }

    /**
     * 文件的新增和修改，需要携带文件对象
     *
     * @param file
     * @param fileInfo
     * @return
     */
    @PostMapping("/dataChanged")
    public Boolean dataChanged(@RequestParam(value = "file") MultipartFile file, FileChangeReq fileInfo) {
        log.info("dataChanged");
        if (null != file) {
            log.info(file.toString());
        }
        log.info("fileInfo" + fileInfo.toString());
        boolean result = false;
        switch (fileInfo.getType()) {
            case "1":
                result = fileService.addFile(fileInfo, file);
                break;
            case "2":
                result = fileService.modifyFile(fileInfo, file);
                break;
            default:
                log.warn("file type is :" + fileInfo.getType());
                break;
        }
        return result;
    }

    /**
     * 文件的删除，文件夹的新增，修改，删除，不涉及文件传输
     *
     * @param fileInfo
     * @return
     */
    @PostMapping("/infoChanged")
    public Boolean infoChanged(@RequestBody FileChangeReq fileInfo) {
        log.info("infoChanged");
        log.info(fileInfo.toString());

        boolean result = false;
        switch (fileInfo.getType()) {
            case "0":
                result = fileService.deleteFile(fileInfo);
                break;
            case "1":
                result = fileService.addFile(fileInfo, null);
                break;
            case "2":
                result = fileService.modifyFile(fileInfo, null);
                break;
            default:
                log.warn("file type is :" + fileInfo.getType());
                break;
        }
        return result;
    }

}
