package com.skd.server.service;

import com.skd.server.entity.request.FileChange;

public interface FileService {

    boolean add(FileChange file);
    boolean modify(FileChange file);
    boolean delete(FileChange file);
}
