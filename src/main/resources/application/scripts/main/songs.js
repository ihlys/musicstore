/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/songs.css';
import './music-content';
import './jquery.page-scroller';

$(function () {
  const $songsDiv = $('.music-content__collection');

  $songsDiv.pageScroller({
    nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres/1/songs'),
    /*[- */
    prototypingMode: true,
    /* -]*/
  });
});
