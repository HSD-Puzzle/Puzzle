package com.example.burger42.Game.UI.Scaffolding;

public interface DragFilter extends Iterable<String> {

    public void addFilterTag(String tag);

    public void removeFilterTag(String tag);

    public boolean hasAtLeastOneEqualFilterTag(DragFilter other);
}
