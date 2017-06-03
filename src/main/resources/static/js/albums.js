(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $albumsDiv = $(".albums"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/genres/1/artists/1/albums?page=0&size=3"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($albumsDiv, nextPageUrl);
      
      $albumsDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      }, 75);

      $albumsDiv.onItems("click", function() {
        console.log("PRIVET");
      });

      $albumsDiv.fixFlexBoxLastRow();
      
    })();

  });
  
})(jQuery);