package com.skd.server.controller;

import com.skd.server.entity.request.FileChange;
import com.skd.server.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("changed")
    public Boolean changed(@RequestParam(value = "file", required = false) MultipartFile file, FileChange fileInfo) {

        log.info("fileInfo" + fileInfo.toString());
        boolean result = false;
        switch (fileInfo.getType()) {
            case "0":
                result = fileService.delete(fileInfo);
                break;
            case "1":
                result = fileService.add(fileInfo,file);
                break;
            case "2":
                result = fileService.modify(fileInfo,file);
                break;
            default:
                log.warn("file type is :" + fileInfo.getType());
                break;
        }
        return result;
    }

}
