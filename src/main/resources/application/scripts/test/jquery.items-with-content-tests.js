import 'babel-polyfill';
import 'Main/jquery.items-with-content.js';

describe('Items with content widget tests.', function() {
 
  beforeEach(function() {
    this.xhr = sinon.useFakeXMLHttpRequest();
 
    this.requests = [];
    this.xhr.onCreate = function(xhr) {
      this.requests.push(xhr);
    }.bind(this);
  });

  afterEach(function() {
    this.xhr.restore();
  });
 
  it('Must load item\'s own content on click.', function(done) {
    const test = this;
    const $collectionDiv = $('#test-collection');
    const testItem = $collectionDiv.children()[0];
    $collectionDiv.itemsWithContent();
    
    $(testItem).click();

    const response = '<div id="items-content"><h1>Loaded items content</h1></div>';
    test.requests[0].respond(200, { 'Content-Type': 'text/html' }, response);
    setTimeout(function () {
      expect(test.requests[0].url).to.be.equal('/collection/1/item-content');
      expect($collectionDiv.find('#items-content')).to.have.lengthOf(1);
      done();
    }, 1);
  });
  
});