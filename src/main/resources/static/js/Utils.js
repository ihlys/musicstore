var Utils = {}

Utils.FlexSpaceAroundHelper = function FlexSpaceAroundHelper(div) {
	
	var divChildren = div.children();
	var lastDiv = divChildren.last();
	var divsCount = divChildren.length;
	var divMarginRight 		= parseInt(lastDiv.css("marginRight"));
	var divHorizontalSpace 	= parseInt(lastDiv.css("width")) + divMarginRight + 
									parseInt(lastDiv.css("marginLeft"));
	
	this.squeezeDivsToLeftInLastRow = function() {
		
		var divWidth = div.width();
		var divsInOneRow = Math.floor(divWidth / divHorizontalSpace);
		var missingDivsInLastRow = divsInOneRow - divsCount % divsInOneRow;
		if (missingDivsInLastRow < divsInOneRow) {
			var gapsExtraSpacePerDiv = (divWidth - divsInOneRow * divHorizontalSpace) / divsInOneRow;
			var freeSpaceLeft = missingDivsInLastRow * (divHorizontalSpace + gapsExtraSpacePerDiv);
		
			lastDiv.css("margin-right", freeSpaceLeft + divMarginRight);
		}
		
		console.log("Utils.FlexSpaceAroundHelper: divWidth = " + divWidth + 
												"; divHorizontalSpace = " + divHorizontalSpace +
												"; divsInOneRow = " + divsInOneRow +
												"; gapsExtraSpacePerDiv = " + gapsExtraSpacePerDiv +
												"; missingDivsInLastRow = " + missingDivsInLastRow +
												"; freeSpaceLeft = " + freeSpaceLeft);
	}
	
}
