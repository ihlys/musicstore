import 'babel-polyfill';
import $ from "jquery";
import FlexSpaceAroundHelper from 'Main/flex-space-around-helper.js';

describe("FlexSpaceAroundHelper tests", function() {
 
  it("Last row elements must be aligned to left correctly.", function() {
    const $flexedDiv = $("#test-collection1");
    
    const flexSpaceAroundHelper = new FlexSpaceAroundHelper($flexedDiv);
    flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    
    const lastChild = $flexedDiv.children().last();
    
    expect(lastChild.css("marginRight")).to.equal("200px");
  });
  
  it("Last row elements with margin must be aligned to left correctly.", function() {
    const $flexedDiv = $("#test-collection2");
    
    const flexSpaceAroundHelper = new FlexSpaceAroundHelper($flexedDiv);
    flexSpaceAroundHelper.squeezeChildrenToLeftInLastRow();
    
    const lastChild = $flexedDiv.children().last();
    
    expect(lastChild.css("marginRight")).to.equal("320px");
  });
});