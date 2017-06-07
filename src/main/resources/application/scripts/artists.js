import './_music-content-import.js';
import 'Styles/artists.css';


$(document).ready(function() {

  (function() {
    
    const $artistsDiv = $(".artists");
    
    $artistsDiv.gridCollection();
    
    Views.loadDataOnScrolled($artistsDiv, 25, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/genres/1/artists?page=0"));

    Views.onItemClickLoadContent($artistsDiv, "> div");
    
  })();

});