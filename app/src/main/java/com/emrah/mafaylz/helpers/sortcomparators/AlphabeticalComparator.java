package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import java.util.Comparator;

public class AlphabeticalComparator implements Comparator<FileSearchResultItem> {

    @Override
    public int compare(FileSearchResultItem o1, FileSearchResultItem o2) {
        return o1.getFileName().compareToIgnoreCase(o2.getFileName());
    }
}
