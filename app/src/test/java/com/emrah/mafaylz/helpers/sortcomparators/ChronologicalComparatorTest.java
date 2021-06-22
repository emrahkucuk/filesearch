package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChronologicalComparatorTest {

    FileSearchResultItem first;
    FileSearchResultItem second;
    FileSearchResultItem third;
    final ChronologicalComparator comparator = new ChronologicalComparator();

    @Before
    public void setUp() {
        first = new FileSearchResultItem("First", "/first", System.currentTimeMillis() - 1000, ".pdf");
        second = new FileSearchResultItem("Second", "/second", System.currentTimeMillis(), ".pdf");
        third = new FileSearchResultItem("Third", "/third", System.currentTimeMillis() + 1000, ".pdf");
    }

    @Test
    public void compare() {
        assertTrue(comparator.compare(first, second) < 0);
        assertTrue(comparator.compare(third, second) > 0);
        assertEquals(0, comparator.compare(third, third));
    }
}