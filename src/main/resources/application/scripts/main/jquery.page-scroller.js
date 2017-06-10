/* eslint no-underscore-dangle: 0 */

import $ from 'jquery';
import 'jquery-ui';
import fetch from './ajax';

$.widget('ui.pageScroller', {

  options: {
    nextPageUrl: null,
    scrollPadding: 50,
    prototypingMode: false,
  },

  _create() {
    if (!this.options.nextPageUrl) throw Error('Required parameter nextPageUrl must be specified.');

    const widget = this;
    let isWaitingForAjaxResults = false;
    this.scrollListener = function () {
      if ($(this).scrollTop() >= $(this).prop('scrollHeight') - $(this).height() - widget.options.scrollPadding) {
        if (!isWaitingForAjaxResults && widget.options.nextPageUrl) {
          isWaitingForAjaxResults = true;
          const promise = fetch(widget.options.nextPageUrl, widget.options.prototypingMode);
          promise
            .then((request) => {
              $(this).append(request.response);
              if (!widget.options.prototypingMode) {
                widget.options.nextPageUrl = widget._getNextUrlFromHeader(request.getResponseHeader('Link'));
              } else {
                widget.options.nextPageUrl = null;
              }

              isWaitingForAjaxResults = false;
            })
            .catch(console.log);
        }
      }
    };

    this.element.scroll(this.scrollListener);
  },

  _destroy() {
    this.element.off('scroll', this.scrollListener);
  },

  _getNextUrlFromHeader(header) {
    return header.substring(header.lastIndexOf('<') + 1, header.lastIndexOf('>'));
  },
});
