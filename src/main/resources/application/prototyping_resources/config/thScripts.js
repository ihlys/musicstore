const rootPath = thymol.location + thRelativeRootPath + "/";
const scriptsPaths = rootPath + thResourcePath + "/config/thScripts/";

thymol.ThUtils.loadScript(scriptsPaths + "Requests.js");
thymol.ThUtils.loadScript(scriptsPaths + "RequestObject.js");