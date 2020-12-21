import { AxiosResponse } from 'axios';
import httpCtx from "./HttpContext";
import DataService  from './DataService';
import { LocationInfo, LocationsInfo } from './DataEntities';

const http = httpCtx.locations;

class LocationDataService extends DataService<LocationInfo, LocationsInfo> {

  public constructor() {
    super(http, '/locations', LocationInfo.parse, LocationsInfo.parseLocations);
  }
}

export default new LocationDataService();
