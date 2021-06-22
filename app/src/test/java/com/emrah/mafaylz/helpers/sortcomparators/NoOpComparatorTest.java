package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoOpComparatorTest {

    FileSearchResultItem pdf;
    FileSearchResultItem docx;
    FileSearchResultItem txt;
    FileSearchResultItem first;
    FileSearchResultItem second;
    FileSearchResultItem third;
    FileSearchResultItem abc;
    FileSearchResultItem def;
    FileSearchResultItem ghi;
    final NoOpComparator comparator = new NoOpComparator();

    @Before
    public void setUp() {
        pdf = new FileSearchResultItem("pdf", "/pdf", System.currentTimeMillis(), ".pdf");
        docx = new FileSearchResultItem("docx", "/docx", System.currentTimeMillis(), ".docx");
        txt = new FileSearchResultItem("txt", "/txt", System.currentTimeMillis(), ".txt");
        first = new FileSearchResultItem("First", "/first", System.currentTimeMillis() - 1000, ".pdf");
        second = new FileSearchResultItem("Second", "/second", System.currentTimeMillis(), ".pdf");
        third = new FileSearchResultItem("Third", "/third", System.currentTimeMillis() + 1000, ".pdf");
        abc = new FileSearchResultItem("Abc", "/abc", System.currentTimeMillis(), ".pdf");
        def = new FileSearchResultItem("def", "/def", System.currentTimeMillis(), ".pdf");
        ghi = new FileSearchResultItem("ghi", "/ghi", System.currentTimeMillis(), ".pdf");
    }

    @Test
    public void testCompare() {
        assertEquals(0, comparator.compare(docx, pdf));
        assertEquals(0, comparator.compare(txt, pdf));
        assertEquals(0, comparator.compare(txt, txt));

        assertEquals(0, comparator.compare(abc, def));
        assertEquals(0, comparator.compare(ghi, def));
        assertEquals(0, comparator.compare(ghi, ghi));

        assertEquals(0, comparator.compare(first, second));
        assertEquals(0, comparator.compare(third, second));
        assertEquals(0, comparator.compare(third, third));
    }

}