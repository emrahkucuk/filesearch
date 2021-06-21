package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import java.util.Comparator;

public class NoOpComparator implements Comparator<FileSearchResultItem> {

    @Override
    public int compare(FileSearchResultItem o1, FileSearchResultItem o2) {
        return 0;
    }
}
