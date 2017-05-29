(function ( $ ) {
  
  $.fn.fixFlexBoxLastRow = function() {
    
    return this.each(() => {
      let flexedDiv = $(this);
      let flexSpaceAroundHelper = new FlexSpaceAroundHelper(flexedDiv);
      
      let sensor = new ResizeSensor(flexedDiv, function() {
        flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
      });
      
      flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    });
  }
  
  var FlexSpaceAroundHelper = function FlexSpaceAroundHelper( div ) {
    let divChildren = div.children(),
      lastChildren = divChildren.last(),
      childrenCount = divChildren.length,
      childrenMarginRight = parseInt(lastChildren.css("marginRight")),
      childrenHorizontalSpace = parseInt(lastChildren.css("width")) + childrenMarginRight + parseInt(lastChildren.css("marginLeft"));
    
    this.squeezeChildrenToLeftInLastRow = function() {
      
      let divWidth = div.width();
        childrenInOneRow = Math.floor(divWidth / childrenHorizontalSpace);
        missingChildrenInLastRow = childrenInOneRow - childrenCount % childrenInOneRow;
        isRowFull = missingChildrenInLastRow < childrenInOneRow;
        marginRestored = false;
        
      if ( isRowFull ) {
        let gapsExtraSpacePerChildren = (divWidth - childrenInOneRow * childrenHorizontalSpace) / childrenInOneRow,
          freeSpaceLeft = missingChildrenInLastRow * (childrenHorizontalSpace + gapsExtraSpacePerChildren);
      
        lastChildren.css("marginRight", freeSpaceLeft + childrenMarginRight);
        
        console.log(`FlexSpaceAroundHelper:\n` +
                    `divWidth = ${divWidth};\n` +
                    `childrenHorizontalSpace = ${childrenHorizontalSpace};\n` +
                    `childrenInOneRow = ${childrenInOneRow};\n` +
                    `gapsExtraSpacePerChildren = ${gapsExtraSpacePerChildren};\n` +
                    `missingChildrenInLastRow = ${missingChildrenInLastRow}\n` +
                    `freeSpaceLeft = ${freeSpaceLeft}`);
      } else if ( !marginRestored ) {
        lastChildren.css("marginRight", childrenMarginRight);
        marginRestored = true;
      }
    }
  }
  
}( jQuery ));