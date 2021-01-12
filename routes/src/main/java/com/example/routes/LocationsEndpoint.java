package com.example.routes;


import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint("locations")
public class LocationsEndpoint {
    /*

    get     /locations?...              получение массива элементов + q,f,o.
    post    /locations?action=add       добавление нового элемента,
    get     /locations/{id}             получение элемента по ИД,
    put     /locations/{id}             обновление элемента,
    delete  /locations/{id}             удаление элемента,


    * */

    private static final String NAMESPACE_URI = "main.webapp.xml";

    @Autowired
    public LocationsDao locationsDao;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LocationsQuerySpec")
    @ResponsePayload
    public LocationsQueryResultType getLocations(@RequestPayload QuerySpecType spec) throws Throwable {
        return locationsDao.queryLocations(spec.getFilter(), spec.getSortOn(), spec.getPageSize(), spec.getPageNumber());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LocationCreationSpec")
    @ResponsePayload
    public LocationType addLocation(@RequestPayload LocationCreationSpecType spec) throws Throwable {
        return locationsDao.addNewLocation(spec);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LocationQueryByIdSpec")
    @ResponsePayload
    public LocationType getLocationById(@RequestPayload ByIdSpecType spec) throws Throwable {
        return locationsDao.getLocation(spec.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LocationUpdateSpec")
    public void updateLocation(@RequestPayload LocationType location) throws Throwable {
        locationsDao.updateLocation(location.getId(), location);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LocationDeleteByIdSpec")
    public void deleteLocation(@RequestPayload ByIdSpecType spec) throws Throwable {
        locationsDao.deleteLocation(spec.getId());
    }
}