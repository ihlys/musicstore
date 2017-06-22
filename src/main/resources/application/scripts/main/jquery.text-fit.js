import $ from 'jquery';
import 'jquery-ui';
import 'Node_modules/jquery-textfill/source/jquery.textfill';

/**
 * <p>Changes css styles of text element to fit it's container width. It uses
 * <a href="http://jquery-textfill.github.io/">jquery-textfill</a> plugin under
 * the hood, but besides font size it is also manipulates "<code>white-space</code>"
 * css style to configure line wrapping. It works like follows:
 * <ol>
 *   <li>It takes same <code>options</code> object that jquery-textfill supports;</li>
 *   <li>It initially sets "<code>white-space: nowrap</code>";</li>
 *   <li>It delegates text resizing operation to jquery-textfill plugin;</li>
 *   <li>If jquery-textfill plugin reports that text after resizing to minimal size
 *       (specified by <code>options</code> object) still doesn't fit container,
 *       than this widget sets "<code>white-space: normal</code>" css style, allowing
 *       wrapping lines on white spaces, and sets "<code>font-size</code>" css style
 *       to minimal value.</li>
 * </ol>
 * <p>This widget created because jquery-textfill plugin does resize after text is
 * wrapped by presence of "<code>white-space: normal</code>" css property (default
 * value).
 *
 * @function external:"jQuery.fn".textfit
 *
 * @param {object} options  the same <a href="https://github.com/jquery-textfill/jquery-textfill#options">options</a>
 *                          object as in <code>jquery-textfill</code> plugin
 *
 */
$.widget('ui.textfit', {

  options: {
    innerTag: 'span',
  },

  _create() {
    const opts = this.options;
    const $resizedElems = this.element.find(`${opts.innerTag}:visible:first`);
    $resizedElems.css('white-space', 'nowrap');
    this.originalFontSize = $resizedElems.css('font-size');

    const userFailCallback = opts.fail;
    const internalFailCallback = function (textfill) {
      $resizedElems.css('font-size', opts.minFontPixels);
      $resizedElems.css('white-space', 'normal');
      if (userFailCallback) {
        userFailCallback.call(textfill);
      }
    };
    opts.fail = internalFailCallback;

    this.element.textfill(opts);
  },

  _destroy() {
    this.element.css('font-size', this.originalFontSize);
  },

});
