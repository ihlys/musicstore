var	thRelativeRootPath = "../../..";
var thRootPath = thymol.location + thRelativeRootPath;
var	thPath = "templates";
var thResourcePath = "application/prototyping_resources"
var	thProtocol = "";
var thDebug = true;

// thRootPath already points to ~/src/main/resources directory
// and is succesfully works in other paths, however, "/resources"
// segment is erased for some reason when used in thMessagePath
// variable
var thMessagePath = thRootPath + "/resources/i18n";

console.log(thMessagePath);

var thTemplatesPath = thRootPath + "/" + thPath;
var thRequestResponsesPath = thRootPath + "/" + thResourcePath + "/responses";
console.log(thRequestResponsesPath);