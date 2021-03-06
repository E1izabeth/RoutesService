package xml.webapp.main;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.5.0-SNAPSHOT-39ff641f3d81d8e0b9487ae8c49ac2a5bf055bc1
 * 2021-01-12T07:23:03.377+03:00
 * Generated source version: 3.5.0-SNAPSHOT
 *
 */
@WebService(targetNamespace = "main.webapp.xml", name = "LocationsEndpointPortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LocationsEndpointPortType {

    @WebMethod
    @Oneway
    public void deleteLocation(

        @WebParam(partName = "LocationDeleteByIdSpec", name = "LocationDeleteByIdSpec", targetNamespace = "main.webapp.xml")
        ByIdSpecType locationDeleteByIdSpec
    );

    @WebMethod
    @WebResult(name = "LocationsQueryResult", targetNamespace = "main.webapp.xml", partName = "LocationsQueryResult")
    public LocationsQueryResultType getLocations(

        @WebParam(partName = "LocationsQuerySpec", name = "LocationsQuerySpec", targetNamespace = "main.webapp.xml")
        QuerySpecType locationsQuerySpec
    );

    @WebMethod
    @Oneway
    public void updateLocation(

        @WebParam(partName = "LocationUpdateSpec", name = "LocationUpdateSpec", targetNamespace = "main.webapp.xml")
        LocationUpdateSpecType locationUpdateSpec
    );

    @WebMethod
    @WebResult(name = "Location", targetNamespace = "main.webapp.xml", partName = "Location")
    public LocationType addLocation(

        @WebParam(partName = "LocationCreationSpec", name = "LocationCreationSpec", targetNamespace = "main.webapp.xml")
        LocationCreationSpecType locationCreationSpec
    );

    @WebMethod
    @WebResult(name = "Location", targetNamespace = "main.webapp.xml", partName = "Location")
    public LocationType getLocationById(

        @WebParam(partName = "LocationQueryByIdSpec", name = "LocationQueryByIdSpec", targetNamespace = "main.webapp.xml")
        ByIdSpecType locationQueryByIdSpec
    );
}
