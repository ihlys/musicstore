package com.ihordev.util.navigation;

import org.springframework.context.MessageSource;

import java.util.Locale;


public class NavigationTextLocalizer {

    private static final String NAVIGATION_PREFIX = "Navigation.";

    private MessageSource messageSource;
    private Locale locale;

    public NavigationTextLocalizer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getNavTextForSimpleSegment(String segment) {
        return messageSource.getMessage(NAVIGATION_PREFIX + segment, null, locale);
    }

    public String getNavTextForCollectionSegment(String parent, String item) {
        return "temp";
    }
}
