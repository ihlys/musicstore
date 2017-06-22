package com.ihordev.testutils;

import org.mockito.ArgumentMatcher;

import javax.servlet.http.HttpServletRequest;

public class MatcherUtils {

    private MatcherUtils() {
        throw new AssertionError("MatcherUtils cannot be instantiated.");
    }

    public static ArgumentMatcher<HttpServletRequest> requestWithURI(String requestURI) {
        return request -> request.getRequestURI().equals(requestURI);
    }
}
