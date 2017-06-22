/* eslint no-underscore-dangle: 0 */
/* eslint no-undef: 0 */
/* eslint no-unused-vars: 0 */

import $ from 'jquery';
import 'jquery-ui';
import 'Styles/grid-collection.css';
import { ResizeSensor, ElementQueries } from 'css-element-queries';
import { FlexSpaceAroundHelper } from './flex-space-around-helper';

/**
 * <p>Makes container element with items on which it is applied look like a grid.
 * It adds it's own css classes with styles to element and it's items:
 *
 * <pre><code>
 * .grid-collection {
 *   display: flex;
 *   flex-wrap: wrap;
 *   justify-content: space-around;
 *   align-content: flex-start;
 * }
 *
 * .grid-collection__item {
 *   margin: 10px;
 *   overflow: hidden;
 *   border-radius: 8px;
 *   box-shadow: 0 0 8px rgb(178, 178, 178);
 * }
 * </pre></code>
 *
 * <p>It also uses {@link module:flex-space-around-helper~FlexSpaceAroundHelper}
 * internally to fix align of items in last row.
 *
 * @function external:"jQuery.fn".gridCollection
 */
$.widget('ui.gridCollection', {

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
    this.element.children('div:not(.resize-sensor)').each(function () {
      $(this).addClass('grid-collection__item');
    });
  },

});
