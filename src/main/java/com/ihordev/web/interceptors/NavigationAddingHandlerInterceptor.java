package com.ihordev.web.interceptors;

import com.ihordev.core.navigation.Navigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.core.util.AnnotationUtils.isWithNavigationLinks;
import static com.ihordev.core.util.UrlUtils.getFullUrl;


@Component
public class NavigationAddingHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NavigationAddingHandlerInterceptor.class);

    private Navigation navigation;

    @Autowired
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }


    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && isWithNavigationLinks((HandlerMethod) handler)) {
            logger.debug("adding navigation for request with url: {}", getFullUrl(request));

            modelAndView.addObject("navigation", navigation.getNavigationLinks(request));
        }

    }

}
