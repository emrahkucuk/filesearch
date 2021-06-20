package com.emrah.mafaylz.model;

import java.util.List;

public class FileSearchResult {
    private final List<FileSearchResultItem> fileSearchResultList;
    private final String fileSearchQuery;

    public FileSearchResult(List<FileSearchResultItem> fileSearchResultList, String query) {
        this.fileSearchResultList = fileSearchResultList;
        this.fileSearchQuery = query;
    }

    public List<FileSearchResultItem> getFileSearchResultList() {
        return fileSearchResultList;
    }

    public String getFileSearchQuery() {
        return fileSearchQuery;
    }

    public String getFileCount() {
        return String.valueOf(fileSearchResultList.size());
    }
}
