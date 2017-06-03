(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $thematicCompilationsDiv = $(".thematic-compilations"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/thematic-compilations?page=0&size=3"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($thematicCompilationsDiv, nextPageUrl);
      
      $thematicCompilationsDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      }, 75);

      $thematicCompilationsDiv.onItems("click", function() {
        console.log("PRIVET");
      });

      $thematicCompilationsDiv.fixFlexBoxLastRow();
      
    })();
    
  });
  
})(jQuery);