import 'babel-polyfill';
import 'mutationobserver-shim';
import 'Main/jquery.grid-collection.js';

describe("Grid collection widget tests", function() {
 
  it("Must add css styles for grid collection looking correctly.", function() {
    const $flexedDiv = $("#test-collection");
  
    $flexedDiv.gridCollection();
  
    const children = $flexedDiv.children(":not(.resize-sensor)");
    const lastChild = children.last();
    
    expect(lastChild.css("marginRight")).to.equal("310px");
    expect($flexedDiv.hasClass("grid-collection")).to.be.true;
    children.each(function() {
      expect($(this).hasClass("grid-collection__item")).to.be.true;
    });
  });
  
});