/*[- */
$(window).on("load", function() {
/* -]*/
/*[+
$(document).ready(function() {
+]*/

	$(".flex-collection").fixFlexBoxLastRow();
  
  $(".flex-collection").append("	<div class='genre flex-collection__item'>" +
		"<div class='container-with-background'>" +
			"<div class='container-with-background__background-wrapper'>"+
				"<img th:src='@{${genre.image}}'>"+
			"</div>"+
			"<div class='container-with-background__foreground-wrapper genre__content'>"+
				"<div class='container-with-background'>"+
					"<div class='container-with-background__background-wrapper genre__title-wrapper'>"+
						"<span th:text='${genre.name}'></span>"+
					"</div>"+
					"<div class='container-with-background__foreground-wrapper'>"+
						"<a class='show-link show-link--genre-songs' 	 href='#'>Показать</a>"+
						"<a class='show-link show-link--genre-subgenres'  href='#'>Выбрать поджанры</a>"+
					"</div>"+
				"</div>"+
			"</div>"+
		"</div>"+
	"</div>");

});