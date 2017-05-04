
$(document).ready(function() {

	var genresDiv  = $("div.genres");
	
	var genresDivChildren = genresDiv.children();
	var lastGenreDiv = genresDivChildren.last();
	var genreDivsCount = genresDivChildren.length;
	
	var genreDivMarginRight 	  = parseInt(lastGenreDiv.css("marginRight"));
	var genreDivHorizontalSpace   = parseInt(lastGenreDiv.css("width")) + 
									genreDivMarginRight + 
									parseInt(lastGenreDiv.css("marginLeft"));
	
	console.log("genreDivHorizontalSpace = " + genreDivHorizontalSpace);
	
	var squeezeGenreDivsToLeftInLastRowFunction = function squeezeGenreDivsToLeftInLastRow() {
		/*
		// representational long form
		var genresDivWidth = genresDiv.width();
		var genreDivsInOneRow = Math.floor(genresDivWidth / genreDivHorizontalSpace);
		var gapsExtraSpacePerGenreDiv = (genresDivWidth - genreDivsInOneRow * genreDivHorizontalSpace) / genreDivsInOneRow;
		var missingGenreDivsInLastRow = genreDivsInOneRow - genreDivsCount % genreDivsInOneRow;
		var freeSpaceLeft = missingGenreDivsInLastRow * genreDivHorizontalSpace + gapsExtraSpacePerGenreDiv;
		lastGenreDiv.css("margin-right", freeSpaceLeft + freeSpaceLeft;
		*/
		
		// short form
		var genresDivWidth = genresDiv.width();
		var genreDivsInOneRow = Math.floor(genresDivWidth / genreDivHorizontalSpace);
		lastGenreDiv.css("margin-right", (genreDivsInOneRow - genreDivsCount % genreDivsInOneRow) * genreDivHorizontalSpace 
				+ genreDivMarginRight + (genresDivWidth - genreDivsInOneRow * genreDivHorizontalSpace) / genreDivsInOneRow);
	}
	
	
	var sensor = new ResizeSensor(genresDiv, function() {
		squeezeGenreDivsToLeftInLastRowFunction();
	});
	
	squeezeGenreDivsToLeftInLastRowFunction();
});