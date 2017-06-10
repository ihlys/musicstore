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

export default class FlexSpaceAroundHelper {

  constructor(_$flexedDiv) {
    this[$flexedDiv] = _$flexedDiv;
    init(this);
  }

  refresh() {
    restoreMargin(this);
    init(this);
    this.squeezeChildrenToLeftInLastRow();
  }

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
