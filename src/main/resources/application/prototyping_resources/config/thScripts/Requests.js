var Requests = {}

Requests.setRequestObject = function(requestObject) {
  var requestDialect = {
    objects : [
      requestObject
    ]
  };

  thymol.addDialect(requestDialect);
}