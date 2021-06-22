package com.emrah.mafaylz.helpers;

import com.emrah.mafaylz.model.FileSearchResult;
import com.emrah.mafaylz.model.FileSearchResultItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FileSearchHelperTest {

    FileSearchHelper fileSearchHelper;
    ArrayList<FileSearchResultItem> defaultSorting;
    ArrayList<FileSearchResultItem> alphabeticalSorting;
    ArrayList<FileSearchResultItem> extensionSorting;
    ArrayList<FileSearchResultItem> chronologicalSorting;

    @Before
    public void setUp() {
        FileSearchResultItem abc = new FileSearchResultItem("Abc", "/abc", System.currentTimeMillis() + 1000, ".txt");
        FileSearchResultItem ghi = new FileSearchResultItem("ghi", "/ghi", System.currentTimeMillis(), ".docx");
        FileSearchResultItem def = new FileSearchResultItem("def", "/def", System.currentTimeMillis() - 1000, ".pdf");
        fileSearchHelper = new FileSearchHelper(new FileSearchListener() {
            @Override
            public void onSuccess(FileSearchResult fileSearchResult) {

            }

            @Override
            public void onSaveSuccess() {

            }
        });

        defaultSorting = new ArrayList<>();
        defaultSorting.add(abc);
        defaultSorting.add(ghi);
        defaultSorting.add(def);

        alphabeticalSorting = new ArrayList<>();
        alphabeticalSorting.add(abc);
        alphabeticalSorting.add(def);
        alphabeticalSorting.add(ghi);

        extensionSorting = new ArrayList<>();
        extensionSorting.add(ghi);
        extensionSorting.add(def);
        extensionSorting.add(abc);

        chronologicalSorting = new ArrayList<>();
        chronologicalSorting.add(def);
        chronologicalSorting.add(ghi);
        chronologicalSorting.add(abc);

        fileSearchHelper.setResult(new FileSearchResult(defaultSorting, ""));
    }

    @Test
    public void getResult() {
        FileSearchResult noneResult = FileSearchHelper.getResult(SortType.NONE);
        FileSearchResult alphabeticalResult = FileSearchHelper.getResult(SortType.ALPHABETICAL);
        FileSearchResult extensionResult = FileSearchHelper.getResult(SortType.EXTENSION);
        FileSearchResult chronologicalResult = FileSearchHelper.getResult(SortType.CHRONOLOGICAL);

        assertEquals(noneResult.getFileSearchResultList(), defaultSorting);
        assertEquals(alphabeticalResult.getFileSearchResultList(), alphabeticalSorting);
        assertEquals(extensionResult.getFileSearchResultList(), extensionSorting);
        assertEquals(chronologicalResult.getFileSearchResultList(), chronologicalSorting);
    }
}