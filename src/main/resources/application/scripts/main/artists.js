/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/artists.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  const $artistsDiv = $('.music-content__collection');

  $artistsDiv.gridCollection()
    .pageScroller({
      scrollPadding: 25,
      nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres/1/artists?page=0'),
      /*[- */
      prototypingMode: true,
      /* -]*/
    })
    .itemsWithContent(/*[- */{ prototypingMode: true }/* -]*/);
});
