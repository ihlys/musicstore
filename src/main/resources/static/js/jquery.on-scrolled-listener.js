(function ( $ ) {
  
  $.fn.onScrolled = function(listener, padding = 50) {
    
    return this.each(function() {
      const $collection = $(this);
      $collection.scroll(function() {
        if ($collection.scrollTop() >= $collection.prop('scrollHeight') - $collection.height() - padding) {
          listener();
        }
      });
    });
  }
  
}( jQuery ));