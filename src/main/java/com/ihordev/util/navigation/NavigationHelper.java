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
    private List<CollectionSegment> collectionSegments = new ArrayList<>();
    private Pattern segmentsPattern;

    private NavigationHelper(MessageSource messageSource, List<CollectionSegment> collectionSegments) {
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

        int simpleSegmentGroup = collectionSegments.size() + 1; // this group is appended always last in expression

        while(matcher.find()) {
            String simpleSegment = matcher.group(simpleSegmentGroup);
            if (simpleSegment != null) {
                processSimpleSegment(simpleSegment, pathAccumulator, navPaths);
            }

            if (!collectionSegments.isEmpty()) {
                int collectionSegmentGroup = 1;
                do {
                    String collectionSegmentPath = matcher.group(collectionSegmentGroup);
                    if (collectionSegmentPath != null) {
                        processCollectionSegment(collectionSegmentPath, collectionSegmentGroup, pathAccumulator, navPaths);
                    }
                } while (collectionSegmentGroup++ < collectionSegments.size());
            }
        }

        return navPaths;
    }

    private void processSimpleSegment(String simpleSegment, StringBuilder pathAccumulator, List<PathNavigation> navPaths) {
        pathAccumulator.append("/").append(simpleSegment);
        String navText = localizer.getNavTextForSimpleSegment(simpleSegment);
        String navPath = pathAccumulator.toString();
        navPaths.add(new PathNavigation(navText, navPath));
    }

    private void processCollectionSegment(String collectionSegmentPath, int collectionSegmentGroup,
                                         StringBuilder pathAccumulator, List<PathNavigation> navPaths) {
        int slashIdx = collectionSegmentPath.indexOf("/");
        String collectionParentSegment = collectionSegmentPath.substring(0, slashIdx);
        String collectionItemSegment = collectionSegmentPath.substring(slashIdx + 1);

        // add collection parent
        processSimpleSegment(collectionParentSegment, pathAccumulator, navPaths);

                                                // regex groups starts from 1, segments in list starts from 0
        CollectionSegment segmentForCurrentGroup = collectionSegments.get(collectionSegmentGroup - 1);
        if (segmentForCurrentGroup instanceof HierarchicalSegment) {
            HierarchicalSegment hierarchicalSegment = (HierarchicalSegment) segmentForCurrentGroup;

            // add hierarchy line of items
            List<String> hierarchyLine = hierarchicalSegment.getHierarchyLine(collectionItemSegment);
            for (ListIterator<String> iter = hierarchyLine.listIterator(); iter.hasNext(); ) {
                String hierarchyLineItem = iter.next();
                String itemNavText = localizer.getNavTextForCollectionSegment(collectionParentSegment, hierarchyLineItem);
                String itemNavPath = pathAccumulator.toString() + "/" + hierarchyLineItem;
                navPaths.add(new PathNavigation(itemNavText, itemNavPath));
                if (!iter.hasNext()) {
                    pathAccumulator.append("/").append(hierarchyLineItem); // hierarchy top accumulate
                }
            }
        } else {
            pathAccumulator.append("/").append(collectionItemSegment);
            String itemNavText = localizer.getNavTextForCollectionSegment(collectionParentSegment, collectionItemSegment);
            String itemNavPath = pathAccumulator.toString();
            navPaths.add(new PathNavigation(itemNavText, itemNavPath));
        }

    }

    private Pattern createRegularExpressionPattern() {
        // regular expression has composite structure like this:
        //      - group 1 -            - group N -            - group N+1 -
        // (collection_segment1) | (collection_segmentN) | (all simple segments)
        String patternString;
        if (collectionSegments.isEmpty()) {
            patternString = PATTERN_FORMAT_SIMPLE_SEGMENT;
        } else {
            patternString = collectionSegments.stream()
                    .map(CollectionSegment::getSegment)
                    .map(segment -> String.format(PATTERN_FORMAT_HIERARCHY_LINE, segment))
                    .collect(collectingAndThen(joining("|"), rs -> rs.concat("|" + PATTERN_FORMAT_SIMPLE_SEGMENT)));
        }

        logger.debug("Pattern string: {}", patternString);

        return Pattern.compile(patternString);
    }


    public static class Builder {

        private final MessageSource messageSource;
        private List<CollectionSegment> collectionSegments = new ArrayList<>();

        public Builder(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        public Builder addHierarchicalSegment(String segment, NavigableHierarchyService navigableHierarchyService) {
            collectionSegments.add(new HierarchicalSegment(segment, navigableHierarchyService));
            return this;
        }

        public Builder addCollectionSegment(String collectionSegment) {
            collectionSegments.add(new CollectionSegment(collectionSegment));
            return this;
        }

        public NavigationHelper build() {
            return new NavigationHelper(messageSource, collectionSegments);
        }
    }
}
