(function( $ ) {

  $(document).ready(function() {

    (function() {

      const $genresDiv = $(".genres"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/genres?page=0&size=3"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($genresDiv, nextPageUrl);
      
      $genresDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      }, 75);

      $genresDiv.onItems("click", function() {
        console.log("PRIVET");
      });

      $genresDiv.fixFlexBoxLastRow();
      
    })();

  });
  
})(jQuery);