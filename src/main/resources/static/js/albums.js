(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $albumsDiv = $(".albums");
      
      $albumsDiv.gridCollection();
      
      Views.loadDataOnScrolled($albumsDiv, 75, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/genres/1/artists/1/albums?page=0"));

      Views.onItemClickLoadContent($albumsDiv, "> div");
      
    })();

  });
  
})(jQuery);