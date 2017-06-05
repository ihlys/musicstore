(function( $ ) {

  $(document).ready(function() {
    
    (function() {

      const $songsDiv = $(".songs");
    
      Views.loadDataOnScrolled($songsDiv, 0, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/genres/1/songs?page=0"));
      
    }());
    
  });
  
})(jQuery);