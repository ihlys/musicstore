var Utils = {}

Utils.FlexSpaceAroundHelper = function FlexSpaceAroundHelper(div) {
	
	var divChildren = div.children();
	var lastChildren = divChildren.last();
	var childrenCount = divChildren.length;
	var childrenMarginRight 	= parseInt(lastChildren.css("marginRight"));
	var childrenHorizontalSpace = parseInt(lastChildren.css("width")) + childrenMarginRight + 
									parseInt(lastChildren.css("marginLeft"));
	
	this.squeezeChildrenToLeftInLastRow = function() {
		
		var divWidth = div.width();
		var childrenInOneRow = Math.floor(divWidth / childrenHorizontalSpace);
		var missingChildrenInLastRow = childrenInOneRow - childrenCount % childrenInOneRow;
		
		var isRowFull = missingChildrenInLastRow < childrenInOneRow;
		var marginRestored = false;
		if (isRowFull) {
			var gapsExtraSpacePerChildren = (divWidth - childrenInOneRow * childrenHorizontalSpace) / childrenInOneRow;
			var freeSpaceLeft = missingChildrenInLastRow * (childrenHorizontalSpace + gapsExtraSpacePerChildren);
		
			lastChildren.css("marginRight", freeSpaceLeft + childrenMarginRight);
			
			console.log("Utils.FlexSpaceAroundHelper:\n" +
						"divWidth = " + divWidth + ";\n" +
						"childrenHorizontalSpace = " + childrenHorizontalSpace + ";\n" +
						"childrenInOneRow = " + childrenInOneRow + ";\n" +
						"gapsExtraSpacePerChildren = " + gapsExtraSpacePerChildren + ";\n" +
						"missingChildrenInLastRow = " + missingChildrenInLastRow + ";\n" +
						"freeSpaceLeft = " + freeSpaceLeft);
		} else if (!marginRestored) {
			lastChildren.css("marginRight", childrenMarginRight);
			marginRestored = true;
		}
	}
}
