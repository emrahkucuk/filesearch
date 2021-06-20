package com.emrah.mafaylz.model;

import java.io.Serializable;

public class FileSearchResultItem implements Serializable {
    private final String fileName;
    private final String filePath;

    public FileSearchResultItem(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }
}
