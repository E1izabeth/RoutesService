package com.example.routes;

import com.example.routes.db.IRouteDbEntity;
import com.example.routes.db.LocatedRouteDbEntity;
import com.example.routes.db.LocationDbEntity;
import  com.example.routes.xml.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;

public class XmlEntityBuilder {

    private final Urls _urls;

    public XmlEntityBuilder(String prefix) {
        _urls = new Urls(prefix);
    }

    public RoutesQueryResultType makeQueryResult(RoutesListType routes, long pageNumber, long pageSize, long pagesCount, long totalCount, DiscoverabilityType discoverability) {
        RoutesQueryResultType qr = new RoutesQueryResultType();
        qr.setRoutes(routes);
        qr.setPageNumber(pageNumber);
        qr.setPagesCount(pagesCount);
        qr.setPageSize(pageSize);
        qr.setTotalCount(totalCount);
        qr.setDiscoverability(discoverability);
        return qr;
    }

    public LocationsQueryResultType makeQueryResult(LocationsListType locations, long pageNumber, long pageSize, long pagesCount, long totalCount, DiscoverabilityType discoverability) {
        LocationsQueryResultType qr = new LocationsQueryResultType();
        qr.setLocations(locations);
        qr.setPageNumber(pageNumber);
        qr.setPagesCount(pagesCount);
        qr.setPageSize(pageSize);
        qr.setTotalCount(totalCount);
        qr.setDiscoverability(discoverability);
        return qr;
    }

    public RoutesListType makeRoutesList(RouteType... acts) {
        RoutesListType obj = new RoutesListType();
        Arrays.stream(acts).forEach(l -> obj.getRoute().add(l));
        return obj;
    }

    public LocationsListType makeLocationsList(LocationType... acts) {
        LocationsListType obj = new LocationsListType();
        Arrays.stream(acts).forEach(l -> obj.getLocation().add(l));
        return obj;
    }

    public RouteType makeRoute(long id, LocalDateTime creationDate, String name, long distance, CoordinatesType coords, LocationType from, LocationType to, DiscoverabilityType discoverability) {
        RouteType r = new RouteType();
        r.setCoordinates(coords);
        r.setCreationDate(new LocalDate(){{
            setMills(creationDate.toInstant(ZoneOffset.UTC).toEpochMilli());
        }});
        r.setDistance(distance);
        r.setId(id);
        r.setName(name);
        r.setFrom(from);
        r.setTo(to);
        r.setDiscoverability(discoverability);
        return r;
    }

    public LinksListType makeLinksList(LinkType... links) {
        LinksListType obj = new LinksListType();
        Arrays.stream(links).forEach(l -> obj.getLink().add(l));
        return obj;
    }

    public ActionsListType makeActionsList(ActionType... acts) {
        ActionsListType obj = new ActionsListType();
        Arrays.stream(acts).forEach(l -> obj.getAction().add(l));
        return obj;
    }

    public LinkType makeLink(Long id, String name, LinkRelationType rel, String href, String resultKind) {
        LinkType link = new LinkType();
        link.setId(id);
        link.setName(name);
        link.setRelation(rel);
        link.setHref(href);
        link.setKind(resultKind);
        return link;
    }

    public ActionType makeAction(String name, HttpMethodType method, String href, String paramKind, String resultKind) {
        ActionType action = new ActionType();
        action.setName(name);
        action.setMethod(method);
        action.setHref(href);
        action.setParamKind(paramKind);
        action.setResultKind(resultKind);
        return action;
    }

    public DiscoverabilityType makeDiscoverability(LinksListType links, ActionsListType actions) {
        DiscoverabilityType obj = new DiscoverabilityType();
        obj.setLinks(links);
        obj.setActions(actions);
        return obj;
    }

    public CoordinatesType makeCoordinates(int x, double y) {
        CoordinatesType coord = new CoordinatesType();
        coord.setX(x);
        coord.setY(y);
        return coord;
    }

    public LocationType makeLocation(long id, int x, int y, float z) {
        LocationType loc = new LocationType();
        loc.setId(id);
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        return loc;
    }

    public RouteType translateRouteEntity(IRouteDbEntity entity) {
        RouteType r = makeRoute(
                entity.getId(), entity.getCreationDate(), entity.getName(), entity.getDistance(),
                makeCoordinates(entity.getCoordX(), entity.getCoordY()),
                makeLocation(entity.getLocationFrom().id, entity.getLocationFrom().x, entity.getLocationFrom().y, entity.getLocationFrom().z),
                makeLocation(entity.getLocationTo().id, entity.getLocationTo().x, entity.getLocationTo().y, entity.getLocationTo().z),
                this.makeRouteLinks(entity.getId(), entity.getName())
        );
        return r;
    }

    public LocationType translateLocationEntity(LocationDbEntity entity) {
        LocationType r = makeLocation(entity.id, entity.x, entity.y, entity.z);
        return r;
    }

