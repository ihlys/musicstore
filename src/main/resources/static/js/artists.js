(function( $ ) {

  $(document).ready(function() {

    (function() {

      const $artistsDiv = $(".artists"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/genres/1/artists?page=0&size=3"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($artistsDiv, nextPageUrl);
      
      $artistsDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      }, 75);

      $artistsDiv.onItems("click", function() {
        console.log("PRIVET");
      });

      $artistsDiv.fixFlexBoxLastRow();
      
    })();
  
  });
  
})(jQuery);