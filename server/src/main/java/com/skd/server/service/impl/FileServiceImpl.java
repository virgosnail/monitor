package com.skd.server.service.impl;

import com.skd.server.entity.request.FileChange;
import com.skd.server.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public boolean add(FileChange file) {
        log.info(file.getType());
        log.info(file.getFile().toString());
        log.info(file.getPath());
        return true;
    }

    @Override
    public boolean modify(FileChange file) {
        log.info(file.getType());
        log.info(file.getFile().toString());
        log.info(file.getPath());
        return true;
    }

    @Override
    public boolean delete(FileChange file) {
        log.info(file.getType());
        log.info(file.getFile().toString());
        log.info(file.getPath());
        return true;
    }
}
