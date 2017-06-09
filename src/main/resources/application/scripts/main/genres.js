/* eslint spaced-comment: 0 */
/* global $ */
/* global thJsUrlMappings */

import 'Styles/genres.css';
import './music-content';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';

$(function () {
  const $genresDiv = $('.music-content__collection');

  $genresDiv.gridCollection()
    .pageScroller({
      scrollPadding: 65,
      nextPageUrl: /*[[${nextPageUrl}]]*/ thJsUrlMappings.get('/genres?page=0'),
      /*[- */
      prototypingMode: true,
      /* -]*/
    })
    .itemsWithContent({ itemsSelector: "button"/*[- */, prototypingMode: true }/* -]*/);
});
