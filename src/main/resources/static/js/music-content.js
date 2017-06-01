(function( $ ) {

  $(document).ready(function() {
  
    initMusicInfoDetailsButton();
   
    initFlexCollection();
    
  });
  
  function initFlexCollection() {
    let nextPageUrl = /*[[${??????}]]*/ thJsUrlMapping.get("/genres/artists?page=0&size=3");
    const $flexCollection = $(".flex-collection");
    
    console.log($flexCollection);
    
    let isWaitingForAjaxResults = false;
    $flexCollection.scroll(function() {
      if($flexCollection.scrollTop() == $flexCollection.prop('scrollHeight') - $flexCollection.height() && !isWaitingForAjaxResults && nextPageUrl != null) {
        isWaitingForAjaxResults = true;
        let promise = fetchMusicContentNextPage(nextPageUrl);
        promise
          .then((response) => {
            $flexCollection.append(response);
            
            /*[+
            nextPageUrl = response.getResponseHeader("Content-Type");
            +]*/
            
            /*[- */
            nextPageUrl = null;
            /* -]*/
            
            isWaitingForAjaxResults = false;
          })
          .catch(console.log);
      }
    });    
  }
  
  function fetchMusicContentNextPage(nextPageUrl) {
    return promise = new Promise(function(resolve, reject) {
      
      const xhr = new XMLHttpRequest(resolve, reject);
      xhr.open('GET', nextPageUrl, true);
      
      xhr.onload = function() {
        
        /*[+
        if (this.status == 200) {
          resolve(this.response);
        } else {
          var error = new Error(this.statusText);
          error.code = this.status;
          reject(error);
        }
        +]*/
        
        /*[- */
        resolve(this.response);
        /* -]*/
        
      };

      xhr.onerror = function() {
        reject(new Error("Network Error"));
      };

      xhr.send();
    });
  }
  
  function initMusicInfoDetailsButton() {
    
    const musicInfoDetails = $("div.music-info__details");
    const musicInfoExpandBtn = $("div.music-info__expand-btn-wrapper");
    
    $("button.music-info__expand-btn").click(function() {
      musicInfoDetails.slideToggle("slow");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-after");
      musicInfoExpandBtn.toggleClass("music-info__expand-btn-wrapper--show-before");
    });
  } 
  
})(jQuery);

