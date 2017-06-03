(function ( $ ) {
  
  $.fn.fixFlexBoxLastRow = function() {
    
    return this.each(function() {
      const $flexedDiv = $(this);
      const flexSpaceAroundHelper = new FlexSpaceAroundHelper($flexedDiv);
      
      const observer = new MutationObserver(function(mutations) {
        if (mutations.some(mutation => mutation.type === 'childList')) {
          flexSpaceAroundHelper.refresh();
        }
      });
      
      const flexedDiv = $(this)[0];
      observer.observe(flexedDiv, { childList: true });
      
      const sensor = new ResizeSensor(flexedDiv, function() {
          flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
      });
      
      flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    });
  }
  
  
  var FlexSpaceAroundHelper = (function() {
    
    const _$flexedDiv = Symbol('$flexedDiv');
    var _divChildren = Symbol('divChildren');
    var _lastChild = Symbol('lastChild');
    var _childrenCount = Symbol('childrenCount');
    var _childMarginRight = Symbol('childMarginRight');
    var _childHorizontalSpace = Symbol('childHorizontalSpace');
    
    function init(instance) {
      instance[_divChildren] = instance[_$flexedDiv].children(":not(.resize-sensor)");
      instance[_lastChild] = instance[_divChildren].last();
      instance[_childrenCount] = instance[_divChildren].length;
      instance[_childMarginRight] = parseInt(instance[_lastChild].css("marginRight"));
      instance[_childHorizontalSpace] = parseInt(instance[_lastChild].css("width")) + instance[_childMarginRight] +
                  parseInt(instance[_lastChild].css("marginLeft"));
      
      console.log(`FlexSpaceAroundHelper(init):\n` +
                  `childrenCount = ${instance[_childrenCount]};\n` +
                  `childMarginRight = ${instance[_childMarginRight]};\n` +
                  `childHorizontalSpace = ${instance[_childHorizontalSpace]};\n`);
    }
    
    function restoreMargin(instance) {
      instance[_lastChild].css("marginRight", instance[_childMarginRight]);
    }
    
    class FlexSpaceAroundHelper {
      
      constructor( $flexedDiv ) {
        this[_$flexedDiv] = $flexedDiv;
        init(this);
      }
     
      refresh() {
        restoreMargin(this);
        init(this);
        this.squeezeChildrenToLeftInLastRow();
      }
      
      squeezeChildrenToLeftInLastRow() {
        let divWidth = this[_$flexedDiv].prop("clientWidth"),
          childrenInOneRow = Math.floor(divWidth / this[_childHorizontalSpace]),
          missingChildrenInLastRow = childrenInOneRow - this[_childrenCount] % childrenInOneRow,
          marginRestored = false;
      
        if ( missingChildrenInLastRow < childrenInOneRow ) {
          let gapsExtraSpacePerChild = (divWidth - childrenInOneRow * this[_childHorizontalSpace]) / childrenInOneRow,
            freeSpaceLeft = missingChildrenInLastRow * (this[_childHorizontalSpace] + gapsExtraSpacePerChild);
        
          this[_lastChild].css("marginRight", freeSpaceLeft + this[_childMarginRight]);
          
          console.log(`FlexSpaceAroundHelper(run):\n` +
                      `divWidth = ${divWidth};\n` +
                      `childrenInOneRow = ${childrenInOneRow};\n` +
                      `gapsExtraSpacePerChild = ${gapsExtraSpacePerChild};\n` +
                      `missingChildrenInLastRow = ${missingChildrenInLastRow}\n` +
                      `freeSpaceLeft = ${freeSpaceLeft}`);
        } else if ( !marginRestored ) {
          restoreMargin(this);
          marginRestored = true;
        }
      }
    }
    
    return FlexSpaceAroundHelper;
  }());
      
}( jQuery ));