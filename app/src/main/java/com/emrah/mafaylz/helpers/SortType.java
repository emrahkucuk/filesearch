package com.emrah.mafaylz.helpers;

import com.emrah.mafaylz.helpers.sortcomparators.AlphabeticalComparator;
import com.emrah.mafaylz.helpers.sortcomparators.ChronologicalComparator;
import com.emrah.mafaylz.helpers.sortcomparators.ExtensionComparator;
import com.emrah.mafaylz.helpers.sortcomparators.NoOpComparator;
import com.emrah.mafaylz.model.FileSearchResultItem;

import java.util.Comparator;

public enum SortType {

    NONE {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new NoOpComparator();
        }

        @Override
        int getSortingSpinnerPosition() {
            return 0;
        }
    }, ALPHABETICAL {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new AlphabeticalComparator();
        }

        @Override
        int getSortingSpinnerPosition() {
            return 1;
        }
    }, EXTENSION {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new ExtensionComparator();
        }

        @Override
        int getSortingSpinnerPosition() {
            return 2;
        }
    }, CHRONOLOGICAL {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new ChronologicalComparator();
        }

        @Override
        int getSortingSpinnerPosition() {
            return 3;
        }
    };

    abstract Comparator<FileSearchResultItem> getSortingComparator();

    abstract int getSortingSpinnerPosition();

    public static SortType getSortType(int spinnerPosition) {
        for (SortType sortType : values()) {
            if (sortType.getSortingSpinnerPosition() == spinnerPosition) {
                return sortType;
            }
        }
        return SortType.NONE;
    }

}
