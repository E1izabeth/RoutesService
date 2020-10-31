package main.webapp;

import main.webapp.xml.*;

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

    public RoutesListType makeRoutesList(RouteType... acts) {
        RoutesListType obj = new RoutesListType();
        Arrays.stream(acts).forEach(l -> obj.getRoute().add(l));
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

    public LocationType makeLocation(int x, int y, float z) {
        LocationType loc = new LocationType();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        return loc;
    }

    public RouteType translateRouteEntity(RouteDbEntity entity) {
        RouteType r = makeRoute(
                entity.id, entity.creationDate, entity.name, entity.distance,
                makeCoordinates(entity.coordX, entity.coordY),
                makeLocation(entity.fromLocX, entity.fromLocY, entity.fromLocZ),
                makeLocation(entity.toLocX, entity.toLocY, entity.toLocZ),
                this.makeRouteLinks(entity.id, entity.name)
        );
        return r;
    }

    public RoutesQueryResultType translateQueryResultEntity(Iterable<RouteDbEntity> routes, String filter, String sort, long pageNumber, long pageSize, long pagesCount, long totalCount) {
        ArrayList<RouteType> results = new ArrayList<>();
        for (RouteDbEntity entity : routes) {
            results.add(this.translateRouteEntity(entity));
        }

        RoutesQueryResultType r = makeQueryResult(
                makeRoutesList(results.toArray(new RouteType[0])),
                pageNumber, pageSize, pagesCount, totalCount,
                this.makeRootLinks(filter, sort, pageSize, pageNumber, pageNumber > 0, pageNumber < pagesCount - 1)
        );

        return r;
    }

    public DistanceQueryResultType translateDistanceQueryResult(long value) {
        DistanceQueryResultType result = new DistanceQueryResultType();
        result.setValue(value);
        return result;
    }

    public DiscoverabilityType makeRootLinks(String filter, String sort, Long pageSize, Long pageNum, boolean back, boolean fwd) {
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
                this.makeAction("GetDistanceSum", HttpMethodType.POST, _urls.forRoutesGetDistanceSumAction(), null, "DistanceSumQueryResult"),
                this.makeAction("GetRoutesByNameSubstring", HttpMethodType.POST, _urls.forRoutesGetByNameAction(), "QueryRoutesByNameSpec", "RoutesQueryResult")
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
}
