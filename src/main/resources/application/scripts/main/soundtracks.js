/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/soundtracks.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  const $soundtracksDiv = $('.music-content__collection');

  $soundtracksDiv.gridCollection()
    .pageScroller({
      scrollPadding: 75,
      nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/soundtracks?page=0'),
      /*[- */
      prototypingMode: true,
      /* -]*/
    })
    .itemsWithContent(/*[- */{ prototypingMode: true }/* -]*/);
});
