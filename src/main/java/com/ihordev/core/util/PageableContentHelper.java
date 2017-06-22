package com.ihordev.core.util;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.core.util.UrlUtils.getFullUrl;
import static com.ihordev.core.util.UrlUtils.setQueryParameter;
import static java.lang.String.format;


/**
 * <p>A helper class for populating {@code Model} instance with
 * pageable data and returning corresponding view name from controllers
 * request methods, depending on request type.
 */
@SuppressWarnings("initialization.fields.uninitialized")
@Component
public class PageableContentHelper {

    private Model model;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void set(Model model, HttpServletRequest request, HttpServletResponse response) {
        this.model = model;
        this.request = request;
        this.response = response;
    }

    /**
     * Adds to {@code Model} instance various page attributes and generates corresponding
     * view name depending on request type.
     * <p>Attributes:
     * <ol>
     *     <li>Entities page attribute - a page of data. It's name is taken from
     *         {@code entitiesPageModelAttribute} parameter. It is always added.</li>
     *     <li>initialNextPageUrl attribute - URL address that is used by page when
     *         it is needed to request next page of data first time.</li>
     * </ol>
     * <p>Request can be:
     * <p>1. Initial and synchronous - when page is first ever requested.
     * "initialNextPageUrl" attribute is also added and view name is equal to
     * {@code entitiesPageMainView} parameter.
     * <p>2. Initial and asynchronous - when user requests another page from already
     * loaded current page. "initialNextPageUrl" attribute is also added and view
     * name is resolved as "{@code entitiesPageMainView :: #main__content}".
     * <p>3. Asynchronous with page parameter - when next page of currently viewed data
     * is requested. View name is resolved as "{@code fragments/entitiesPageMainView}".
     *
     * @param entitiesPageModelAttribute  the name of entities page attribute
     * @param entitiesPageMainView  the name of entities page view
     * @param entitiesPage  the page of entities instances
     *
     * @return view name that corresponds to request type
     */

    public String processRequest(String entitiesPageModelAttribute, String entitiesPageMainView,
                                 Slice<?> entitiesPage) {
        model.addAttribute(entitiesPageModelAttribute, entitiesPage);
        String initialNextPageUrl = (entitiesPage.hasNext())
                ? setQueryParameter(getFullUrl(request), "page", String.valueOf(entitiesPage.getNumber() + 1))
                : "";
        if (RequestUtils.isAjaxRequest(request)) {
            if (request.getParameter("page") == null) {
                model.addAttribute("initialNextPageUrl", initialNextPageUrl);
                return format("%s :: #main__content", entitiesPageMainView);
            } else {
                response.addHeader(HttpHeaders.LINK, "<" + initialNextPageUrl + ">; rel=next");
                return format("fragments/%1$s", entitiesPageMainView);
            }
        } else {
            model.addAttribute("initialNextPageUrl", initialNextPageUrl);
            return entitiesPageMainView;
        }
    }
}