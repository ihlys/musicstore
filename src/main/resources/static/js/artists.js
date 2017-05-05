
$(document).ready(function() {

	var artistsDiv  = $("div.artists");
	
	var flexSpaceAroundHelper = new Utils.FlexSpaceAroundHelper(artistsDiv);
	
	var sensor = new ResizeSensor(artistsDiv, function() {
		flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
	});
	
	flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
});