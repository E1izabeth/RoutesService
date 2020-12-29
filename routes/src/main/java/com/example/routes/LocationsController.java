package com.example.routes;


import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081", allowedHeaders = "*")
@Component
@RefreshScope
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

    @Autowired
    public LocationsDao locationsDao;

//    @GetMapping(path = Urls.WEB_CHILDREN, produces = MediaType.APPLICATION_XML_VALUE)
//    public RoutesQueryResultType getWeb(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false) Long page_size, @RequestParam(required = false) Long page_num) throws Throwable {
//        // TODO
//        throw new RuntimeException();
//    }

    @GetMapping(path = Urls.LOCATIONS_ROOT, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationsQueryResultType getLocations(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false, name="page-size") Integer page_size, @RequestParam(required = false, name="page-num") Integer page_num) throws Throwable {
        return locationsDao.queryLocations(filter, sort, page_size, page_num);
    }

    @PostMapping(path = Urls.LOCATIONS_ROOT, params = Urls.QUERY_ACTION_PARAMETER_ADD, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationType addLocation(@RequestBody LocationCreationSpecType locationCreationSpecType) throws Throwable {
        return locationsDao.addNewLocation(locationCreationSpecType);
    }

    @GetMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public LocationType getLocationById(@PathVariable Long id) throws Throwable {
        return locationsDao.getLocation(id);
    }

    @PutMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public void updateLocation(@PathVariable Long id, @RequestBody LocationType newRoute) throws Throwable {
        locationsDao.updateLocation(id, newRoute);
    }

    @DeleteMapping(path = Urls.LOCATIONS_ROOT_LOCATION, produces = MediaType.APPLICATION_XML_VALUE)
    public void deleteLocation(@PathVariable Long id) throws Throwable {
        locationsDao.deleteLocation(id);
    }
}