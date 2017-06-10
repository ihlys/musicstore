var thMappings;
var thJsUrlMappings;

(function () {
  
  const rootPath = thymol.location + thRelativeRootPath;
  const templatesPath = rootPath + "/" + thPath;
  const requestResponsesPath = rootPath + "/" + thResourcePath + "/responses";
  
  thMappings = [
    ["/genres/1/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/2/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/3/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/4/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/5/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/6/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/7/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/8/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/9/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/10/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/11/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/12/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/13/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/14/artists", requestResponsesPath + "/generic-content-ajax-response.html"],
    
    ["/genres/1/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/2/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/3/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/4/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/5/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/6/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/7/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/8/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/9/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/10/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/11/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/12/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/13/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    ["/genres/14/subgenres", requestResponsesPath + "/genre-subgenres-ajax-response.html"],
    
    ["/genres/1/artists/1/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/2/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/3/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/4/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/5/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/6/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/7/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/8/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/9/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/10/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/11/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/12/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/13/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/14/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/15/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/16/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/17/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/18/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/19/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/20/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/21/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/22/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/23/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/24/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/25/albums", requestResponsesPath + "/generic-content-ajax-response.html"],
    
    ["/genres/1/artists/1/albums/1/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/2/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/3/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/4/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/5/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/6/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/genres/1/artists/1/albums/7/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    
    ["/soundtracks/1/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/2/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/3/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/4/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/5/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/6/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/7/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/soundtracks/8/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    
    ["/thematic-compilations/1/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/2/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/3/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/4/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/5/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/6/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/7/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/8/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/9/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/10/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/11/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/12/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/13/songs", requestResponsesPath + "/generic-content-ajax-response.html"],
    ["/thematic-compilations/14/songs", requestResponsesPath + "/generic-content-ajax-response.html"]
  ];
  
  thJsUrlMappings = new Map([
    ["/genres", requestResponsesPath + "/genres.html"],
    ["/genres/1/artists", requestResponsesPath + "/artists.html"],
    ["/genres/1/artists/1/albums", requestResponsesPath + "/albums.html"],
    ["/genres/1/songs", requestResponsesPath + "/songs.html"],
    ["/soundtracks", requestResponsesPath + "/soundtracks.html"],
    ["/thematic-compilations", requestResponsesPath + "/thematic-compilations.html"]
  ]);
  
})();