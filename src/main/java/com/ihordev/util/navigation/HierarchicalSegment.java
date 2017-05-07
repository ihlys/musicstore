package com.ihordev.util.navigation;

import java.util.List;


public class HierarchicalSegment {

    private String hierarchyParentSegment;
    private NavigableHierarchyService navigableHierarchyService;

    public HierarchicalSegment(String hierarchyParentSegment, NavigableHierarchyService navigableHierarchyService) {
        this.hierarchyParentSegment = hierarchyParentSegment;
        this.navigableHierarchyService = navigableHierarchyService;
    }

    public List<String> getHierarchyLine(String hierarchyItemSegment) {
        return navigableHierarchyService.getHierarchyLine(hierarchyItemSegment);
    }

    public String getSegment() {
        return hierarchyParentSegment;
    }

    @Override
    public String toString() {
        return "HierarchicalSegment{" + hierarchyParentSegment + "}";
    }
}
