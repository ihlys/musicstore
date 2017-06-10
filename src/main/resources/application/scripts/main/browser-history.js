/* eslint spaced-comment: 0 */
/* global window, $ */

import fetch from './ajax';

window.onpopstate = function (event) {
  console.log("onpop: " + event.url);
  const promise = fetch(event.url/*[- */, true /* -]*/);
  promise
    .then((request) => {
      if (event.url.contains('/genres')) {
        $('music-content').html(request.response);
      } else {
        $('main').html(request.response);
      }
    })
    .catch(console.log);
};
