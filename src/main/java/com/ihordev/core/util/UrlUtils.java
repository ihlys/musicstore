package com.ihordev.core.util;

import org.checkerframework.dataflow.qual.SideEffectFree;

import javax.servlet.http.HttpServletRequest;


public final class UrlUtils {

    private UrlUtils() {
        throw new AssertionError("UrlUtil cannot be instantiated.");
    }

    @SideEffectFree
    public static String getFullUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String requestQueryString = request.getQueryString();
        if (requestQueryString != null) {
            requestUrl.append("?").append(requestQueryString);
        }
        return requestUrl.toString();
    }

    @SideEffectFree
    public static String cutSegmentFromEnd(String fromUrlString) {
        int segmentIdx = fromUrlString.lastIndexOf('/', fromUrlString.length());
        if (segmentIdx == 0 && fromUrlString.length() > 1) {
            return fromUrlString.substring(0, segmentIdx + 1);
        } else {
            return fromUrlString.substring(0, segmentIdx);
        }
    }

    @SideEffectFree
    public static String rewriteQueryParameter(String url, String paramName, String paramValue) {
        int paramValueIdx = url.indexOf(paramName + "=") + paramName.length() + 1;
        String beforeParamValueUrlPart = url.substring(0, paramValueIdx);
        int paramEndIdx = url.indexOf(paramValueIdx, '&');
        paramEndIdx = (paramEndIdx != -1) ? paramEndIdx : url.length();
        String afterParamValueUrlPart = url.substring(paramEndIdx, url.length());
        return beforeParamValueUrlPart + paramValue + afterParamValueUrlPart;
    }

}
