/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/soundtracks.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  $('.music-content').itemsWithContent({ itemsSelector: 'div.soundtrack[data-content-url]'/*[- */, prototypingMode: true/* -]*/ });

  $('.music-content__collection').gridCollection().pageScroller({
    scrollPadding: 75,
    nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/soundtracks'),
    /*[- */
    prototypingMode: true,
    /* -]*/
  });
});
