var Utils = {}

Utils.ajaxContentLoader = (function() {
    
  const _isPaged = Symbol('isPaged');
  var _contentUrl = Symbol('contentUrl');
  var _isWaitingForAjaxResults = Symbol('isWaitingForAjaxResults');
  
  class ajaxContentLoader {
    
    constructor( contentUrl, isPaged = false ) {
      this[_contentUrl] = contentUrl;
      this[_isPaged] = isPaged;
      this[_isWaitingForAjaxResults] = false;
    }
    
    loadContent(responseHandler) {
      if (!this[_isWaitingForAjaxResults] && this[_contentUrl] != null) {
        this[_isWaitingForAjaxResults] = true;
        let promise = fetchContent(this[_contentUrl]);
        promise
          .then((response) => {
            responseHandler(response);
            
            if (this[_isPaged]) {
              /*[+
              this[_contentUrl] = response.getResponseHeader...
              +]*/
              
              /*[- */
              this[_contentUrl] = null;
              /* -]*/
            }
            
            this[_isWaitingForAjaxResults] = false;
          })
          .catch(console.log);
      }
    }
  }
  
  function fetchContent(contentUrl) {
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
  
  return ajaxContentLoader;
}());