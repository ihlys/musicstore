/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/thematic-compilations.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  const $thematicCompilationsDiv = $('.music-content__collection');

  $thematicCompilationsDiv.gridCollection()
    .pageScroller({
      scrollPadding: 65,
      nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/thematic-compilations?page=0'),
      /*[- */
      prototypingMode: true,
      /* -]*/
    })
    .itemsWithContent(/*[- */{ prototypingMode: true }/* -]*/);
});
