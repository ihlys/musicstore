package com.ihordev.core.navigation;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MatchedRequestInfo {

    private String matchedPattern;
    private Map<String, String> matchedPathParams = new HashMap<>();
    private Locale locale;
    private String requestURL;

    public String getMatchedPattern() {
        return matchedPattern;
    }

    public Map<String, String> getMatchedPathParams() {
        return matchedPathParams;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public MatchedRequestInfo(String matchedPattern, @Nullable Map<String, String> matchedPathParams,
                              Locale locale, String requestURL) {
        this.matchedPattern = matchedPattern;
        if (matchedPathParams != null) { this.matchedPathParams.putAll(matchedPathParams); }
        this.locale = locale;
        this.requestURL = requestURL;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchedRequestInfo that = (MatchedRequestInfo) o;

        if (!matchedPattern.equals(that.matchedPattern)) return false;
        if (!matchedPathParams.equals(that.matchedPathParams)) return false;
        if (!locale.equals(that.locale)) return false;
        return requestURL.equals(that.requestURL);

    }

    @Override
    public int hashCode() {
        int result = matchedPattern.hashCode();
        result = 31 * result + matchedPathParams.hashCode();
        result = 31 * result + locale.hashCode();
        result = 31 * result + requestURL.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "locale=" + locale +
                ", requestURL='" + requestURL + '\'' +
                ", matchedPattern='" + matchedPattern + '\'' +
                ", matchedPathParams=" + matchedPathParams +
                '}';
    }
}
