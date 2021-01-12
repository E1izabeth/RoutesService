package com.example.routes;


import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@Endpoint("routes")
public class RoutesEndpoint {
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

    private static final String NAMESPACE_URI = "main.webapp.xml";

    @Autowired
    public RoutesDao routesDao;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RoutesQuerySpec")
    @ResponsePayload
    public RoutesQueryResultType getRoutes(@RequestPayload QuerySpecType spec) throws Throwable {
        return routesDao.queryRoutes(spec.getFilter(), spec.getSortOn(), spec.getPageSize(), spec.getPageNumber());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RoutesQueryByNameSpec")
    @ResponsePayload
    public RoutesQueryResultType getRoutesByName(@RequestPayload RoutesQueryByNameSpecType spec) throws Throwable {
        return routesDao.getRouteByName(spec.getName());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteRouteByDistanceSpec")
    @ResponsePayload
    public OperationStatusInfoType deleteRouteByDistance(@RequestPayload DeleteRouteByDistanceSpecType spec) throws Throwable {
        return routesDao.delRouteByDst(spec);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DistanceSumQuerySpec")
    @ResponsePayload
    public DistanceQueryResultType getDistanceSum(EmptyType spec) throws Throwable {
        return routesDao.getDst();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RouteCreationSpec")
    @ResponsePayload
    public RouteType addRoute(@RequestPayload RouteCreationSpecType spec) throws Throwable {
        return routesDao.addNewRoute(spec);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RouteQueryByIdSpec")
    @ResponsePayload
    public RouteType getRouteById(@RequestPayload ByIdSpecType spec) throws Throwable {
        return routesDao.getRoute(spec.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RouteUpdateSpec")
    public void updateRoute(@RequestPayload RouteType route) throws Throwable {
        routesDao.updateRoute(route.getId(), route);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RouteDeleteByIdSpec")
    public void deleteRoute(@RequestPayload ByIdSpecType spec) throws Throwable {
        routesDao.deleteRoute(spec.getId());
    }
}