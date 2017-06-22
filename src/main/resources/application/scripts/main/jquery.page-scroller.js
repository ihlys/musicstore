/* eslint no-underscore-dangle: 0 */

import $ from 'jquery';
import 'jquery-ui';
import { fetch } from './ajax';

/**
 * <p>Loads new page of data asynchronously and appends it to end when
 * container element on which it is used is scrolled to bottom. When next
 * page of data is first requested it uses data attribute with name
 * <code>initial-next-page-url</code> from which it gets URL address for next page.
 * After first request, data attribute is removed by this widget and each
 * subsequent request will be given URL address taken by widget from
 * <code>Link</code> header of previous response.
 *
 * @function external:"jQuery.fn".pageScroller
 *
 * @param {object} options - the options object holding:
 *                 <ol>
 *                   <li><code>scrollPadding</code> - option to determine height
 *                       from bottom of the container from where widget will start
 *                       loading next page of data;</li>
 *                   <li><code>prototypingMode</code> - determines if prototyping
 *                       mode is enabled. If enabled then after first request
 *                       widget will not read <code>Link</code> header value for
 *                       next page url and will set it to null internally.
 *                 </ol>
 */
$.widget('ui.pageScroller', {

  options: {
    scrollPadding: 50,
    prototypingMode: false,
  },

  _create() {
    const widget = this;
    let nextPageUrl = widget.element.data('initial-next-page-url');
    widget.element.removeData('initial-next-page-url').removeAttr('initial-next-page-url');

    let isWaitingForAjaxResults = false;
    this.scrollListener = function () {
      if ($(this).scrollTop() >= $(this).prop('scrollHeight') - $(this).height() - widget.options.scrollPadding) {
        if (!isWaitingForAjaxResults && nextPageUrl) {
          isWaitingForAjaxResults = true;
          const promise = fetch(nextPageUrl, widget.options.prototypingMode);
          promise
            .then((request) => {
              $(this).append(request.response);
              if (!widget.options.prototypingMode) {
                const header = request.getResponseHeader('Link');
                if (header != null) {
                  nextPageUrl = widget._getNextUrlFromHeader(header);
                } else {
                  nextPageUrl = null;
                }
              } else {
                nextPageUrl = null;
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
