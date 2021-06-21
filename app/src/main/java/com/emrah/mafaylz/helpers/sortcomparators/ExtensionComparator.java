package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import java.util.Comparator;

public class ExtensionComparator implements Comparator<FileSearchResultItem> {

    @Override
    public int compare(FileSearchResultItem o1, FileSearchResultItem o2) {
        return o1.getFileExtension().compareToIgnoreCase(o2.getFileExtension());
    }
}
