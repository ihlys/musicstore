function fixFlexBoxLastRow(divClass) {
	
	var flexedDiv  = $("div." + divClass);
	
	var flexSpaceAroundHelper = new Utils.FlexSpaceAroundHelper(flexedDiv);
	
	var sensor = new ResizeSensor(flexedDiv, function() {
		flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
	});
	
	flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
}