var thJsUrlMapping;

(function() {
  
  const requestResponsesPath = "static/Static%20prototyping%20resources/responses";
  const rootPath = thymol.location + thRelativeRootPath;
  
  thJsUrlMapping = new Map([
    ["/genres/artists?page=0&size=3", rootPath + "/" + requestResponsesPath + "/artistsPage.html"]
  ]);
  
})();