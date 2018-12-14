package com.skd.server.entity.request;

import java.io.File;

public class FileChange {

    private String type;
    private File file;
    private String path;

    public FileChange() {
    }

    public FileChange(String type, File file, String path) {
        this.type = type;
        this.file = file;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FileChange{" +
                "type='" + type + '\'' +
                ", file=" + file +
                ", path='" + path + '\'' +
                '}';
    }
}
