package com.ihordev.core.util;

import org.junit.Assert;
import org.junit.Test;

import static com.ihordev.core.util.UrlUtils.rewriteQueryParameter;


public class UrlUtilsTests {

    @Test
    public void shouldRewriteUrlParamThatIsPlacedInCenter() {
        String urlString = "http://localhost:8080/application?one=15&two=30";

        String expectedUrlString = "http://localhost:8080/application?one=25&two=30";
        String actualUrlString = rewriteQueryParameter(urlString, "one", "25");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldRewriteUrlParamThatIsPlacedAtTheEnd() {
        String urlString = "http://localhost:8080/application?one=15&two=30";

        String expectedUrlString = "http://localhost:8080/application?one=15&two=40";
        String actualUrlString = rewriteQueryParameter(urlString, "two", "40");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }
}
