/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/albums.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  $('.music-content').itemsWithContent({ itemsSelector: 'div.album[data-content-url]'/*[- */, prototypingMode: true/* -]*/ });

  $('.music-content__collection').gridCollection().pageScroller({
    scrollPadding: 75,
    nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres/1/artists/1/albums'),
    /*[- */
    prototypingMode: true,
    /* -]*/
  });
});
