import $ from 'jquery';
import 'jquery-ui';
import fetch from './ajax';

$.widget('ihordev.itemsWithContent', {

  options: {
    itemsSelector: '> div',
    prototypingMode: false,
  },

  _create() {
    const widget = this;
    let isWaitingForAjaxResults = false;
    this.clickListener = function () {
      if (!isWaitingForAjaxResults) {
        isWaitingForAjaxResults = true;
        const promise = fetch($(this).data('content-url'), widget.options.prototypingMode);
        promise
          .then((request) => {
            widget.element.html(request.response);
            history.pushState(request.url);
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
