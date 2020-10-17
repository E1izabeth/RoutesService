import { AxiosResponse } from 'axios';
import http from "../http-common";

var parseXml = (text: string) : Node => {
  const parser = new DOMParser();
  const dom = parser.parseFromString(text, "application/xml");
  return dom.documentElement;
}

var xpath = (node: Node, expr: string, type?: number) : XPathResult => {
  const doc = node.ownerDocument || node as Document;

  const nsResolver: XPathNSResolver = (prefix) => {
    var ns = new Map<string|null,string>([
      ['m', 'main.webapp.xml'],
    ]);
    return ns.get(prefix)||null;
  };

  const result = doc.evaluate(expr, node, nsResolver, type);
  return result;
}
var xpathString = (node: Node, expr: string) : string => {
  const result = xpath(node, expr, XPathResult.STRING_TYPE);
  return result.stringValue;
}
var xpathNodes = (node: Node, expr: string) : Node[] => {
  const result = xpath(node, expr);
  var items = [];
  for (var item = result.iterateNext(); item; item = result.iterateNext()) {
    items.push(item);
  }
  return items;
}

export class Coordinates {
  public constructor (
    public x : number,
    public y : number
  ) {
  }
}
export class Location {
  public constructor (
    public x : number,
    public y : number,
    public z : number
  ) {
  }
}
export class RouteInfo {
  public constructor (
    public id: number,
    public name: string,
    public distance: number,
    public creationDate: Date,
    public coordinates: Coordinates,
    public from: Location,
    public to: Location
  ) {
  }

  public get creationDateString(): string {
    return this.creationDate.toLocaleString();
  }

  public toXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <Route Id="${this.id}" Name="${this.name}" Distance="${this.distance}" xmlns="main.webapp.xml">
        <Coordinates X="${this.coordinates.x}" Y="${this.coordinates.y}"/>
        <From X="${this.from.x}" Y="${this.from.y}" Z="${this.from.z}"/>
        <To X="${this.to.x}" Y="${this.to.y}" Z="${this.to.z}"/>
        <CreationDate Mills="${this.creationDate.valueOf()}"/>
    </Route>`;
  }

  public toSpecXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <RouteCreationSpec Name="${this.name}" Distance="${this.distance}" xmlns="main.webapp.xml">
        <Coordinates X="${this.coordinates.x}" Y="${this.coordinates.y}"/>
        <From X="${this.from.x}" Y="${this.from.y}" Z="${this.from.z}"/>
        <To X="${this.to.x}" Y="${this.to.y}" Z="${this.to.z}"/>
    </RouteCreationSpec>`;
  }

  public static makeEmptyRoute(): RouteInfo {
    return new RouteInfo(
      0, '', 0,
      new Date(Date.now()),
      new Coordinates(0, 0),
      new Location(0, 0, 0),
      new Location(0, 0, 0)
    );
  }
}

export class RoutesInfo {
  public constructor(
    public readonly routes: RouteInfo[],
    public readonly totalCount: number,
    public readonly pageNumber: number,
    public readonly pageSize: number,
    public readonly pagesCount: number,
  ) {
  }

  public get isFirstPage() { return this.pageNumber < 1; }
  public get isLastPage() { return this.pageNumber > this.pagesCount - 2; }
}

export class RemoteErrorInfo {
  public constructor(
    public readonly message: string,
    public readonly stackTrace: string,
    public readonly typeName: string
  ){
  }
}

export class OperationStatusInfo {
  public constructor(
    public readonly message: string,
    public readonly status: string
  ){
  }
}

var parseRoute = (node: Node) : RouteInfo => {
  return new RouteInfo(
    Number.parseInt(xpathString(node, './@Id')),
    xpathString(node, './@Name'),
    Number.parseFloat(xpathString(node, './@Distance')),
    new Date(Number.parseInt(xpathString(node, './m:CreationDate/@Mills'))),
    new Coordinates(
      Number.parseFloat(xpathString(node, './m:Coordinates/@X')),
      Number.parseFloat(xpathString(node, './m:Coordinates/@Y'))
    ),
    new Location(
      Number.parseFloat(xpathString(node, './m:From/@X')),
      Number.parseFloat(xpathString(node, './m:From/@Y')),
      Number.parseFloat(xpathString(node, './m:From/@Z'))
    ),
    new Location(
      Number.parseFloat(xpathString(node, './m:To/@X')),
      Number.parseFloat(xpathString(node, './m:To/@Y')),
      Number.parseFloat(xpathString(node, './m:To/@Z'))
    )
  )
}

