package com.example.routes;

import com.example.routes.db.LocationDbEntity;
import com.example.routes.db.LocationsRepository;
import com.example.routes.xml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@Component
public class LocationsDao extends MyDao {

    @Autowired
    public LocationsRepository _locations;

    public LocationsDao() throws SQLException {
        super();
    }

    public LocationsQueryResultType queryLocations(String filterStr, String sortStr, Integer pageSize, Integer pageNum) throws Throwable {
        EntitiesSelector<LocationDbEntity> selector = new MyDao.EntitiesSelector<LocationDbEntity>(_locations::findAll);
        FilteringQueryResult<LocationDbEntity> result = selector.selectEntities(filterStr, sortStr, pageSize, pageNum);

        LocationsQueryResultType qr = _xmlBuilder.translateLocationsQueryResultEntity(result.data, filterStr, sortStr, result.pageNum, result.pageSize, result.pagesCount, result.totalCount);
        return qr;
    }

    private LocationDbEntity findLocation(Long id) throws Throwable {
        if (id == null) {
            return null;
        }

        Optional<LocationDbEntity> maybeLocation = _locations.findById(id);
        if (maybeLocation.isPresent()) {
            LocationDbEntity route = maybeLocation.get();
            return route;
        } else {
            return null;
        }
    }

    public LocationType getLocation(Long id) throws Throwable {
        LocationDbEntity location = this.findLocation(id);
        if (location == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            LocationType result = _xmlBuilder.translateLocationEntity(location);
            return result;
        }
    }

    public void deleteLocation(Long id) throws Throwable {
        Optional<Integer> usages = _locations.countUsagesById(id);
        if (usages.isPresent()) {
            if (usages.get() > 0) {
                throw new RestApiException(HttpServletResponse.SC_CONFLICT, "Route " + id + " is in use");
            } else {
                _locations.deleteById(id);
            }
        } else {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        }
    }

    public void updateLocation(Long id, LocationType newLocation) throws Throwable {
        LocationDbEntity location = this.findLocation(id);
        OperationStatusInfoType result = new OperationStatusInfoType();

        if (location == null) {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No route having id " + id);
        } else {
            if (newLocation.getId() != location.id) {
                throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Inconsistent entity id: expected " + location.id + " while given " + newLocation.getId());
            }
            updateLocationEntity(location, newLocation);
            _locations.save(location);
        }
    }

    private void updateLocationEntity(LocationDbEntity location, LocationType newLocation) {
        location.x = newLocation.getX();
        location.y = newLocation.getY();
        location.z = newLocation.getZ();
    }
    public LocationType addNewLocation(LocationCreationSpecType newLocationSpec) throws Throwable {
        LocationDbEntity entity = createLocationFromSpec(newLocationSpec);
        _locations.saveAndFlush(entity);
        LocationType newLocation = _xmlBuilder.translateLocationEntity(entity);
        //TODO _response.setStatus(HttpServletResponse.SC_CREATED);
        return newLocation;
    }

    private LocationDbEntity createLocationFromSpec(LocationCreationSpecType spec) {
        LocationDbEntity obj = new LocationDbEntity();
        obj.x = spec.getX();
        obj.y = spec.getY();
        obj.z = spec.getZ();
        return obj;
    }
}
