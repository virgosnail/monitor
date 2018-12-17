package com.skd.server.entity.request;

import lombok.Data;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 15:00
 */
@Data
public class FileChange {

    private Boolean isDir;
    private String type;
    private String relativePath;

}
