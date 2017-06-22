package com.ihordev.core.navigation;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A class representing navigation point in navigation structure.
 */

public class NavigationLink {

    private String uri;
    private String label;


    public NavigationLink(String uri, String label) {
        this.uri = uri;
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationLink that = (NavigationLink) o;

        if (!uri.equals(that.uri)) return false;
        return label.equals(that.label);

    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + label.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NavigationLink{" +
                "uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
