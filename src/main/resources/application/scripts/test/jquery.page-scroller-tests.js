import 'babel-polyfill';
import 'Main/jquery.page-scroller.js';

describe('Page scroller widget tests.', function() {
 
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
 
  it('Must load next page of data on scrolled to bottom.', function(done) {
    const test = this;
    const $scrollerDiv = $('#test-container');
    $scrollerDiv.pageScroller({nextPageUrl: '/content?page=1', scrollPadding: 0});
    $scrollerDiv
      .animate({ scrollTop: 220 }, { duration: 800, complete: function () {
        const response = '<h1 id="loaded-content1">Loaded page one of content.</h1>';
        test.requests[0].respond(200, { 'Content-Type': 'text/html', 'Link': '</content?page=2>; rel="next"' }, response);
        setTimeout(function () {
          expect(test.requests[0].url).to.be.equal('/content?page=1');
          expect($scrollerDiv.find('#loaded-content1')).to.have.lengthOf(1);      
        }, 1);
      }})
      .animate({ scrollTop: 300 }, { duration: 800, complete: function () {
          const response = '<h1 id="loaded-content2">Loaded page two of content.</h1>';
          test.requests[1].respond(200, { 'Content-Type': 'text/html', 'Link': '</content?page=3>; rel="next"' }, response);
          setTimeout(function () {
            expect(test.requests[1].url).to.be.equal('/content?page=2');
            expect($scrollerDiv.find('#loaded-content2')).to.have.lengthOf(1);   
          }, 1);
          done();
        }
      });
  });
  
});