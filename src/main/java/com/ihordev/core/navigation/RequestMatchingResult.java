package com.ihordev.core.navigation;

import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;


/**
 * A class representing information about request matching result in
 * {@link RequestMapping}.
 */

public class RequestMatchingResult {

    private @Nullable String matchedPattern;
    private @Nullable Map<String, String> matchedPathParams;

    public RequestMatchingResult(@Nullable String matchedPattern,
                                 @Nullable Map<String, String> matchedPathParams) {
        this.matchedPattern = matchedPattern;
        this.matchedPathParams = matchedPathParams;
    }

    @EnsuresNonNullIf(result = true, expression = {"this.matchedPattern", "this.matchedPathParams"})
    @SuppressWarnings("contracts.conditional.postcondition.not.satisfied")
    // if matchedPattern != null (request has match), then matchedPathParams is always not null (at least empty)
    public boolean isRequestHasMatch() {
        return matchedPattern != null;
    }

    public String getMatchedPattern() {
        if (isRequestHasMatch()) {
            return matchedPattern;
        } else {
            throw new IllegalStateException("Cannot get matchedPattern: request has not match in request mapping.");
        }
    }

    public Map<String, String> getMatchedPathParams() {
        if (isRequestHasMatch()) {
            return matchedPathParams;
        } else {
            throw new IllegalStateException("Cannot get matchedPathParams: request has not match in request mapping.");
        }
    }
}
