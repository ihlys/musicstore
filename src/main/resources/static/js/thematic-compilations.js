(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $thematicCompilationsDiv = $(".thematic-compilations");
      
      $thematicCompilationsDiv.gridCollection();
      
      Views.loadDataOnScrolled($thematicCompilationsDiv, 65, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/thematic-compilations?page=0"));

      Views.onItemClickLoadContent($thematicCompilationsDiv, "button");
      
    })();
    
  });
  
})(jQuery);