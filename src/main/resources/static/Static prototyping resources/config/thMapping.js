var thJsUrlMapping;

(function() {
  
  const requestResponsesPath = "static/Static%20prototyping%20resources/responses";
  const rootPath = thymol.location + thRelativeRootPath;
  
  thJsUrlMapping = new Map([
    ["/genres?page=0&size=3", rootPath + "/" + requestResponsesPath + "/genres.html"],
    ["/genres/1/artists?page=0&size=3", rootPath + "/" + requestResponsesPath + "/artists.html"],
    ["/genres/1/artists/1/albums?page=0&size=3", rootPath + "/" + requestResponsesPath + "/albums.html"],
    ["/genres/1/songs?page=0&size=4", rootPath + "/" + requestResponsesPath + "/songs.html"],
    ["/soundtracks?page=0&size=3", rootPath + "/" + requestResponsesPath + "/soundtracks.html"],
    ["/thematic-compilations?page=0&size=3", rootPath + "/" + requestResponsesPath + "/thematic-compilations.html"]
  ]);
  
})();