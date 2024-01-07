package com.example.burger42.Game.UI.Scaffolding;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * a Basic implementation of the DragFilter
 */
public class BasicDragFilter implements DragFilter {
    private final TreeSet<String> filterTags = new TreeSet<>();

    /**
     * adds a FilterTag to the List of filter tags
     *
     * @param tag the tag to add.
     * @return this BasicDragFilter it self to use in filter chain
     */
    public BasicDragFilter addFilterTag(String tag) {
        filterTags.add(tag);
        return this;
    }

    /**
     * removes a FilterTag from the List of filter tags
     *
     * @param tag the tag to remove.
     * @return this BasicDragFilter it self to use in filter chain
     */
    public BasicDragFilter removeFilterTag(String tag) {
        filterTags.remove(tag);
        return this;
    }

    /**
     * @param other the other DragFilter to compare with this.
     * @return true if a filter at least one filter tag exists in both DragFilter else false
     */
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
