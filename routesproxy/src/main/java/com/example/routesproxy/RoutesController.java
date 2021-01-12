package com.example.routesproxy;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xml.webapp.main.*;

import javax.servlet.http.HttpServletResponse;

// @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081", allowedHeaders = "*")
@Component
// @RefreshScope
@RestController
@RequestMapping("")
public class RoutesController {
    /*

    get     /routes?...              получение массива элементов + q,f,o.
    post    /routes?action=add       добавление нового элемента,

    post    /routes?action=del-by-dst  Удалить один (любой) объект, значение поля distance которого эквивалентно заданному.
    get     /routes?action=get-dst     Рассчитать сумму значений поля distance для всех объектов.
    get     /routes?action=get-by-name Вернуть массив объектов, значение поля name которых содержит заданную подстроку.

    get     /routes/{id}             получение элемента по ИД,
    put     /routes/{id}             обновление элемента,
    delete  /routes/{id}             удаление элемента,


    * */

    private RoutesEndpointPortType routes() {
        RoutesEndpointService svc = new RoutesEndpointService();
        RoutesEndpointPortType port = svc.getRoutesEndpointPort();
        return port;
    }

    @GetMapping(path = Urls.ROUTES_ROOT, produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getRoutes(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false, name="page-size") Integer page_size, @RequestParam(required = false, name="page-num") Integer page_num) throws Throwable {
        return routes().getRoutes(new QuerySpecType().withFilter(filter).withSortOn(sort).withPageNumber(page_num).withPageSize(page_size));
    }

    @GetMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_GET_ROUTES_BY_NAME, produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getRouteByName(@RequestParam(name="name") String name) throws Throwable {
        return routes().getRoutesByName(new RoutesQueryByNameSpecType().withName(name));
    }

    @GetMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_GET_DISTANCE_SUM, produces = MediaType.APPLICATION_XML_VALUE)
    public DistanceQueryResultType getDistanceSum() throws Throwable {
        return routes().getDistanceSum(new EmptyType());
    }

    @PostMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_ADD, produces = MediaType.APPLICATION_XML_VALUE)
    public RouteType addRoute(@RequestBody RouteCreationSpecType routeCreationSpecType) throws Throwable {
        return routes().addRoute(routeCreationSpecType);
    }

    @PostMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_DELETE_BY_DISTANCE, produces = MediaType.APPLICATION_XML_VALUE)
    public OperationStatusInfoType deleteRouteByDistance(@RequestBody DeleteRouteByDistanceSpecType deleteRouteByDistanceSpecType) throws Throwable {
        return routes().deleteRouteByDistance(deleteRouteByDistanceSpecType);
    }

    @GetMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public RouteType getRouteById(@PathVariable Long id) throws Throwable {
        return routes().getRouteById(new ByIdSpecType().withId(id));
    }

    @PutMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public void updateRoute(@PathVariable Long id, @RequestBody RouteType newRoute) throws Throwable {
        if (newRoute.getId() != id) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Inconsistent entity id: expected " + id + " while given " + newRoute.getId());
        }
        routes().updateRoute(
                new RouteUpdateSpecType().withId(newRoute.getId())
                                         .withName(newRoute.getName())
                                         .withCreationDate(newRoute.getCreationDate())
                                         .withCoordinates(newRoute.getCoordinates())
                                         .withDiscoverability(newRoute.getDiscoverability())
                                         .withDistance(newRoute.getDistance())
                                         .withFrom(newRoute.getFrom())
                                         .withTo(newRoute.getTo())
        );
    }

    @DeleteMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public void deleteRoute(@PathVariable Long id) throws Throwable {
        routes().deleteRoute(new ByIdSpecType().withId(id));
    }
}