package main.webapp;

import main.webapp.db.DbConnection;
import main.webapp.db.DbContext;
import main.webapp.db.DbParametrizedQueryInfo;
import main.webapp.db.DbQueryParameter;
import main.webapp.query.QueryTranslator;
import main.webapp.xml.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

public class RoutesContext implements AutoCloseable{
    private final XmlEntityBuilder _xmlBuilder;
    private final DbContext _dbCtx;
    private final Logger _log;
    private final HttpServletRequest _request;
    private final HttpServletResponse _response;

    public RoutesContext(String prefix, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        _xmlBuilder = new XmlEntityBuilder(prefix);
        _request = request;
        _response = response;

        _log = Logger.getLogger(getClass().getName());

        _dbCtx = new DbContext(new DbConnection("pg", 5432, "studs", "s243891", "pui193"));

        if (!_dbCtx.isLocalTableExists(RouteDbEntity.class)) {
            _dbCtx.createTable(RouteDbEntity.class);
        }
    }

    private <T> T parseRequest(Class<T> type) throws IOException {
        try {
            MySerializer serializer = new MySerializer();
            T obj = serializer.deserialize(type, _request.getReader());
            return obj;
        } catch (SAXException ex) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage(), ex);
        } catch (JAXBException ex) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage(), ex);
        } catch (NumberFormatException ex) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    private void prepareResponse(Object result) throws Throwable {
        _response.setContentType("text/xml;charset=UTF-8");

        PrintWriter out = _response.getWriter();
        MySerializer serializer = new MySerializer();
        serializer.serialize(result, out);
        out.close();
    }

    public void queryRoutes(String filterStr, String sortStr, Long pageSize, Long pageNum) throws Throwable {
        QueryTranslator qt = new QueryTranslator();

        if (pageSize == null)
            pageSize = qt.getDefaultPageSize();
        if (pageNum == null)
            pageNum = 0L;

        Long from = pageNum == null ? 0 : pageNum * pageSize;
        Long to = from + pageSize;

        DbParametrizedQueryInfo countQuery = qt.translateQuery(filterStr, sortStr, null, null);
        long totalCount = _dbCtx.compute(long.class, new DbParametrizedQueryInfo("SELECT COUNT(*) FROM (" + countQuery.sql + ") AS q", countQuery.parameters));

        DbParametrizedQueryInfo query = qt.translateQuery(filterStr, sortStr, from, to);
        Iterable<RouteDbEntity> data = _dbCtx.select(RouteDbEntity.class, query);

        RoutesQueryResultType qr = _xmlBuilder.translateQueryResultEntity(data, filterStr, sortStr, pageNum, pageSize, (totalCount - 1) / pageSize + 1, totalCount);

        this.prepareResponse(qr);
    }

    private RouteDbEntity findRoute(Long id) throws Throwable {
        if (id == null) {
            return null;
        }

        Iterable<RouteDbEntity> routes = _dbCtx.select(RouteDbEntity.class, new DbParametrizedQueryInfo("SELECT * FROM RouteDbEntity WHERE id = ?", new DbQueryParameter(1, id)));
        Optional<RouteDbEntity> maybeRoute = StreamSupport.stream(routes.spliterator(), false).findAny();
        if (maybeRoute.isPresent()) {
            RouteDbEntity route = maybeRoute.get();
            return route;
        } else {
            return null;
        }
    }

    public void getRoute(Long id) throws Throwable {
        RouteDbEntity route = this.findRoute(id);
        if (route == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            RouteType result = _xmlBuilder.translateRouteEntity(route);
            this.prepareResponse(result);
        }
    }

    @Override
    public void close() throws Exception {
        _dbCtx.close();
    }

    public void deleteRoute(Long id) throws Throwable {
        RouteDbEntity route = this.findRoute(id);
        if (route == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            _dbCtx.mutate(new DbParametrizedQueryInfo("DELETE FROM RouteDbEntity WHERE id = ?", new DbQueryParameter(1, id)));
        }
    }

    public void updateRoute(Long id) throws Throwable {
        RouteDbEntity route = this.findRoute(id);
        if (route == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            RouteType newRoute = this.parseRequest(RouteType.class);
            if (newRoute.getId() != route.id) {
                throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Inconsistent entity id: expected " + route.id + " while given " + newRoute.getId());
            }

            route.name = newRoute.getName();
            makeRoute(route, newRoute);

            _dbCtx.update(route, new DbParametrizedQueryInfo("id = ?", new DbQueryParameter(1, id)));
        }
    }

    private void makeRoute(RouteDbEntity route, RouteType newRoute) {
        route.distance = newRoute.getDistance();

        route.coordX = newRoute.getCoordinates().getX();
        route.coordY = newRoute.getCoordinates().getY();

        route.fromLocX = newRoute.getFrom().getX();
        route.fromLocY = newRoute.getFrom().getY();
        route.fromLocZ = newRoute.getFrom().getZ();

        route.toLocX = newRoute.getTo().getX();
        route.toLocY = newRoute.getTo().getY();
        route.toLocZ = newRoute.getTo().getZ();
    }

    public void addNewRoute() throws Throwable {
        RouteType newRoute = this.parseRequest(RouteType.class);
        RouteDbEntity entity = decomposeEntity(newRoute);
        entity.creationDateMills = System.currentTimeMillis();
        _dbCtx.insert(entity);
        this.prepareResponse(_xmlBuilder.translateRouteEntity(entity));
        _response.setStatus(HttpServletResponse.SC_CREATED);
    }

    public void delRouteByDst() throws Throwable {
        OperationStatusInfoType result = new OperationStatusInfoType();
        DeleteRouteByDistanceSpecType spec = this.parseRequest(DeleteRouteByDistanceSpecType.class);

        Iterable<RouteDbEntity> routes = _dbCtx.select(RouteDbEntity.class, new DbParametrizedQueryInfo("SELECT * FROM RouteDbEntity WHERE distance = ? LIMIT 1", new DbQueryParameter(1, spec.getExactDistance())));
        Optional<RouteDbEntity> maybeRoute = StreamSupport.stream(routes.spliterator(), false).findAny();
        if (maybeRoute.isPresent()) {
            _dbCtx.mutate(new DbParametrizedQueryInfo("DELETE FROM RouteDbEntity WHERE id = ?", new DbQueryParameter(1, maybeRoute.get().id)));
            result.setMessage("Some route deleted");
            result.setStatus(OperationStatus.ACCOMPLISHED);
        } else {
            result.setMessage("No route adequate to request found");
            result.setStatus(OperationStatus.DENIED);
        }
        this.prepareResponse(result);
    }

    public void getDst() throws Throwable {
        long value = _dbCtx.compute(long.class, new DbParametrizedQueryInfo("SELECT SUM(distance) FROM RouteDbEntity"));
        this.prepareResponse(_xmlBuilder.translateDistanceQueryResult(value));
    }

    public void getRouteByName() throws Throwable {
        QueryRoutesByNameSpecType spec = this.parseRequest(QueryRoutesByNameSpecType.class);
        this.queryRoutes("name contains \"" + spec.getExactName() + "\"", null, null, null);
    }

    private RouteDbEntity decomposeEntity(RouteType route) {
        RouteDbEntity obj = new RouteDbEntity();

        obj.id = route.getId();
        obj.name = route.getName();
        obj.creationDateMills = route.getCreationDate().getMills();
        makeRoute(obj, route);

        return obj;
    }

    public void handleWebRequest(String baseUrl, String requestPath) throws Throwable {
        RestWebUi.handleFreeWebRequest(_request, _response, baseUrl, requestPath);
    }
}