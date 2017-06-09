/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/albums.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  const $albumsDiv = $('.music-content__collection');

  $albumsDiv.gridCollection()
    .pageScroller({
      scrollPadding: 75,
      nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres/1/artists/1/albums?page=0'),
      /*[- */
      prototypingMode: true,
      /* -]*/
    })
    .itemsWithContent(/*[- */{ prototypingMode: true }/* -]*/);
});
