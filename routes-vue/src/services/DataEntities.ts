import { IEntityCreationSpec } from './DataService';
import xml, { XmlNode, IXmlEntity, IXmlContent, XmlElement, XmlElementContent, XmlElementFabric } from './XmlUtils';

export class Coordinates {
  public constructor (
    public x : number,
    public y : number
  ) {
  }
}
export class LocationInfo implements IXmlEntity, IXmlContent, IEntityCreationSpec {
    public constructor (
        public id: number,
        public x : number,
        public y : number,
        public z : number
    ) {
    }
    toXmlSpecElement(x: XmlElementFabric): XmlElement {
        return x.element('LocationCreationSpec', this.getXmlData(x));
    }

    public static parse (x: XmlNode) : LocationInfo {
        return new LocationInfo(
            x.int('./@Id'),
            x.float('./@X'),
            x.float('./@Y'),
            x.float('./@Z')
        );
    }

    toXmlElement(x: XmlElementFabric): XmlElement {
        return x.element('Location', this.toXmlElementContent(x));
    }
    toXmlElementContent(x: XmlElementFabric): XmlElementContent {
        return this.getXmlData(x).append({
            Id: this.id,
        });
    }

    getXmlData(x: XmlElementFabric): XmlElementContent {
        return x.content({
            X: this.x,
            Y: this.y,
            Z: this.z
        });
    }

}
export class RouteInfo implements IXmlEntity, IEntityCreationSpec {
    public constructor (
        public id: number,
        public name: string,
        public distance: number,
        public creationDate: Date,
        public coordinates: Coordinates,
        public from: LocationInfo,
        public to: LocationInfo
    ) {
    }

    public get creationDateString(): string {
        return this.creationDate.toLocaleString();
    }

    public static parse (x: XmlNode) : RouteInfo {
        return new RouteInfo(
            x.int('./@Id'),
            x.string('./@Name'),
            x.float('./@Distance'),
            new Date(x.int('./m:CreationDate/@Mills')),
            new Coordinates(
                x.float('./m:Coordinates/@X'),
                x.float('./m:Coordinates/@Y')
            ),
            LocationInfo.parse(x.node('./m:From')),
            LocationInfo.parse(x.node('./m:To'))
        );
    }

    toXmlElement(x: XmlElementFabric): XmlElement {
        return x.element('Route', this.getXmlData(x).append({
                Id: this.id,
            }, [
                x.element('CreationDate', { Mills: this.creationDate.valueOf() }),
            ]
        ));
    }

    getXmlData(x: XmlElementFabric): XmlElementContent {
        return x.content({
                Name: this.name,
                Distance: this.distance
            }, [
                x.element('Coordinates', { X: this.coordinates.x, Y: this.coordinates.y }),
                x.element('From', this.from),
                x.element('To', this.to)
            ]
        );
    }

    toXmlSpecElement(x: XmlElementFabric) : XmlElement {
        return x.element('RouteCreationSpec', this.getXmlData(x));
    }

    public static makeEmptyRoute(): RouteInfo {
        return new RouteInfo(
            0, '', 0,
            new Date(Date.now()),
            new Coordinates(0, 0),
            new LocationInfo(0, 0, 0, 0),
            new LocationInfo(0, 0, 0, 0)
        );
    }
}

export class PagedEntities<T> {
    public constructor(
        public readonly entities: T[],
        public readonly totalCount: number,
        public readonly pageNumber: number,
        public readonly pageSize: number,
        public readonly pagesCount: number,
    ) {
    }

    public get isFirstPage() { return this.pageNumber < 1; }
    public get isLastPage() { return this.pageNumber > this.pagesCount - 2; }

    public static parse<T> (x: XmlNode, rootXPath: string, entityXPath: string, entityCtor: (x: XmlNode) => T) : PagedEntities<T> {
        const r = x.node(rootXPath);
        return new PagedEntities<T>(
            r.nodes(entityXPath).map(entityCtor),
            r.int('./@TotalCount'),
            r.int('./@PageNumber'),
            r.int('./@PageSize'),
            r.int('./@PagesCount'),
        );
    }
}

export class RoutesInfo extends PagedEntities<RouteInfo> {
    public constructor() { super([], 0, 0, 0, 0); }

    public static parseRoutes (x: XmlNode) : RoutesInfo {
        return PagedEntities.parse(x, '/m:RoutesQueryResult', './m:Routes/m:Route', RouteInfo.parse);
    }
}


export class LocationsInfo extends PagedEntities<LocationInfo>{
    public constructor() { super([], 0, 0, 0, 0); }

    public static parseLocations (x: XmlNode) : LocationsInfo {
        return PagedEntities.parse(x, '/m:LocationsQueryResult', './m:Locations/m:Location', LocationInfo.parse);
    }
}

export class RemoteErrorInfo {
    public constructor(
        public readonly message: string,
        public readonly stackTrace: string,
        public readonly typeName: string
    ){
    }

    public static parse(x: XmlNode) : RemoteErrorInfo {
        return new RemoteErrorInfo(
            x.string('./m:Message/text()'),
            x.string('./m:StackTrace/text()'),
            x.string('./@Type'),
        );
    }
}

export class OperationStatusInfo {
    public constructor(
        public readonly message: string,
        public readonly status: string
    ){
    }

    public static parse(x: XmlNode) : OperationStatusInfo {
        return new OperationStatusInfo(
            x.string('./m:Message/text()'),
            x.string('./@Status')
        );
    }
}
