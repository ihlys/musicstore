function fixFlexBoxLastRow(divClass) {
	
	var flexedDiv  = $("div." + divClass);
	
	var flexSpaceAroundHelper = new Utils.FlexSpaceAroundHelper(flexedDiv);
	
	var sensor = new ResizeSensor(flexedDiv, function() {
		flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
	});
	
	flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
}