package com.ihordev.core.navigation;

import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Map;


public class RequestMatchingResult {

    private boolean isRequestHasMatch;

    private @Nullable String matchedPattern;
    private @Nullable Map<String, String> matchedPathParams;

    public RequestMatchingResult(boolean isRequestHasMatch,
                                 @Nullable String matchedPattern,
                                 @Nullable Map<String, String> matchedPathParams) {
        this.isRequestHasMatch = isRequestHasMatch;
        if (isRequestHasMatch) {
            assert matchedPattern != null : "matchedPattern cannot be null if isRequestHasMatch is true";
            this.matchedPattern = matchedPattern;
        }
        this.matchedPathParams = matchedPathParams;
    }

    @SuppressWarnings("nullness") // expression already checked during object creation in constructor
    @EnsuresNonNullIf(result = true, expression = "getMatchedPattern()")
    public boolean isRequestHasMatch() {
        return isRequestHasMatch;
    }

    @Pure
    public @Nullable String getMatchedPattern() {
        return matchedPattern;
    }

    public @Nullable Map<String, String> getMatchedPathParams() {
        return matchedPathParams;
    }

}
