(function ( $ ) {
  
  $.fn.fixFlexBoxLastRow = function() {
    
    return this.each(function() {
      let $flexedDiv = $(this);
      let flexSpaceAroundHelper = new FlexSpaceAroundHelper($flexedDiv);
      
      let observer = new MutationObserver(function(mutations) {
        if (mutations.some(mutation => mutation.type === 'childList')) {
          flexSpaceAroundHelper.refresh();
        }
      });
      
      let flexedDiv = $(this)[0];
      observer.observe(flexedDiv, { childList: true });
      
      let sensor = new ResizeSensor(flexedDiv, function() {
          flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
      });
      
      flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    });
  }
  
  
  var FlexSpaceAroundHelper = (function() {
    
    var flexedDiv = Symbol('flexedDiv');
    var divChildren = Symbol('divChildren');
    var lastChild = Symbol('lastChild');
    var childrenCount = Symbol('childrenCount');
    var childMarginRight = Symbol('childMarginRight');
    var childHorizontalSpace = Symbol('childHorizontalSpace');
    
    function init(instance) {
      instance[divChildren] = instance[flexedDiv].children(":not(.resize-sensor)");
      instance[lastChild] = instance[divChildren].last();
      instance[childrenCount] = instance[divChildren].length;
      instance[childMarginRight] = parseInt(instance[lastChild].css("marginRight"));
      instance[childHorizontalSpace] = parseInt(instance[lastChild].css("width")) + instance[childMarginRight] +
                  parseInt(instance[lastChild].css("marginLeft"));
      
      console.log(`FlexSpaceAroundHelper(init):\n` +
                  `childrenCount = ${instance[childrenCount]};\n` +
                  `childMarginRight = ${instance[childMarginRight]};\n` +
                  `childHorizontalSpace = ${instance[childHorizontalSpace]};\n`);
    }
    
    function restoreMargin(instance) {
      instance[lastChild].css("marginRight", instance[childMarginRight]);
    }
    
    class FlexSpaceAroundHelper {
      
      constructor( div ) {
        this[flexedDiv] = div;
        init(this);
      }
     
      refresh() {
        restoreMargin(this);
        init(this);
        this.squeezeChildrenToLeftInLastRow();
      }
      
      squeezeChildrenToLeftInLastRow() {
        let divWidth = this[flexedDiv].width(),
          childrenInOneRow = Math.floor(divWidth / this[childHorizontalSpace]),
          missingChildrenInLastRow = childrenInOneRow - this[childrenCount] % childrenInOneRow,
          marginRestored = false;
      
        if ( missingChildrenInLastRow < childrenInOneRow ) {
          let gapsExtraSpacePerChildren = (divWidth - childrenInOneRow * this[childHorizontalSpace]) / childrenInOneRow,
            freeSpaceLeft = missingChildrenInLastRow * (this[childHorizontalSpace] + gapsExtraSpacePerChildren);
        
          this[lastChild].css("marginRight", freeSpaceLeft + this[childMarginRight]);
          
          console.log(`FlexSpaceAroundHelper(run):\n` +
                      `divWidth = ${divWidth};\n` +
                      `childrenInOneRow = ${childrenInOneRow};\n` +
                      `gapsExtraSpacePerChildren = ${gapsExtraSpacePerChildren};\n` +
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