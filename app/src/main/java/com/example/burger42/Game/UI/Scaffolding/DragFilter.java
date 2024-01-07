package com.example.burger42.Game.UI.Scaffolding;

/**
 * The DragFilter checks whether a filter value is present in two filters.
 * This is used to check whether a drag shadow has an influence on a drag area.
 */
public interface DragFilter extends Iterable<String> {

    /**
     * adds a FilterTag to the List of filter tags
     *
     * @param tag the tag to add.
     * @return this DragFilter it self to use in filter chain
     */
    public DragFilter addFilterTag(String tag);

    /**
     * removes a FilterTag from the List of filter tags
     *
     * @param tag the tag to remove.
     * @return this DragFilter it self to use in filter chain
     */
    public DragFilter removeFilterTag(String tag);

    /**
     * @param other the other DragFilter to compare with this.
     * @return true if a filter at least one filter tag exists in both DragFilter else false
     */
    public boolean hasAtLeastOneEqualFilterTag(DragFilter other);
}
