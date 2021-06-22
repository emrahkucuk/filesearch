package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlphabeticalComparatorTest {

    FileSearchResultItem abc;
    FileSearchResultItem def;
    FileSearchResultItem ghi;
    final AlphabeticalComparator comparator = new AlphabeticalComparator();

    @Before
    public void setUp() {
        abc = new FileSearchResultItem("Abc", "/abc", System.currentTimeMillis(), ".pdf");
        def = new FileSearchResultItem("def", "/def", System.currentTimeMillis(), ".pdf");
        ghi = new FileSearchResultItem("ghi", "/ghi", System.currentTimeMillis(), ".pdf");
    }

    @Test
    public void testCompare() {
        assertTrue(comparator.compare(abc, def) < 0);
        assertTrue(comparator.compare(ghi, def) > 0);
        assertEquals(0, comparator.compare(ghi, ghi));
    }
}