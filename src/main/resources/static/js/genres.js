(function( $ ) {

  $(document).ready(function() {

    (function() {
      
      const $genresDiv = $(".genres");
      
      $genresDiv.gridCollection();
      
      Views.loadDataOnScrolled($genresDiv, 65, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/genres?page=0"));

      Views.onItemClickLoadContent($genresDiv, "button");
      
    })();

  });
  
})(jQuery);