package com.emrah.mafaylz.model;

import java.io.Serializable;

public class FileSearchResultItem implements Serializable {
    private final String fileName;
    private final String filePath;
    private final long lastModifiedDate;
    private final String fileExtension;

    public FileSearchResultItem(String fileName, String filePath, long lastModifiedDate, String fileExtension) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastModifiedDate = lastModifiedDate;
        this.fileExtension = fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
