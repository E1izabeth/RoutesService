import { AxiosResponse } from 'axios';
import httpCtx from "./HttpContext";
import DataService from './DataService';
import { OperationStatusInfo, RouteInfo, RoutesInfo } from './DataEntities';
import xml from './XmlUtils';

const http = httpCtx.navigations;

class NavigatorService {

  findShortest(from: number, to: number): Promise<RoutesInfo> {
    return http.get(`/route/${from}/${to}/shortest`, RoutesInfo.parseRoutes);
  }
  findLongest(from: number, to: number): Promise<RoutesInfo> {
    return http.get(`/route/${from}/${to}/longest`, RoutesInfo.parseRoutes);
  }

  findAllOrdered(from: number, to: number, fieldName: string): Promise<RoutesInfo> {
    return http.get(`/routes/${from}/${to}/${fieldName}`, RoutesInfo.parseRoutes);
  }
}

export default new NavigatorService();
