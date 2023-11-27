package com.example.burger42;

import static org.junit.Assert.assertTrue;


import com.example.burger42.Game.DragFilter;

import org.junit.Test;

public class DragFilterTest {
    @Test
    public void hasAtLeastOneEqualFilterTagTest() {
        DragFilter dragFilter = new DragFilter();
        dragFilter.addFilterTag("1");
        dragFilter.addFilterTag("100");
        dragFilter.addFilterTag("Test");
        DragFilter dragFilter2 = new DragFilter();
        dragFilter2.addFilterTag("100");
        assertTrue(dragFilter.hasAtLeastOneEqualFilterTag(dragFilter2));
    }
}
