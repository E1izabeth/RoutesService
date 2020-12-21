import axios, { AxiosInstance, AxiosResponse } from "axios";
import { RemoteErrorInfo } from './DataEntities';
import xml, { IXmlEntity, XmlNode } from './XmlUtils';


const getRootUrl = () => {
  var wnd: any = window;
  if (wnd.webpackHotUpdate) {
    //return 'http://localhost:8585/RoutesService/';
    return 'http://localhost:8080';
  } else {
    const loc = window.location.toString();
    const root = loc.substring(0, loc.indexOf('/webapp'));
    return root + '/';
  }
};

const rootUrl = getRootUrl();
console.warn('Current root: ' + rootUrl);

const makeHttpCtx = (rootUrl: string) => axios.create({
  //baseURL: "http://localhost:8585/RoutesService/webapp/",
  baseURL: rootUrl,
  headers: {
    "Content-type": "application/xml"
  }
});

const isString = (o: any) => typeof o === "string";

export class HttpContext {
  public constructor(
    public readonly raw: AxiosInstance
  ) {
  }

  protected handleError(e: any) {
    if (e.response) {
      console.log('error body ' + e.response.data);
      e.remote = RemoteErrorInfo.parse(xml.parse(e.response.data));
    }
    throw e;
    return e;
  }

  public get<T>(url: string, parseEntity: (node: XmlNode) => T): Promise<T> {
    return this.raw.get(url)
                    .then((rs) => parseEntity(xml.parse(rs.data)))
                    .catch(this.handleError);
  }

  public post<T>(url: string, data: String|IXmlEntity, parseEntity: (node: XmlNode) => T): Promise<T> {
    return this.raw.post(url, isString(data) ? data : xml.create((data as IXmlEntity).toXmlElement.bind(data)))
                    .then((rs) => parseEntity(xml.parse(rs.data)))
                    .catch(this.handleError);
  }

  public put<S>(url: string, data: String|IXmlEntity): Promise<AxiosResponse> {
    return this.raw.put(url, isString(data) ? data : xml.create((data as IXmlEntity).toXmlElement.bind(data)))
                    .catch(this.handleError);
  }

  public delete(url: string): Promise<AxiosResponse> {
    return this.raw.delete(url)
                    .catch(this.handleError);
  }
};

const routesSvcRoot = 'https://localhost:8586/';
const navSvcRoot = 'https://localhost:8687/navigator-0.0.1-SNAPSHOT/navigator';

export default {
  routes: new HttpContext(makeHttpCtx(routesSvcRoot)),
  locations: new HttpContext(makeHttpCtx(routesSvcRoot)),
  navigations: new HttpContext(makeHttpCtx(navSvcRoot)),
}
