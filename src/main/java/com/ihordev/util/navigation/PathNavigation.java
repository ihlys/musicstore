package com.ihordev.util.navigation;


public class PathNavigation {

    private String navText;
    private String navPath;

    public PathNavigation(String navText, String navPath) {
        this.navText = navText;
        this.navPath = navPath;
    }

    public String getNavText() {
        return navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }

    public String getNavPath() {
        return navPath;
    }

    public void setNavPath(String navPath) {
        this.navPath = navPath;
    }

    @Override
    public String toString() {
        return "PathNavigation{" + navText + " = " + navPath + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathNavigation that = (PathNavigation) o;

        if (!navText.equals(that.navText)) return false;
        return navPath.equals(that.navPath);

    }

    @Override
    public int hashCode() {
        int result = navText.hashCode();
        result = 31 * result + navPath.hashCode();
        return result;
    }
}
