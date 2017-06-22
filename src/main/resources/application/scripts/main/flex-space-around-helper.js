/* eslint-disable import/prefer-default-export */

/** @module flex-space-around-helper */

import dedent from 'dedent-js';

const $flexedDiv = Symbol('$flexedDiv');
const divChildren = Symbol('divChildren');
const lastChild = Symbol('lastChild');
const childrenCount = Symbol('childrenCount');
const childMarginRight = Symbol('childMarginRight');
const childHorizontalSpace = Symbol('childHorizontalSpace');

function init(instance) {
  instance[divChildren] = instance[$flexedDiv].children(':not(.resize-sensor)');
  instance[lastChild] = instance[divChildren].last();
  instance[childrenCount] = instance[divChildren].length;
  instance[childMarginRight] = parseInt(instance[lastChild].css('marginRight'));
  instance[childHorizontalSpace] = parseInt(instance[lastChild].css('width')) + instance[childMarginRight] +
              parseInt(instance[lastChild].css('marginLeft'));

  console.log(dedent`FlexSpaceAroundHelper(init):
    childrenCount = ${instance[childrenCount]};
    childMarginRight = ${instance[childMarginRight]};
    childHorizontalSpace = ${instance[childHorizontalSpace]};`);
}

function restoreMargin(instance) {
  instance[lastChild].css('marginRight', instance[childMarginRight]);
}

/**
 * <p>A helper class that can be used on div that has css styles:
 * <ul>
 *   <li>display: flex;</li>
 *   <li>flex-wrap: wrap;</li>
 *   <li>justify-content: space-around;</li>
 *   <li>align-content: flex-start.</li>
 * </ul>
 *
 * <p> It fixes items align in last row to move them to left edge of the container.
 * <p>For example, flexed elements that looks like follows:
 * <table>
 *   <tr>
 *     <td>[]</td> <td>[]</td> <td>[]</td>
 *   </tr>
 *   <tr>
 *     <td>  </td> <td>[]</td> <td>  </td>
 *   </tr>
 * </table>
 * <p>Will be like this:
 * <table>
 *   <tr>
 *     <td>[]</td> <td>[]</td> <td>[]</td>
 *   </tr>
 *   <tr>
 *     <td>[]</td> <td>  </td> <td>  </td>
 *   </tr>
 * </table>
 */
// can be export default, but then JsDoc can't properly document it
// to workaround, it is exported separately, see below
// it still marks class as inner member tough it is public
// see https://github.com/jsdoc3/jsdoc/issues/1272
class FlexSpaceAroundHelper {

  /**
   * Creates a FlexSpaceAroundHelper instance.
   *
   * @param {jQuery} _$flexedDiv - The div container that is flexed and must be fixed
   */
  constructor(_$flexedDiv) {
    this[$flexedDiv] = _$flexedDiv;
    init(this);
  }

  /**
   * Reconfigures itself like if it were newly added to container. It can be used
   * after new elements is added to container.
   */
  refresh() {
    restoreMargin(this);
    init(this);
    this.squeezeChildrenToLeftInLastRow();
  }

  /**
   * Moves all items in last row to left edge of the container.
   */
  squeezeChildrenToLeftInLastRow() {
    const divWidth = this[$flexedDiv].prop('clientWidth');
    const childrenInOneRow = Math.floor(divWidth / this[childHorizontalSpace]);
    const missingChildrenInLastRow = childrenInOneRow - (this[childrenCount] % childrenInOneRow);
    let marginRestored = false;

    if (missingChildrenInLastRow < childrenInOneRow) {
      const gapsExtraSpacePerChild = (divWidth - (childrenInOneRow * this[childHorizontalSpace]))
        / childrenInOneRow;
      const freeSpaceLeft = missingChildrenInLastRow * (this[childHorizontalSpace]
        + gapsExtraSpacePerChild);

      this[lastChild].css('marginRight', freeSpaceLeft + this[childMarginRight]);

      console.log(dedent`FlexSpaceAroundHelper(run):
        divWidth = ${divWidth};
        childrenInOneRow = ${childrenInOneRow};
        gapsExtraSpacePerChild = ${gapsExtraSpacePerChild};
        missingChildrenInLastRow = ${missingChildrenInLastRow};
        freeSpaceLeft = ${freeSpaceLeft}`);
    } else if (!marginRestored) {
      restoreMargin(this);
      marginRestored = true;
    }
  }
}

export { FlexSpaceAroundHelper };
