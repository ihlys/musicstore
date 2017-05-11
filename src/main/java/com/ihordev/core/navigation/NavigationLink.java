package com.ihordev.core.navigation;

import org.checkerframework.checker.nullness.qual.Nullable;


public class NavigationLink {

    private String href;
    private String label;

    public String getHref() {
        return href;
    }

    public String getLabel() {
        return label;
    }

    public NavigationLink(String href, String label) {
        this.href = href;
        this.label = label;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationLink that = (NavigationLink) o;

        if (!href.equals(that.href)) return false;
        return label.equals(that.label);

    }

    @Override
    public int hashCode() {
        int result = href.hashCode();
        result = 31 * result + label.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NavigationLink{href=" + href + ", label=" + label + "}";
    }
}
