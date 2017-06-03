(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $soundtracksDiv = $(".soundtracks"),
        nextPageUrl = /*[[${nextPageUrl}]]*/ thJsUrlMapping.get("/soundtracks?page=0&size=3"),
        ajaxPagedContentLoader = new Utils.ajaxPagedContentLoader($soundtracksDiv, nextPageUrl);
      
      $soundtracksDiv.onScrolled(function() {
        ajaxPagedContentLoader.loadNextPage();
      }, 75);

      $soundtracksDiv.onItems("click", function() {
        console.log("PRIVET");
      });

      $soundtracksDiv.fixFlexBoxLastRow();
      
    })();
    
  });
  
})(jQuery);