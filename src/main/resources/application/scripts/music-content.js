import './_music-content-import.js';

$(document).ready(function() {

  (function() { // init music info details button expand/collapse 
    const musicInfoDetails = $("div.music-info__details");
    const musicInfoExpandBtn = $("div.music-info__expand-btn-wrapper");
    
    $("button.music-info__expand-btn").click(function() {
      musicInfoDetails.slideToggle("slow");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-after");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-before");
    });
  })();
});

