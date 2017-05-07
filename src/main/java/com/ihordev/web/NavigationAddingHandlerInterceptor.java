package com.ihordev.web;

import com.ihordev.util.navigation.NavigationHelper;
import com.ihordev.util.navigation.NavigationTextLocalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NavigationAddingHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NavigationAddingHandlerInterceptor.class);

    @Autowired
    private MessageSource messageSource;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    //private NavigationHelper navigationHelper = new NavigationHelper(messageSource);

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception
    {

        if (modelAndView != null)
        {
            String url = urlPathHelper.getPathWithinApplication(request);
            logger.debug("adding navigation for request with path: %s", url);
            //modelAndView.addObject("navigation", navigationHelper.createNavigationPaths(url, request.getLocale()));
        }
    }

}
