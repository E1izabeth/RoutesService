package com.example.routesproxy;


import org.springframework.stereotype.Component;
import xml.webapp.main.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xml.webapp.main.RoutesEndpointPortType;
import xml.webapp.main.RoutesEndpointService;

// @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081", allowedHeaders = "*")
@Component
// @RefreshScope
@RestController
@RequestMapping("")
public class LocationsController {
    /*

    get     /locations?...              получение массива элементов + q,f,o.
    post    /locations?action=add       добавление нового элемента,
    get     /locations/{id}             получение элемента по ИД,
    put     /locations/{id}             обновление элемента,
    delete  /locations/{id}             удаление элемента,


    * */


    private LocationsEndpointPortType locations() {
        LocationsEndpointService svc = new LocationsEndpointService();
        LocationsEndpointPortType port = svc.getLocationsEndpointPort();
        return port;
    }

    @GetMapping(path = Urls.LOCATIONS_ROOT, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationsQueryResultType getLocations(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false, name="page-size") Integer page_size, @RequestParam(required = false, name="page-num") Integer page_num) throws Throwable {
        return locations().getLocations(new QuerySpecType().withFilter(filter).withSortOn(sort).withPageNumber(page_num).withPageSize(page_size));
    }

    @PostMapping(path = Urls.LOCATIONS_ROOT, params = Urls.QUERY_ACTION_PARAMETER_ADD, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationType addLocation(@RequestBody LocationCreationSpecType locationCreationSpecType) throws Throwable {
        return locations().addLocation(locationCreationSpecType);
    }

    @GetMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationType getLocationById(@PathVariable Long id) throws Throwable {
        return locations().getLocationById(new ByIdSpecType().withId(id));
    }

    @PutMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public void updateLocation(@PathVariable Long id, @RequestBody LocationType newRoute) throws Throwable {
        locations().updateLocation(new LocationUpdateSpecType().withId(id).withX(newRoute.getX()).withY(newRoute.getY()).withZ(newRoute.getZ()));
    }

    @DeleteMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public void deleteLocation(@PathVariable Long id) throws Throwable {
        locations().deleteLocation(new ByIdSpecType().withId(id));
    }
}