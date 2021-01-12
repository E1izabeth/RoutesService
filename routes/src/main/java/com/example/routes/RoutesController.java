package com.example.routes;


import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBElement;
import java.sql.SQLException;

// @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081", allowedHeaders = "*")
//@Component
// @RefreshScope
//@RestController
//@RequestMapping("")
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

    @Autowired
    public RoutesDao routesDao;

    @GetMapping(path = Urls.WEB_CHILDREN, produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getWeb(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false) Long page_size, @RequestParam(required = false) Long page_num) throws Throwable {
        // TODO
        throw new RuntimeException();
    }


    @GetMapping(path = Urls.ROUTES_ROOT, produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getRoutes(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false, name="page-size") Integer page_size, @RequestParam(required = false, name="page-num") Integer page_num) throws Throwable {
        return routesDao.queryRoutes(filter, sort, page_size, page_num);
    }

    @GetMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_GET_ROUTES_BY_NAME, produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getRouteByName(@RequestParam(name="name") String name) throws Throwable {
        return routesDao.getRouteByName(name);
    }

    @GetMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_GET_DISTANCE_SUM, produces = MediaType.APPLICATION_XML_VALUE)
    public DistanceQueryResultType getDistanceSum() throws Throwable {
        return routesDao.getDst();
    }

    @PostMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_ADD, produces = MediaType.APPLICATION_XML_VALUE)
    public RouteType addRoute(@RequestBody RouteCreationSpecType routeCreationSpecType) throws Throwable {
        return routesDao.addNewRoute(routeCreationSpecType);
    }

    @PostMapping(path = Urls.ROUTES_ROOT, params = Urls.QUERY_ACTION_PARAMETER_DELETE_BY_DISTANCE, produces = MediaType.APPLICATION_XML_VALUE)
    public OperationStatusInfoType deleteRouteByDistance(@RequestBody DeleteRouteByDistanceSpecType deleteRouteByDistanceSpecType) throws Throwable {
        return routesDao.delRouteByDst(deleteRouteByDistanceSpecType);
    }

    @GetMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public RouteType getRouteById(@PathVariable Long id) throws Throwable {
        return routesDao.getRoute(id);
    }

    @PutMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public void updateRoute(@PathVariable Long id, @RequestBody RouteType newRoute) throws Throwable {
        routesDao.updateRoute(id, newRoute);
    }

    @DeleteMapping(path = Urls.ROUTES_ROOT_ROUTE, produces = MediaType.APPLICATION_XML_VALUE)
    public void deleteRoute(@PathVariable Long id) throws Throwable {
        routesDao.deleteRoute(id);
    }
}