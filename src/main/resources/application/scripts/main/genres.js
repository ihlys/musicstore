/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/genres.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  $('.music-content').itemsWithContent({ itemsSelector: 'div.genre button[data-content-url]'/*[- */, prototypingMode: true }/* -]*/);

  $('.music-content__collection').gridCollection().pageScroller({
    scrollPadding: 65,
    nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres'),
    /*[- */
    prototypingMode: true,
    /* -]*/
  });
});
