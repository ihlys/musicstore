import dedent from 'dedent-js';
import 'babel-polyfill';
import * as exports from './application';
import 'Main/jquery.items-with-content.js';

describe('Items with content widget tests.', function() {
  
  let loadPageSpy;
  
  beforeEach(function() {
    history.pushState = function () {};
    loadPageSpy = sinon.spy(exports, "loadPage");
  });

  it('Must get item\'s url from data attribute and call application.loadPage() correctly', function() {
    const $collectionDiv = $('#test-collection');
    const testItemOne = $collectionDiv.find('#test-item-one');
    const testItemTwo = $collectionDiv.find('#test-item-two');
    
    $collectionDiv.itemsWithContent({
      itemSelector: '#test-item-two, #test-item-one'
    });

    testItemOne.click();
    testItemTwo.click();

    expect(loadPageSpy.calledTwice).to.be.true;
    expect(loadPageSpy.firstCall.args[0]).to.equal('/collection/1/item-one-content');
    expect(loadPageSpy.secondCall.args[0]).to.equal('/collection/1/item-two-content');
  });

});