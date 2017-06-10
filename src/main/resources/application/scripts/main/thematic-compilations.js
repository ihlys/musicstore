/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/thematic-compilations.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  $('.music-content').itemsWithContent({ itemsSelector: 'div.thematic-compilation[data-content-url]'/*[- */, prototypingMode: true/* -]*/ });

  $('.music-content__collection').gridCollection().pageScroller({
    scrollPadding: 65,
    nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/thematic-compilations'),
    /*[- */
    prototypingMode: true,
    /* -]*/
  });
});
