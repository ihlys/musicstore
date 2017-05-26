package com.ihordev.core.navigation;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;


public class MatchedRequestInfo {

    private String matchedPattern;
    private Map<String, String> matchedPathParams = new HashMap<>();
    private String requestURL;

    public String getMatchedPattern() {
        return matchedPattern;
    }

    public Map<String, String> getMatchedPathParams() {
        return matchedPathParams;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public MatchedRequestInfo(String matchedPattern, @Nullable Map<String, String> matchedPathParams, String requestURL) {
        this.matchedPattern = matchedPattern;
        this.requestURL = requestURL;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchedRequestInfo that = (MatchedRequestInfo) o;

        if (!matchedPattern.equals(that.matchedPattern)) return false;
        if (!matchedPathParams.equals(that.matchedPathParams)) return false;
        return requestURL.equals(that.requestURL);

    }

    @Override
    public int hashCode() {
        int result = matchedPattern.hashCode();
        result = 31 * result + matchedPathParams.hashCode();
        result = 31 * result + requestURL.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "requestURL='" + requestURL + '\'' +
                ", matchedPattern='" + matchedPattern + '\'' +
                ", matchedPathParams=" + matchedPathParams +
                '}';
    }
}
