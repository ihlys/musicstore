package com.ihordev.core.navigation;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing information about request that was successfully
 * matched in {@link RequestMapping}.
 */

public class MatchedRequestInfo {

    private String matchedPattern;
    private Map<String, String> matchedPathParams = new HashMap<>();
    private String requestURI;

    public String getMatchedPattern() {
        return matchedPattern;
    }

    public Map<String, String> getMatchedPathParams() {
        return matchedPathParams;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public MatchedRequestInfo(String matchedPattern, Map<String, String> matchedPathParams,
                              String requestURI) {
        this.matchedPattern = matchedPattern;
        this.matchedPathParams = matchedPathParams;
        this.requestURI = requestURI;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchedRequestInfo that = (MatchedRequestInfo) o;

        if (!matchedPattern.equals(that.matchedPattern)) return false;
        if (!matchedPathParams.equals(that.matchedPathParams)) return false;
        return requestURI.equals(that.requestURI);

    }

    @Override
    public int hashCode() {
        int result = matchedPattern.hashCode();
        result = 31 * result + matchedPathParams.hashCode();
        result = 31 * result + requestURI.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "requestURI='" + requestURI + '\'' +
                ", matchedPattern='" + matchedPattern + '\'' +
                ", matchedPathParams=" + matchedPathParams +
                '}';
    }
}
