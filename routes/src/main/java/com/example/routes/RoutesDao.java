package com.example.routes;

import com.example.routes.db.*;
import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Component
@Transactional
public class RoutesDao extends MyDao {

    @Autowired
    public LocationsRepository _locations;

    @Autowired
    public RoutesRepository _routes;

    @Autowired
    public LocatedRoutesRepository _locatedRoutes;

    public RoutesDao() throws SQLException {
    }

    public RoutesQueryResultType queryRoutes(String filterStr, String sortStr, Integer pageSize, Integer pageNum) throws Throwable {
        EntitiesSelector<LocatedRouteDbEntity> selector = new MyDao.EntitiesSelector<LocatedRouteDbEntity>(_locatedRoutes::findAll);
        FilteringQueryResult<LocatedRouteDbEntity> result = selector.selectEntities(filterStr, sortStr, pageSize, pageNum);

        RoutesQueryResultType qr = _xmlBuilder.translateRoutesQueryResultEntity(result.data, filterStr, sortStr, result.pageNum, result.pageSize, result.pagesCount, result.totalCount);
        return qr;
    }

    private LocatedRouteDbEntity findRoute(Long id) throws Throwable {
        if (id == null) {
            return null;
        }

        Optional<LocatedRouteDbEntity> maybeRoute = _locatedRoutes.findById(id);
        if (maybeRoute.isPresent()) {
            LocatedRouteDbEntity route = maybeRoute.get();
            return route;
        } else {
            return null;
        }
    }

    public RouteType getRoute(Long id) throws Throwable {
        LocatedRouteDbEntity route = this.findRoute(id);
        if (route == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            RouteType result = _xmlBuilder.translateRouteEntity(route);
            return result;
        }
    }

    public void deleteRoute(Long id) throws Throwable {
        Optional<RouteDbEntity> mayBeRoute = _routes.findById(id);
        if (mayBeRoute.isPresent()) {
            _routes.deleteById(mayBeRoute.get().id);
        } else {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        }
    }

    public void updateRoute(Long id, RouteType newRoute) throws Throwable {
        Optional<RouteDbEntity> mayBeRoute = _routes.findById(id);
        OperationStatusInfoType result = new OperationStatusInfoType();

        if (mayBeRoute.isPresent()) {
            RouteDbEntity route = mayBeRoute.get();
            if (newRoute.getId() != route.id) {
                throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Inconsistent entity id: expected " + route.id + " while given " + newRoute.getId());
            }
            updateRouteEntity(route, newRoute);
            this.saveRoute(route);
        } else {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        }
    }

    private void updateRouteEntity(RouteDbEntity route, RouteType newRoute) {
        route.name = newRoute.getName();
        route.distance = newRoute.getDistance();

        route.coordX = newRoute.getCoordinates().getX();
        route.coordY = newRoute.getCoordinates().getY();

        route.FromLocation.x = newRoute.getFrom().getX();
        route.FromLocation.y = newRoute.getFrom().getY();
        route.FromLocation.z = newRoute.getFrom().getZ();

        route.ToLocation.x = newRoute.getTo().getX();
        route.ToLocation.y = newRoute.getTo().getY();
        route.ToLocation.z = newRoute.getTo().getZ();
    }

    @Autowired
    private EntityManager _entityManager;

    public RouteType addNewRoute(RouteCreationSpecType newRouteSpec) throws Throwable {
        RouteDbEntity entity = createRouteFromSpec(newRouteSpec);
        this.saveRoute(entity);

        RouteType newRoute = _xmlBuilder.translateRouteEntity(entity);
        //TODO _response.setStatus(HttpServletResponse.SC_CREATED);
        return newRoute;
    }

    public OperationStatusInfoType delRouteByDst(DeleteRouteByDistanceSpecType spec) throws Throwable {
        OperationStatusInfoType result = new OperationStatusInfoType();

        List<RouteDbEntity> someRoutes = _routes.findByDistance(spec.getExactDistance());
        if (someRoutes.size() > 0) {
            _routes.delete(someRoutes.get(0));
            result.setMessage("Some route deleted");
            result.setStatus(OperationStatus.ACCOMPLISHED);
        } else {
            result.setMessage("No route adequate to request found");
            result.setStatus(OperationStatus.DENIED);
        }
        return result;
    }

    public DistanceQueryResultType getDst() throws Throwable {
        long value = _routes.sumDistance();
        return _xmlBuilder.translateDistanceQueryResult(value);
    }

    public RoutesQueryResultType getRouteByName(String name) throws Throwable {
        return this.queryRoutes("name contains \"" + name + "\"", null, null, null);
    }

    private RouteDbEntity createRouteFromSpec(RouteCreationSpecType spec) {
        RouteDbEntity obj = new RouteDbEntity();

        obj.name = spec.getName();
        obj.creationDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeZone().toZoneId());

        obj.distance = spec.getDistance();

        obj.coordX = spec.getCoordinates().getX();
        obj.coordY = spec.getCoordinates().getY();

        obj.FromLocation = new LocationDbEntity();
        obj.FromLocation.x = spec.getFrom().getX();
        obj.FromLocation.y = spec.getFrom().getY();
        obj.FromLocation.z = spec.getFrom().getZ();

        obj.ToLocation = new LocationDbEntity();
        obj.ToLocation.x = spec.getTo().getX();
        obj.ToLocation.y = spec.getTo().getY();
        obj.ToLocation.z = spec.getTo().getZ();

        return obj;
    }

    private void saveRoute(RouteDbEntity route) {

        LocationDbEntity from = this.saveLocation(route.FromLocation);
        LocationDbEntity to = this.saveLocation(route.ToLocation);

        route.FromLocation = from;
        route.ToLocation = to;

        _routes.saveAndFlush(route);
    }

    private LocationDbEntity saveLocation(LocationDbEntity loc) {
        Optional<LocationDbEntity> old = _locations.findNearestExisting(loc.x, loc.y, loc.z);
        if (old.isPresent()) {
            return old.get();
        } else {
            loc.id = 0;
            _locations.saveAndFlush(loc);
            return loc;
        }
    }
}
