package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import java.util.Comparator;

public class ChronologicalComparator implements Comparator<FileSearchResultItem> {

    @Override
    public int compare(FileSearchResultItem o1, FileSearchResultItem o2) {
        return Long.compare(o1.getLastModifiedDate(), o2.getLastModifiedDate());
    }
}
