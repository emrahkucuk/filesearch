package com.emrah.mafaylz.helpers;

import com.emrah.mafaylz.helpers.sortcomparators.AlphabeticalComparator;
import com.emrah.mafaylz.helpers.sortcomparators.ChronologicalComparator;
import com.emrah.mafaylz.helpers.sortcomparators.ExtensionComparator;
import com.emrah.mafaylz.helpers.sortcomparators.NoOpComparator;

import junit.framework.TestCase;

import org.junit.Assert;

public class SortTypeTest extends TestCase {

    public void testGetSortingComparator() {
        assertEquals(SortType.NONE.getSortingComparator().getClass(), NoOpComparator.class);
        assertEquals(SortType.ALPHABETICAL.getSortingComparator().getClass(), AlphabeticalComparator.class);
        assertEquals(SortType.EXTENSION.getSortingComparator().getClass(), ExtensionComparator.class);
        assertEquals(SortType.CHRONOLOGICAL.getSortingComparator().getClass(), ChronologicalComparator.class);
    }

    public void testValues() {
        SortType[] expectedValues = new SortType[]{
                SortType.NONE,
                SortType.ALPHABETICAL,
                SortType.EXTENSION,
                SortType.CHRONOLOGICAL
        };
        SortType[] actualValues = SortType.values();
        Assert.assertArrayEquals(actualValues, expectedValues);
    }

    public void testValueOf() {
        assertEquals(SortType.valueOf("NONE"), SortType.NONE);
        assertEquals(SortType.valueOf("ALPHABETICAL"), SortType.ALPHABETICAL);
        assertEquals(SortType.valueOf("EXTENSION"), SortType.EXTENSION);
        assertEquals(SortType.valueOf("CHRONOLOGICAL"), SortType.CHRONOLOGICAL);
    }

    public void testToString() {
        assertEquals("Default Sorting", SortType.NONE.toString());
        assertEquals("Sort by File Name", SortType.ALPHABETICAL.toString());
        assertEquals("Sort by File Extension", SortType.EXTENSION.toString());
        assertEquals("Sort by Last Modified Date", SortType.CHRONOLOGICAL.toString());
    }
}