package com.emrah.mafaylz.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileSearchResultTest {

    @Test
    public void getFileCount() {
        FileSearchResultItem abc = new FileSearchResultItem("Abc", "/abc", System.currentTimeMillis() + 1000, ".txt");
        FileSearchResultItem ghi = new FileSearchResultItem("ghi", "/ghi", System.currentTimeMillis(), ".docx");
        FileSearchResultItem def = new FileSearchResultItem("def", "/def", System.currentTimeMillis() - 1000, ".pdf");
        ArrayList<FileSearchResultItem> fileList = new ArrayList<>();
        fileList.add(abc);
        fileList.add(ghi);
        fileList.add(def);
        FileSearchResult fileSearchResult = new FileSearchResult(fileList, "");
        assertEquals(fileSearchResult.getFileCount(),"3");
    }
}