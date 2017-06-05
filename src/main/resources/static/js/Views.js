var Views = {}

Views.loadDataOnScrolled = function($targetDiv, scrollPadding, nextPageUrl) {
  const ajaxPagedContentLoader = new Utils.ajaxContentLoader(nextPageUrl, true);
  
  $targetDiv.onScrolled(function() {
    ajaxPagedContentLoader.loadContent((response) => $targetDiv.append(response));
  }, scrollPadding);
}

Views.onItemClickLoadContent = function($targetDiv, selector) {
  $targetDiv.on("click", selector, function(event) {
    const ajaxContentLoader = new Utils.ajaxContentLoader($(this).data("content-url"));
    ajaxContentLoader.loadContent((response) => {
      $targetDiv.html(response)
    })
  });
}