package com.emrah.mafaylz.helpers;

import androidx.annotation.NonNull;

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

        @NonNull
        @Override
        public String toString() {
            return "Default Sorting";
        }
    }, ALPHABETICAL {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new AlphabeticalComparator();
        }

        @NonNull
        @Override
        public String toString() {
            return "Sort by File Name";
        }
    }, EXTENSION {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new ExtensionComparator();
        }

        @NonNull
        @Override
        public String toString() {
            return "Sort by File Extension";
        }
    }, CHRONOLOGICAL {
        @Override
        Comparator<FileSearchResultItem> getSortingComparator() {
            return new ChronologicalComparator();
        }

        @NonNull
        @Override
        public String toString() {
            return "Sort by Last Modified Date";
        }
    };

    abstract Comparator<FileSearchResultItem> getSortingComparator();

}
