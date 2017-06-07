import './_music-content-import.js';
import 'Styles/soundtracks.css';

$(document).ready(function() {
  
  (function() {
    
    const $soundtracksDiv = $(".soundtracks");
    
    $soundtracksDiv.gridCollection();
    
    Views.loadDataOnScrolled($soundtracksDiv, 75, /*[[${nextPageUrl}]]*/ thJsUrlMappings.get("/soundtracks?page=0"));

    Views.onItemClickLoadContent($soundtracksDiv, "> div");
    
  })();
  
});