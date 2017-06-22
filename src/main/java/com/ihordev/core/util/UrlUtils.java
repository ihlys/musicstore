package com.ihordev.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class UrlUtils {

    private UrlUtils() {
        throw new AssertionError("UrlUtil cannot be instantiated.");
    }

    /**
     * Return full URL address of specified {@code HttpServlet request} instance
     * including query string parameters.
     *
     * @param request  the request for which full URL address with query string
     *                 parameters must be returned
     * @return URL string with query string parameters
     */
    public static String getFullUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String requestQueryString = request.getQueryString();
        if (requestQueryString != null) {
            requestUrl.append("?").append(requestQueryString);
        }
        return requestUrl.toString();
    }

    /**
     * Removes last path segment from specified URI string.
     *
     * @param fromUriString  the URI string from which last path segment must be
     *                       removed
     * @return URI string without last path segment
     */
    public static String cutSegmentFromEnd(String fromUriString) {
        int segmentIdx = fromUriString.lastIndexOf('/', fromUriString.length());
        if (segmentIdx == 0 && fromUriString.length() > 1) {
            return fromUriString.substring(0, segmentIdx + 1);
        } else {
            return fromUriString.substring(0, segmentIdx);
        }
    }

    /**
     * Sets query parameter to specified value in URL string. If URL string already
     * has this parameter then returns URL string with rewritten value, otherwise
     * returns URL string with new query parameter added.
     *
     * @param url  the URL string where query parameter must be set
     * @param paramName  the query parameter to be set name
     * @param paramValue  the query parameter to be set value
     * @return URL string with query parameter set
     */
    public static String setQueryParameter(String url, String paramName, String paramValue) {
        int paramIdx = url.indexOf(paramName + "=");
        if (paramIdx == -1) {
            return url + "?" + paramName + "=" + paramValue;
        } else {
            int paramValueIdx = paramIdx + paramName.length() + 1;
            String beforeParamValueUrlPart = url.substring(0, paramValueIdx);
            int paramEndIdx = url.indexOf(paramValueIdx, '&');
            paramEndIdx = (paramEndIdx != -1) ? paramEndIdx : url.length();
            String afterParamValueUrlPart = url.substring(paramEndIdx, url.length());
            return beforeParamValueUrlPart + paramValue + afterParamValueUrlPart;
        }
    }

    /**
     * Sets path parameter to specified value in URL string. If URL string already
     * has this parameter then returns URL string with rewritten value, otherwise
     * returns URL string with new path parameter added.
     *
     * @param url  the URL string where path parameter must be set
     * @param segmentBeforeParam  the path segment preceding to path parameter
     *                            to be set
     * @param paramValue  the path parameter to be set value
     * @return URL string with query parameter set
     */
    public static String setPathParam(String url, String segmentBeforeParam, String paramValue) {
        Matcher matcher = Pattern.compile("\\/(" + segmentBeforeParam + ")\\/((.+)\\/|(.+))").matcher(url);
        if (!matcher.find()) {
            return url + "/" + paramValue;
        } else {
            int paramValueIdx = (matcher.start(3) != -1) ? matcher.start(3) : matcher.start(4);
            String beforeParamValueUrlPart = url.substring(0, paramValueIdx);
            int paramEndIdx = (matcher.end(3) != -1 ) ? matcher.end(3) : matcher.end(4);
            String afterParamValueUrlPart = url.substring(paramEndIdx, url.length());
            return beforeParamValueUrlPart + paramValue + afterParamValueUrlPart;
        }
    }
}
