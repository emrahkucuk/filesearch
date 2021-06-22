package com.emrah.mafaylz.helpers.sortcomparators;

import com.emrah.mafaylz.model.FileSearchResultItem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtensionComparatorTest {
    FileSearchResultItem pdf;
    FileSearchResultItem docx;
    FileSearchResultItem txt;
    final ExtensionComparator comparator = new ExtensionComparator();

    @Before
    public void setUp() {
        pdf = new FileSearchResultItem("pdf", "/pdf", System.currentTimeMillis(), ".pdf");
        docx = new FileSearchResultItem("docx", "/docx", System.currentTimeMillis(), ".docx");
        txt = new FileSearchResultItem("txt", "/txt", System.currentTimeMillis(), ".txt");
    }

    @Test
    public void testCompare() {
        assertTrue(comparator.compare(docx, pdf) < 0);
        assertTrue(comparator.compare(txt, pdf) > 0);
        assertEquals(0, comparator.compare(txt, txt));
    }

}