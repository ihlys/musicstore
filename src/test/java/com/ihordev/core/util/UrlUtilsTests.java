package com.ihordev.core.util;

import org.junit.Assert;
import org.junit.Test;

import static com.ihordev.core.util.UrlUtils.setPathParam;
import static com.ihordev.core.util.UrlUtils.setQueryParameter;


public class UrlUtilsTests {

    @Test
    public void shouldSetUrlParam() {
        String urlString = "http://localhost:8080/application";

        String expectedUrlString = "http://localhost:8080/application?one=25";
        String actualUrlString = setQueryParameter(urlString, "one", "25");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldRewriteUrlParamThatIsPlacedInCenter() {
        String urlString = "http://localhost:8080/application?one=15&two=30";

        String expectedUrlString = "http://localhost:8080/application?one=25&two=30";
        String actualUrlString = setQueryParameter(urlString, "one", "25");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldRewriteUrlParamThatIsPlacedAtTheEnd() {
        String urlString = "http://localhost:8080/application?one=15&two=30";

        String expectedUrlString = "http://localhost:8080/application?one=15&two=40";
        String actualUrlString = setQueryParameter(urlString, "two", "40");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldSetPathParam() {
        String urlString = "http://localhost:8080/application";

        String expectedUrlString = "http://localhost:8080/application/40";
        String actualUrlString = setPathParam(urlString, "application", "40");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldRewritePathParamThatIsPlacedInCenter() {
        String urlString = "http://localhost:8080/application/15/other";

        String expectedUrlString = "http://localhost:8080/application/40/other";
        String actualUrlString = setPathParam(urlString, "application", "40");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }

    @Test
    public void shouldRewritePathParamThatIsPlacedAtTheEnd() {
        String urlString = "http://localhost:8080/application/15";

        String expectedUrlString = "http://localhost:8080/application/40";
        String actualUrlString = setPathParam(urlString, "application", "40");

        Assert.assertEquals(expectedUrlString, actualUrlString);
    }
}
