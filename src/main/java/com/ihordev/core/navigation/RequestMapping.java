package com.ihordev.core.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("uninitialized")
@Component
public class RequestMapping {

    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @SuppressWarnings("unchecked")
    public RequestMatchingResult matchRequest(HttpServletRequest request) {
        try {
            HandlerExecutionChain handler = handlerMapping.getHandler(request);
            Map<String, String> matchedPathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String matchedPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            return new RequestMatchingResult(isHandlerFound(handler), matchedPattern, matchedPathVariables);
        } catch (Exception e) {
            throw new NavigationException(e);
        }
    }

    private boolean isHandlerFound(HandlerExecutionChain handler) {
        return !Objects.equals(handler, handlerMapping.getDefaultHandler());
    }

}
