/* global history */

import $ from 'jquery';
import 'jquery-ui';
import fetch from './ajax';

$.widget('ui.itemsWithContent', {

  options: {
    itemsSelector: null,
    prototypingMode: false,
  },

  _create() {
    if (!this.options.itemsSelector) throw Error('Required parameter itemsSelector must be specified.');

    const widget = this;
    let isWaitingForAjaxResults = false;
    this.clickListener = function () {
      if (!isWaitingForAjaxResults) {
        isWaitingForAjaxResults = true;
        const contentUrl = $(this).data('content-url');
        const promise = fetch(contentUrl, widget.options.prototypingMode);
        promise
          .then((request) => {
            widget.element.html(request.response);
            console.log("contentUrl = " + contentUrl);
            history.pushState({}, "", contentUrl);
            isWaitingForAjaxResults = false;
          })
          .catch(console.log);
      }
    };

    this.element.on('click', this.options.itemsSelector, this.clickListener);
  },

  _destroy() {
    this.element.off('scroll', this.clickListener);
  },

});
