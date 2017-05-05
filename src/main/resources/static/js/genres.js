
$(document).ready(function() {

	var genresDiv  = $("div.genres");
	
	var flexSpaceAroundHelper = new Utils.FlexSpaceAroundHelper(genresDiv);
	
	var sensor = new ResizeSensor(genresDiv, function() {
		flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
	});
	
	flexSpaceAroundHelper.squeezeDivsToLeftInLastRow();
});