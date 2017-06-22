/* global window */
/* global thPrototypingMode */

/**
 * <p>Manages browsers history object states when user clicks back or forward
 * button by listening to {@link onpopstate} events. If event has null state
 * then it loads content with constant URL address equal to "/".
 *
 * @module browser-history
 */

import { loadPage } from './application';

if (!thPrototypingMode) {
  const INITIAL_STATE = {
    pageUrl: '/',
  };

  window.onpopstate = function (event) {
    const poppedState = (event.state == null) ? INITIAL_STATE : event.state;
    loadPage(poppedState.pageUrl);
  };
}
