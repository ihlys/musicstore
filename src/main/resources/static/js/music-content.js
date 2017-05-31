(function( $ ) {

  $(document).ready(function() {

    initMusicInfoDetailsButton();
    
  });
  
  function initMusicInfoDetailsButton() {
    
    var musicInfoDetails = $("div.music-info__details");
    var musicInfoExpandBtn = $("div.music-info__expand-btn-wrapper");
    
    console.log(musicInfoDetails);
    
    $("button.music-info__expand-btn").click(function() {
      musicInfoDetails.slideToggle("slow");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-after");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-before");
    });
  }
  
})(jQuery);

