package com.skd.server.controller;

import com.skd.server.entity.request.FileChange;
import com.skd.server.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("changed")
    public Boolean changed(@RequestBody FileChange file) {
        log.info(file.toString());
        boolean result = false;
        switch (file.getType()) {
            case "0":
                result = fileService.delete(file);
                break;
            case "1":
                result = fileService.add(file);
                break;
            case "2":
                result = fileService.modify(file);
                break;
            default:
                log.warn("file type is :" + file.getType());
                break;
        }
        return result;
    }
}
