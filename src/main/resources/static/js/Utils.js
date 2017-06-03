var Utils = {}

Utils.ajaxPagedContentLoader = (function() {
    
    const _$containerDiv = Symbol('$containerDiv');
    var _nextPageUrl = Symbol('nextPageUrl');
    var _isWaitingForAjaxResults = Symbol('isWaitingForAjaxResults');
    
    class AjaxPagedContentLoader {
      
      constructor( $containerDiv, nextPageUrl ) {
        this[_$containerDiv] = $containerDiv;
        this[_nextPageUrl] = nextPageUrl;
        this[_isWaitingForAjaxResults] = false;
      }
     
      loadNextPage() {
        if (!this[_isWaitingForAjaxResults] && this[_nextPageUrl] != null) {
          this[_isWaitingForAjaxResults] = true;
          let promise = fetchMusicContentNextPage(this[_nextPageUrl]);
          promise
            .then((response) => {
              this[_$containerDiv].append(response);
              
              /*[+
              this[_nextPageUrl] = response.getResponseHeader("Content-Type");
              +]*/
              
              /*[- */
              this[_nextPageUrl] = null;
              /* -]*/
              
              this[_isWaitingForAjaxResults] = false;
            })
            .catch(console.log);
        }
      }
    }
    
    function fetchMusicContentNextPage(contentUrl) {
      return promise = new Promise(function(resolve, reject) {
        
        const xhr = new XMLHttpRequest(resolve, reject);
        xhr.open('GET', contentUrl, true);
        
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
    
    return AjaxPagedContentLoader;
  }());