var parseError = (node: Node) : RemoteErrorInfo => {
  return new RemoteErrorInfo(
    xpathString(node, './m:Message/text()'),
    xpathString(node, './m:StackTrace/text()'),
    xpathString(node, './@Type'),
  );
}

var parseOperationStatus = (node: Node) : OperationStatusInfo => {
  return new OperationStatusInfo(
    xpathString(node, './m:Message/text()'),
    xpathString(node, './@Status')
  );
}

var parseRoutesList = (root: Node) : RoutesInfo => {
  return new RoutesInfo(
    xpathNodes(root, '/m:RoutesQueryResult/m:Routes/m:Route').map(parseRoute),
    Number.parseInt(xpathString(root, '/m:RoutesQueryResult/@TotalCount')),
    Number.parseInt(xpathString(root, '/m:RoutesQueryResult/@PageNumber')),
    Number.parseInt(xpathString(root, '/m:RoutesQueryResult/@PageSize')),
    Number.parseInt(xpathString(root, '/m:RoutesQueryResult/@PagesCount')),
  );
}

class RouteDataService {
  private handleError(e: any) {
    if (e.response) {
      console.log('error body ' + e.response.data);
      e.remote = parseError(parseXml(e.response.data));
    }
    throw e;
    return e;
  }

  getAll():Promise<RoutesInfo> {
    return http.get("/routes")
               .then((rs) => parseRoutesList(parseXml(rs.data)))
               .catch(this.handleError);
  }

  get(id: number): Promise<RouteInfo> {
    return http.get(`/routes/${id}`)
               .then((rs) => parseRoute(parseXml(rs.data)))
               .catch(this.handleError);
  }

  create(data: RouteInfo): Promise<RouteInfo> {
    return http.post("/routes?action=add", data.toSpecXml())
               .then((rs) => parseRoute(parseXml(rs.data)))
               .catch(this.handleError);
  }

  delOneByDst(distance: number): Promise<OperationStatusInfo> {
    var querySpec = `<?xml version="1.0" encoding="UTF-8" ?><DeleteRouteByDistanceSpec ExactDistance="${distance}" xmlns="main.webapp.xml" />`;
    return http.post("/routes?action=del-by-dst", querySpec)
               .then((rs) => parseOperationStatus(parseXml(rs.data)))
               .catch(this.handleError);
  }

  getSummaryDst(): Promise<number> {
    return http.get("/routes?action=get-dst")
               .then((rs) => {
                  const dom = parseXml(rs.data);
                  const value = xpathString(dom, '/m:DistanceSumQueryResult/@Value');
                  return Number.parseFloat(value);
               })
               .catch(this.handleError);
  }

  getByName(name: string): Promise<RoutesInfo> {
    return http.get(`/routes?action=get-by-name&name=${name}`)
               .then((rs) => parseRoutesList(parseXml(rs.data)))
               .catch(this.handleError);
  }

  update(data: RouteInfo): Promise<AxiosResponse> {
    console.log(`updating ${data}`);
    return http.put(`/routes/${data.id}`, data.toXml())
               .catch(this.handleError);
  }

  delete(id: number): Promise<AxiosResponse> {
    return http.delete(`/routes/${id}`)
               .catch(this.handleError);
  }

  findByParams(filter?: string, sort?: string, pageSize?: number, pageNum?: number): Promise<RoutesInfo> {
    return http.get(`/routes?filter=${filter ? encodeURIComponent(filter) : ''}&sort=${sort ? encodeURIComponent(sort) : ''}
                            &page-size=${pageSize ? encodeURIComponent(pageSize) : ''}&page-num=${pageNum ? encodeURIComponent(pageNum) : ''}`)
              .then((rs) => parseRoutesList(parseXml(rs.data)))
              .catch(this.handleError);
  }
}

export default new RouteDataService();