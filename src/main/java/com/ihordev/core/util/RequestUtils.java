package com.ihordev.core.util;

import javax.servlet.http.HttpServletRequest;


public class RequestUtils {

    private RequestUtils() {
        throw new AssertionError("RequestUtils cannot be instantiated.");
    }

    /**
     * Determines if specified {@code HttpServletRequest} instance is an ajax
     * request by checking presence of "X-Requested-With" Header with value
     * "XMLHttpRequest".
     *
     * @param request  the request to check if it is an ajax request
     * @return true if specified request is an ajax request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
