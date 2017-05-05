
$(document).ready(function() {

	var thematicCollectionsDiv  = $("div.thematic-collections");
	
	var flexSpaceAroundHelper = new Utils.FlexSpaceAroundHelper(thematicCollectionsDiv);
	
	var sensor = new ResizeSensor(thematicCollectionsDiv, function() {
		flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
	});
	
	flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
});