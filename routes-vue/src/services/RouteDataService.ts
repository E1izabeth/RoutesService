import { AxiosResponse } from 'axios';
import httpCtx from "./HttpContext";
import DataService from './DataService';
import { OperationStatusInfo, RouteInfo, RoutesInfo } from './DataEntities';
import xml from './XmlUtils';

const http = httpCtx.routes;

class RouteDataService extends DataService<RouteInfo, RoutesInfo> {

  public constructor() {
    super(http, '/routes', RouteInfo.parse, RoutesInfo.parseRoutes);
  }

  delOneByDst(distance: number): Promise<OperationStatusInfo> {
    // var querySpec = `<?xml version="1.0" encoding="UTF-8" ?><DeleteRouteByDistanceSpec ExactDistance="${distance}" xmlns="main.webapp.xml" />`;
    const querySpec = xml.create(x => x.element('DeleteRouteByDistanceSpec', { ExactDistance: distance }))
    return http.post("/routes?action=del-by-dst", querySpec, OperationStatusInfo.parse);
  }

  getSummaryDst(): Promise<number> {
    return http.get("/routes?action=get-dst", x => x.float('/m:DistanceSumQueryResult/@Value'));
  }

  getByName(name: string): Promise<RoutesInfo> {
    return http.get(`/routes?action=get-by-name&name=${name}`, RoutesInfo.parseRoutes);
  }
}

export default new RouteDataService();
