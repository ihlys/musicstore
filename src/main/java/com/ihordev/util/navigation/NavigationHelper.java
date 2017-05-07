package com.ihordev.util.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;


public class NavigationHelper {

    private static final Logger logger = LoggerFactory.getLogger(NavigationHelper.class);

    // must capture substring like this: {collection}/{item}
    private static final String PATTERN_FORMAT_HIERARCHY_LINE = "(%s\\/[^\\/]+)";
    // must capture substring like this: {segment}
    private static final String PATTERN_FORMAT_SIMPLE_SEGMENT = "([^\\/]+)";

    private NavigationTextLocalizer localizer;
    private List<HierarchicalSegment> hierarchicalSegments = new ArrayList<>();
    private List<String> collectionSegments = new ArrayList<>();
    private Pattern segmentsPattern;

    private NavigationHelper(MessageSource messageSource,
                             List<HierarchicalSegment> hierarchicalSegments,
                             List<String> collectionSegments) {
        this.hierarchicalSegments = hierarchicalSegments;
        this.collectionSegments = collectionSegments;
        this.segmentsPattern = createRegularExpressionPattern();
        this.localizer = new NavigationTextLocalizer(messageSource);
    }

    public List<PathNavigation> createNavigationPaths(String fromUrl, Locale locale) {
        Objects.requireNonNull(fromUrl);
        logger.debug("creating navigation from path: {}", fromUrl);

        localizer.setLocale(locale);
        List<PathNavigation> navigationPaths = createNavPaths(fromUrl);

        logger.debug("created navigation paths: {}", navigationPaths);

        return navigationPaths;
    }

    private List<PathNavigation> createNavPaths(String fromUrlString) {
        assert fromUrlString != null;

        List<PathNavigation> navPaths = new ArrayList<>();
        navPaths.add(new PathNavigation(localizer.getNavTextForSimpleSegment("root"), "/")); // add root path by default

        Matcher matcher = segmentsPattern.matcher(fromUrlString);

        StringBuilder pathAccumulator = new StringBuilder();

        int simpleSegmentGroup = hierarchicalSegments.size() + 1; // this group is appended always last in expression

        while(matcher.find()) {
            String simpleSegment = matcher.group(simpleSegmentGroup);
            if (simpleSegment != null) {
                processSimpleSegment(simpleSegment, pathAccumulator, navPaths);
            }

            if (!hierarchicalSegments.isEmpty()) {
                int hierarchicalSegmentGroup = 1;
                do {
                    String hierarchicalSegmentPath = matcher.group(hierarchicalSegmentGroup);
                    if (hierarchicalSegmentPath != null) {
                        processHierarchicalSegment(hierarchicalSegmentPath, hierarchicalSegmentGroup, pathAccumulator, navPaths);
                    }
                } while (hierarchicalSegmentGroup++ < hierarchicalSegments.size());
            }
/*
            // collection segment group always follows after hierarchical
            if (!collectionSegments.isEmpty()) {
                int collectionSegmentGroup = 1 + hierarchicalSegments.size();
                do {
                    String collectionSegmentPath = matcher.group(collectionSegmentGroup);
                    if (collectionSegmentPath != null) {
                        processHierarchicalSegment(hierarchicalSegmentPath, hierarchicalSegmentGroup, pathAccumulator, navPaths);
                    }
                } while (collectionSegmentGroup++ < collectionSegments.size() + hierarchicalSegments.size());
            }*/
        }

        return navPaths;
    }

    private void processSimpleSegment(String simpleSegment, StringBuilder pathAccumulator, List<PathNavigation> navPaths) {
        System.out.println("processSimpleSegment() <- " + simpleSegment);
        pathAccumulator.append("/").append(simpleSegment);
        String navPath = localizer.getNavTextForSimpleSegment(simpleSegment);
        String navText = pathAccumulator.toString();
        navPaths.add(new PathNavigation(navPath, navText));
    }

    private void processHierarchicalSegment(String hierarchicalSegmentPath, int hierarchicalSegmentGroup,
                                            StringBuilder pathAccumulator, List<PathNavigation> navPaths) {
        int slashIdx = hierarchicalSegmentPath.indexOf("/");
        String hierarchyParentSegment = hierarchicalSegmentPath.substring(0, slashIdx);
        String hierarchyItemSegment = hierarchicalSegmentPath.substring(slashIdx + 1);

        // add hierarchy parent
        processSimpleSegment(hierarchyParentSegment, pathAccumulator, navPaths);

        // add hierarchy line of items                  // regex groups starts from 1, segments in list from 0
        HierarchicalSegment segmentForCurrentGroup = hierarchicalSegments.get(hierarchicalSegmentGroup - 1);
        List<String> hierarchyLine = segmentForCurrentGroup.getHierarchyLine(hierarchyItemSegment);
        for (ListIterator<String> iter = hierarchyLine.listIterator(); iter.hasNext(); ) {
            String hierarchyLineItem = iter.next();
            String itemNavText = localizer.getNavTextForHierarchicalSegment(hierarchyParentSegment, hierarchyLineItem);
            String itemNavPath = pathAccumulator.toString() + "/" + hierarchyLineItem;
            navPaths.add(new PathNavigation(itemNavText, itemNavPath));
            if (!iter.hasNext()) {
                pathAccumulator.append("/").append(hierarchyLineItem); // hierarchy top accumulate
            }
        }
    }

/*    private void processCollectionSegment(String collectionSegment, StringBuilder pathAccumulator, List<PathNavigation> navPaths) {
        System.out.println("processCollectionSegment() <- " + collectionSegment);
        pathAccumulator.append("/").append(collectionSegment);
        String navPath = localizer.getNavTextForSimpleSegment(simpleSegment);
        String navText = pathAccumulator.toString();
        navPaths.add(new PathNavigation(navPath, navText));
    }*/

    private Pattern createRegularExpressionPattern() {
        // regular expression has composite structure like this:
        //      - group 1 -                 - group N -             - group N+1 -
        // (hierarchical_segment1) | (hierarchical_segmentN) | (all simple segments)
        String patternString;
        if (hierarchicalSegments.isEmpty()) {
            patternString = PATTERN_FORMAT_SIMPLE_SEGMENT;
        } else {
            patternString = hierarchicalSegments.stream()
                    .map(HierarchicalSegment::getSegment)
                    .map(segment -> String.format(PATTERN_FORMAT_HIERARCHY_LINE, segment))
                    .collect(collectingAndThen(joining("|"), rs -> rs.concat("|" + PATTERN_FORMAT_SIMPLE_SEGMENT)));
        }

        logger.debug("Pattern string: {}", patternString);

        return Pattern.compile(patternString);
    }


    public static class Builder {

        private final MessageSource messageSource;
        private List<HierarchicalSegment> hierarchicalSegments = new ArrayList<>();
        private List<String> collectionSegments = new ArrayList<>();

        public Builder(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        public Builder addHierarchicalSegment(String segment, NavigableHierarchyService navigableHierarchyService) {
            hierarchicalSegments.add(new HierarchicalSegment(segment, navigableHierarchyService));
            return this;
        }

        public Builder addCollectionSegment(String collectionSegment) {
            collectionSegments.add(collectionSegment);
            return this;
        }

        public NavigationHelper build() {
            return new NavigationHelper(messageSource, hierarchicalSegments, collectionSegments);
        }
    }
}
