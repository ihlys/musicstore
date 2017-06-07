var RequestObject = function(requestURI) {
  
  return function() {

    var thExpressionObjectName = "#request";

    function getRequestURI() {
      return requestURI;
    }

    return {
      thExpressionObjectName : thExpressionObjectName,
      getRequestURI : getRequestURI
    };
  }();
}