package com.ihordev.util.navigation;

import java.util.List;


public class HierarchicalSegment extends CollectionSegment {

    private NavigableHierarchyService navigableHierarchyService;

    public HierarchicalSegment(String hierarchyParentSegment, NavigableHierarchyService navigableHierarchyService) {
        super(hierarchyParentSegment);
        this.navigableHierarchyService = navigableHierarchyService;
    }

    public List<String> getHierarchyLine(String hierarchyItemSegment) {
        return navigableHierarchyService.getHierarchyLine(hierarchyItemSegment);
    }

    @Override
    public String toString() {
        return "HierarchicalSegment{" + getSegment() + "}";
    }
}
