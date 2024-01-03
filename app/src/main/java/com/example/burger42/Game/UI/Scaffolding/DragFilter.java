package com.example.burger42.Game.UI.Scaffolding;

/**
 * The DragFilter checks whether a filter value is present in two filters.
 * This is used to check whether a drag shadow has an influence on a drag area.
 */
public interface DragFilter extends Iterable<String> {

    /**
     * addFilterTag adds a tag
     *
     * @param tag
     */
    public DragFilter addFilterTag(String tag);

    public DragFilter removeFilterTag(String tag);

    public boolean hasAtLeastOneEqualFilterTag(DragFilter other);
}
