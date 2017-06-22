/* global history */

import $ from 'jquery';
import 'jquery-ui';
import { loadPage } from './application';

/**
 * <p>Listens to click events on items of container element on which it is applied
 * and loads content of item to which event belongs. Request that gets items
 * content has URL address specified by data attribute with
 * <code>'item-page-url'</code> name.
 *
 * @function external:"jQuery.fn".itemsWithContent
 *
 * @param {object} options the options object holding:
 *                 <ol>
 *                   <li><code>itemSelector</code> - the CSS selector for items;</li>
 *                   <li><code>prototypingMode</code> - determines if prototyping
 *                       mode is enabled. If enabled then state of history will
 *                       not be pushed when sending request for item content.
 *                 </ol>
 */

$.widget('ui.itemsWithContent', {

  options: {
    itemSelector: null,
    prototypingMode: false,
  },

  _create() {
    if (!this.options.itemSelector) throw Error('Required parameter itemSelector must be specified.');

    const widget = this;
    // eslint-disable-next-line no-unused-vars
    let isWaitingForAjaxResults = false;
    widget.element.on('click', this.options.itemSelector, function () {
      if (!isWaitingForAjaxResults) {
        isWaitingForAjaxResults = true;
        const itemPageUrl = $(this).data('item-page-url');
        loadPage(itemPageUrl, () => {
          if (!widget.options.prototypingMode) {
            history.pushState({ pageUrl: itemPageUrl }, '', itemPageUrl);
          }
          isWaitingForAjaxResults = false;
        });
      }
    });
  },

  _destroy() {
    this.element.off('scroll', this.clickListener);
  },

});
