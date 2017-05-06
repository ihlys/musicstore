var Utils = {}

Utils.FlexSpaceAroundHelper = function FlexSpaceAroundHelper(div) {
	
	var divChildren = div.children();
	var lastDiv = divChildren.last();
	var divsCount = divChildren.length;
	var divMarginRight 		= parseInt(lastDiv.css("marginRight"));
	var divHorizontalSpace 	= parseInt(lastDiv.css("width")) + divMarginRight + 
									parseInt(lastDiv.css("marginLeft"));
	
	console.log(divMarginRight);
	
	this.squeezeDivsToLeftInLastRow = function() {
		
		// simple long form
		
		var divWidth = div.width();
		var divsInOneRow = Math.floor(divWidth / divHorizontalSpace);
		var missingDivsInLastRow = divsInOneRow - divsCount % divsInOneRow;
		if (missingDivsInLastRow < divsInOneRow) {
			var gapsExtraSpacePerDiv = (divWidth - divsInOneRow * divHorizontalSpace) / divsInOneRow;
			var freeSpaceLeft = missingDivsInLastRow * (divHorizontalSpace + gapsExtraSpacePerDiv);
		
			lastDiv.css("margin-right", freeSpaceLeft + divMarginRight);
		}
		
		
		console.log("divWidth = " + divWidth);
		console.log("divHorizontalSpace = " + divHorizontalSpace);
		console.log("divsInOneRow = " + divsInOneRow);
		console.log("gapsExtraSpacePerDiv = " + gapsExtraSpacePerDiv);
		console.log("missingDivsInLastRow = " + missingDivsInLastRow);
		console.log("freeSpaceLeft = " + freeSpaceLeft);
		

	}

}
