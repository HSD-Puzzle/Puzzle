package com.example.burger42.Game.UI.Scaffolding;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.TreeSet;

public class BasicDragFilter implements DragFilter {
    private final TreeSet<String> filterTags = new TreeSet<>();

    public void addFilterTag(String tag) {
        filterTags.add(tag);
    }

    public void removeFilterTag(String tag) {
        filterTags.remove(tag);
    }

    public boolean hasAtLeastOneEqualFilterTag(DragFilter other) {
        for (String x : other) {
            if (filterTags.contains(x)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return filterTags.iterator();
    }
}
