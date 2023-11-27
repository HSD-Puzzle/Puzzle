package com.example.burger42.Game;

import java.util.List;
import java.util.TreeSet;

public class DragFilter {
    private final TreeSet<String> filterTags = new TreeSet<>();

    public void addFilterTag(String tag) {
        filterTags.add(tag);
    }

    public void removeFilterTag(String tag) {
        filterTags.remove(tag);
    }

    public boolean hasAtLeastOneEqualFilterTag(DragFilter other) {
        for (String x : other.filterTags) {
            if (filterTags.contains(x)) {
                return true;
            }
        }
        return false;
    }
}
