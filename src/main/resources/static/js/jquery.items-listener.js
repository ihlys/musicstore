(function ( $ ) {
  
  $.fn.onItems = function(event, listener) {
    
    function internalListener(e) {
      if (e.target !== e.currentTarget) {
        listener(e.target.id, e);
      }
      e.stopPropagation();
    }
    
    return this.each(function() {
      $(this).on(event, internalListener);
    });
  }
  
}( jQuery ));