    public RoutesQueryResultType translateRoutesQueryResultEntity(Iterable<LocatedRouteDbEntity> routes, String filter, String sort, long pageNumber, long pageSize, long pagesCount, long totalCount) {
        ArrayList<RouteType> results = new ArrayList<>();
        for (LocatedRouteDbEntity entity : routes) {
            results.add(this.translateRouteEntity(entity));
        }

        RoutesQueryResultType r = makeQueryResult(
                makeRoutesList(results.toArray(new RouteType[0])),
                pageNumber, pageSize, pagesCount, totalCount,
                this.makeRouteRootLinks(filter, sort, pageSize, pageNumber, pageNumber > 0, pageNumber < pagesCount - 1)
        );

        return r;
    }

    public LocationsQueryResultType translateLocationsQueryResultEntity(Iterable<LocationDbEntity> locations, String filter, String sort, long pageNumber, long pageSize, long pagesCount, long totalCount) {
        ArrayList<LocationType> results = new ArrayList<>();
        for (LocationDbEntity entity : locations) {
            results.add(this.translateLocationEntity(entity));
        }

        LocationsQueryResultType r = makeQueryResult(
                makeLocationsList(results.toArray(new LocationType[0])),
                pageNumber, pageSize, pagesCount, totalCount,
                this.makeLocationRootLinks(filter, sort, pageSize, pageNumber, pageNumber > 0, pageNumber < pagesCount - 1)
        );

        return r;
    }

    public DistanceQueryResultType translateDistanceQueryResult(long value) {
        DistanceQueryResultType result = new DistanceQueryResultType();
        result.setValue(value);
        return result;
    }

    public DiscoverabilityType makeRouteRootLinks(String filter, String sort, Long pageSize, Long pageNum, boolean back, boolean fwd) {
        ArrayList<LinkType> links = new ArrayList<>();

        if (back)
            links.add(this.makeLink(null, null, LinkRelationType.BACKWARD, _urls.forRoutes(filter, sort, pageSize, pageNum - 1), null));
        if (fwd)
            links.add(this.makeLink(null, null, LinkRelationType.FORWARD, _urls.forRoutes(filter, sort, pageSize, pageNum + 1), null));

        return this.makeDiscoverability(
            this.makeLinksList(links.toArray(new LinkType[0])),
            this.makeActionsList(
                this.makeAction("CreateRoute", HttpMethodType.POST, _urls.forRoutesCreateRouteAction(), "Route", "Route"),
                this.makeAction("DeleteRouteByDistance", HttpMethodType.POST, _urls.forRoutesDeleteByDistanceAction(), "DeleteRouteByDistanceSpec", null),
                this.makeAction("GetDistanceSum", HttpMethodType.GET, _urls.forRoutesGetDistanceSumAction(), null, "DistanceSumQueryResult"),
                this.makeAction("GetRoutesByNameSubstring", HttpMethodType.GET, _urls.forRoutesGetByNameAction(), "QueryRoutesByNameSpec", "RoutesQueryResult")
            )
        );
    }

    public DiscoverabilityType makeRouteLinks(long id, String name) {
        String routeHref = _urls.forRoute(id);
        return this.makeDiscoverability(
            this.makeLinksList(
                this.makeLink(id, name, LinkRelationType.ALTERNATE, routeHref, "Route"),
                this.makeLink(null,"Collection root", LinkRelationType.UP, _urls.forRoutes(), "RoutesQueryResult")
            ),
            this.makeActionsList(
                this.makeAction("UpdateRoute", HttpMethodType.PUT, routeHref, "Route", null),
                this.makeAction("DeleteRoute", HttpMethodType.DELETE, routeHref, null, null)
            )
        );
    }

    public DiscoverabilityType makeLocationRootLinks(String filter, String sort, Long pageSize, Long pageNum, boolean back, boolean fwd) {
        ArrayList<LinkType> links = new ArrayList<>();

        if (back)
            links.add(this.makeLink(null, null, LinkRelationType.BACKWARD, _urls.forLocations(filter, sort, pageSize, pageNum - 1), null));
        if (fwd)
            links.add(this.makeLink(null, null, LinkRelationType.FORWARD, _urls.forLocations(filter, sort, pageSize, pageNum + 1), null));

        return this.makeDiscoverability(
                this.makeLinksList(links.toArray(new LinkType[0])),
                this.makeActionsList(
                        this.makeAction("CreateLocation", HttpMethodType.POST, _urls.forLocationsCreateLocationAction(), "Location", "Location"))
        );
    }

    public DiscoverabilityType makeLocationLinks(long id, String name) {
        String routeHref = _urls.forLocation(id);
        return this.makeDiscoverability(
                this.makeLinksList(
                        this.makeLink(id, name, LinkRelationType.ALTERNATE, routeHref, "Location"),
                        this.makeLink(null,"Collection root", LinkRelationType.UP, _urls.forRoutes(), "LocationsQueryResult")
                ),
                this.makeActionsList(
                        this.makeAction("UpdateLocation", HttpMethodType.PUT, routeHref, "Location", null),
                        this.makeAction("DeleteLocation", HttpMethodType.DELETE, routeHref, null, null)
                )
        );
    }
}
