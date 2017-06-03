(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $songsDiv = $(".songs"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/genres/1/songs?page=0&size=4"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($songsDiv, nextPageUrl);
      
      $songsDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      });
      
    })();
    
  });
  
})(jQuery);