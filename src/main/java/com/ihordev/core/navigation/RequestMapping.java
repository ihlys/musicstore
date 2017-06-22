package com.ihordev.core.navigation;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>A class for matching http requests with application request mapping.
 * It wraps a {@link RequestMappingHandlerMapping} instance and uses its
 * {@link RequestMappingHandlerMapping#getHandler} method for triggering
 * request matching and
 * {@link HandlerMapping#URI_TEMPLATE_VARIABLES_ATTRIBUTE} and
 * {@link HandlerMapping#BEST_MATCHING_PATTERN_ATTRIBUTE} for obtaining
 * information about match.
 */

@SuppressWarnings("initialization.fields.uninitialized")
@Component
public class RequestMapping {

    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * Tries to match http request with application request mapping and returns
     * result in {@code RequestMatchingResult} instance.
     *
     * @param request  the http request for which matching must be performed
     * @return the instance of {@code RequestMatchingResult} containing
     *         information about matching
     */
    @SuppressWarnings("unchecked")
    public RequestMatchingResult matchRequest(HttpServletRequest request) {
        try {
            handlerMapping.getHandler(request);
            @Nullable Map<String, String> matchedPathVariables =
                    (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            @Nullable String matchedPattern =
                    (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            return new RequestMatchingResult(matchedPattern, matchedPathVariables);
        } catch (Exception e) {
            throw new NavigationException(e);
        }
    }
}
