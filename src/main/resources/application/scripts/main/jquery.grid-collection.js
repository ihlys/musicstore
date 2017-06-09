/* eslint no-underscore-dangle: 0 */
/* eslint no-undef: 0 */
/* eslint no-unused-vars: 0 */

import $ from 'jquery';
import 'jquery-ui';
import 'Styles/grid-collection.css';
import { ResizeSensor, ElementQueries } from 'css-element-queries';
import FlexSpaceAroundHelper from './flex-space-around-helper';

$.widget('ihordev.gridCollection', {

  _create() {
    this._addStyles();

    const flexSpaceAroundHelper = new FlexSpaceAroundHelper(this.element);

    const $this = this;
    const observer = new MutationObserver(function (mutations) {
      if (mutations.some(mutation => mutation.type === 'childList')) {
        $this._addStyles();
        flexSpaceAroundHelper.refresh();
      }
    });

    observer.observe(this.element[0], { childList: true });

    const sensor = new ResizeSensor(this.element[0], function () {
      flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    });

    flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
  },

  _destroy() {
    const observer = new MutationObserver(function () {});
    observer.observe(this.element[0], { childList: true });
    $('resize-sensor').remove();
  },

  _addStyles() {
    this.element.addClass('grid-collection');
    this.element.children('div').each(function () {
      $(this).addClass('grid-collection__item');
    });
  },

});
