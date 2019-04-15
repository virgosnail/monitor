package com.skd.server.controller;

import com.skd.server.config.ServerConfig;
import com.skd.server.model.request.FileChangedReq;
import com.skd.server.model.request.ServerConfigReq;
import com.skd.server.model.response.ResponseResult;
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
@RequestMapping("/monitor")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ServerConfig config;

    @PostMapping("/setConfig")
    public ResponseResult serverPath(@RequestBody ServerConfigReq request) {
        ResponseResult result = new ResponseResult();
        result.setResult(true);
        String serverPath = request.getServerPath();
        config.setRootPath(serverPath);
        File file = new File(serverPath);
        if (file.exists()){
            log.info("receive serverPath is :{}", serverPath);
        } else {
            file.mkdirs();
            log.info("create target directory :{}", serverPath);
        }
        return result;
    }

    /**
     * 文件的新增和修改
     *
     * @param file
     * @param fileInfo
     * @return
     */
    @PostMapping("/fileDataChanged")
    public ResponseResult dataChanged(@RequestParam(value = "file") MultipartFile file, FileChangedReq fileInfo) {

        log.info("fileInfo" + fileInfo.toString());
        ResponseResult result = new ResponseResult();
        result.setResult(true);
        switch (fileInfo.getType()) {
            case "1":
                result.setResult(fileService.addFile(fileInfo, file));
                break;
            case "2":
                result.setResult(fileService.modifyFile(fileInfo, file));
                break;
            default:
                log.warn("unknown file type is : {}", fileInfo.getType());
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
    @PostMapping("/fileInfoChanged")
    public ResponseResult infoChanged(@RequestBody FileChangedReq fileInfo) {
        ResponseResult result = new ResponseResult();
        result.setResult(true);
        switch (fileInfo.getType()) {
            case "0":
                result.setResult(fileService.deleteFile(fileInfo));
                break;
            case "1":
                result.setResult(fileService.addFile(fileInfo, null));
                break;
            case "2":
                result.setResult( fileService.modifyFile(fileInfo, null));
                break;
            default:
                log.warn("file type is :" + fileInfo.getType());
                break;
        }
        return result;
    }

}
