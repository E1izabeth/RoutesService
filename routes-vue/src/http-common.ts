import axios from "axios";

const getRootUrl = () => {
  var wnd: any = window;
  if (wnd.webpackHotUpdate) {
    return 'http://localhost:8585/RoutesService/';
  } else {
    const loc = window.location.toString();
    const root = loc.substring(0, loc.indexOf('/webapp'));
    return root + '/';
  }
};

const rootUrl = getRootUrl();
console.warn('Current root: ' + rootUrl);

export default axios.create({
  //baseURL: "http://localhost:8585/RoutesService/webapp/",
  baseURL: rootUrl,
  headers: {
    "Content-type": "application/xml"
  }
});