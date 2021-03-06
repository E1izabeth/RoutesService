
package xml.webapp.main;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xml.webapp.main package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RouteService_QNAME = new QName("main.webapp.xml", "RouteService");
    private final static QName _LocationCreationSpec_QNAME = new QName("main.webapp.xml", "LocationCreationSpec");
    private final static QName _Route_QNAME = new QName("main.webapp.xml", "Route");
    private final static QName _RoutesQueryResult_QNAME = new QName("main.webapp.xml", "RoutesQueryResult");
    private final static QName _Location_QNAME = new QName("main.webapp.xml", "Location");
    private final static QName _RoutesQuerySpec_QNAME = new QName("main.webapp.xml", "RoutesQuerySpec");
    private final static QName _RouteQueryByIdSpec_QNAME = new QName("main.webapp.xml", "RouteQueryByIdSpec");
    private final static QName _LocationService_QNAME = new QName("main.webapp.xml", "LocationService");
    private final static QName _RouteUpdateSpec_QNAME = new QName("main.webapp.xml", "RouteUpdateSpec");
    private final static QName _RouteCreationSpec_QNAME = new QName("main.webapp.xml", "RouteCreationSpec");
    private final static QName _DistanceSumQuerySpec_QNAME = new QName("main.webapp.xml", "DistanceSumQuerySpec");
    private final static QName _RouteDeleteByIdSpec_QNAME = new QName("main.webapp.xml", "RouteDeleteByIdSpec");
    private final static QName _DeleteRouteByDistanceSpec_QNAME = new QName("main.webapp.xml", "DeleteRouteByDistanceSpec");
    private final static QName _LocationDeleteByIdSpec_QNAME = new QName("main.webapp.xml", "LocationDeleteByIdSpec");
    private final static QName _RoutesQueryByNameSpec_QNAME = new QName("main.webapp.xml", "RoutesQueryByNameSpec");
    private final static QName _LocationUpdateSpec_QNAME = new QName("main.webapp.xml", "LocationUpdateSpec");
    private final static QName _LocationsQueryResult_QNAME = new QName("main.webapp.xml", "LocationsQueryResult");
    private final static QName _DistanceSumQueryResult_QNAME = new QName("main.webapp.xml", "DistanceSumQueryResult");
    private final static QName _LocationsQuerySpec_QNAME = new QName("main.webapp.xml", "LocationsQuerySpec");
    private final static QName _LocationQueryByIdSpec_QNAME = new QName("main.webapp.xml", "LocationQueryByIdSpec");
    private final static QName _OperationStatusInfo_QNAME = new QName("main.webapp.xml", "OperationStatusInfo");
    private final static QName _ErrorInfo_QNAME = new QName("main.webapp.xml", "ErrorInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xml.webapp.main
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ActionsListType }
     * 
     */
    public ActionsListType createActionsListType() {
        return new ActionsListType();
    }

    /**
     * Create an instance of {@link RoutesQueryByNameSpecType }
     * 
     */
    public RoutesQueryByNameSpecType createRoutesQueryByNameSpecType() {
        return new RoutesQueryByNameSpecType();
    }

    /**
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link DistanceQueryResultType }
     * 
     */
    public DistanceQueryResultType createDistanceQueryResultType() {
        return new DistanceQueryResultType();
    }

    /**
     * Create an instance of {@link LocationsQueryResultType }
     * 
     */
    public LocationsQueryResultType createLocationsQueryResultType() {
        return new LocationsQueryResultType();
    }

    /**
     * Create an instance of {@link RoutesQueryResultType }
     * 
     */
    public RoutesQueryResultType createRoutesQueryResultType() {
        return new RoutesQueryResultType();
    }

    /**
     * Create an instance of {@link QuerySpecType }
     * 
     */
    public QuerySpecType createQuerySpecType() {
        return new QuerySpecType();
    }

    /**
     * Create an instance of {@link RouteCreationSpecType }
     * 
     */
    public RouteCreationSpecType createRouteCreationSpecType() {
        return new RouteCreationSpecType();
    }

    /**
     * Create an instance of {@link LocationServiceType }
     * 
     */
    public LocationServiceType createLocationServiceType() {
        return new LocationServiceType();
    }

    /**
     * Create an instance of {@link LocalDate }
     * 
     */
    public LocalDate createLocalDate() {
        return new LocalDate();
    }

    /**
     * Create an instance of {@link RouteServiceType }
     * 
     */
    public RouteServiceType createRouteServiceType() {
        return new RouteServiceType();
    }

    /**
     * Create an instance of {@link RouteType }
     * 
     */
    public RouteType createRouteType() {
        return new RouteType();
    }

    /**
     * Create an instance of {@link DeleteRouteByDistanceSpecType }
     * 
     */
    public DeleteRouteByDistanceSpecType createDeleteRouteByDistanceSpecType() {
        return new DeleteRouteByDistanceSpecType();
    }

    /**
     * Create an instance of {@link RoutesListType }
     * 
     */
    public RoutesListType createRoutesListType() {
        return new RoutesListType();
    }

    /**
     * Create an instance of {@link OperationStatusInfoType }
     * 
     */
    public OperationStatusInfoType createOperationStatusInfoType() {
        return new OperationStatusInfoType();
    }

    /**
     * Create an instance of {@link ActionType }
     * 
     */
    public ActionType createActionType() {
        return new ActionType();
    }

    /**
     * Create an instance of {@link LocationsListType }
     * 
     */
    public LocationsListType createLocationsListType() {
        return new LocationsListType();
    }

    /**
     * Create an instance of {@link DiscoverabilityType }
     * 
     */
    public DiscoverabilityType createDiscoverabilityType() {
        return new DiscoverabilityType();
    }

    /**
     * Create an instance of {@link ErrorInfoType }
     * 
     */
    public ErrorInfoType createErrorInfoType() {
        return new ErrorInfoType();
    }

    /**
     * Create an instance of {@link RouteUpdateSpecType }
     * 
     */
    public RouteUpdateSpecType createRouteUpdateSpecType() {
        return new RouteUpdateSpecType();
    }

    /**
     * Create an instance of {@link ByIdSpecType }
     * 
     */
    public ByIdSpecType createByIdSpecType() {
        return new ByIdSpecType();
    }

    /**
     * Create an instance of {@link LocationCreationSpecType }
     * 
     */
    public LocationCreationSpecType createLocationCreationSpecType() {
        return new LocationCreationSpecType();
    }

    /**
     * Create an instance of {@link LocationUpdateSpecType }
     * 
     */
    public LocationUpdateSpecType createLocationUpdateSpecType() {
        return new LocationUpdateSpecType();
    }

    /**
     * Create an instance of {@link EmptyType }
     * 
     */
    public EmptyType createEmptyType() {
        return new EmptyType();
    }

    /**
     * Create an instance of {@link LinksListType }
     * 
     */
    public LinksListType createLinksListType() {
        return new LinksListType();
    }

    /**
     * Create an instance of {@link LocationType }
     * 
     */
    public LocationType createLocationType() {
        return new LocationType();
    }

    /**
     * Create an instance of {@link CoordinatesType }
     * 
     */
    public CoordinatesType createCoordinatesType() {
        return new CoordinatesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteServiceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteService")
    public JAXBElement<RouteServiceType> createRouteService(RouteServiceType value) {
        return new JAXBElement<RouteServiceType>(_RouteService_QNAME, RouteServiceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationCreationSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationCreationSpec")
    public JAXBElement<LocationCreationSpecType> createLocationCreationSpec(LocationCreationSpecType value) {
        return new JAXBElement<LocationCreationSpecType>(_LocationCreationSpec_QNAME, LocationCreationSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "Route")
    public JAXBElement<RouteType> createRoute(RouteType value) {
        return new JAXBElement<RouteType>(_Route_QNAME, RouteType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoutesQueryResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RoutesQueryResult")
    public JAXBElement<RoutesQueryResultType> createRoutesQueryResult(RoutesQueryResultType value) {
        return new JAXBElement<RoutesQueryResultType>(_RoutesQueryResult_QNAME, RoutesQueryResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "Location")
    public JAXBElement<LocationType> createLocation(LocationType value) {
        return new JAXBElement<LocationType>(_Location_QNAME, LocationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RoutesQuerySpec")
    public JAXBElement<QuerySpecType> createRoutesQuerySpec(QuerySpecType value) {
        return new JAXBElement<QuerySpecType>(_RoutesQuerySpec_QNAME, QuerySpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ByIdSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteQueryByIdSpec")
    public JAXBElement<ByIdSpecType> createRouteQueryByIdSpec(ByIdSpecType value) {
        return new JAXBElement<ByIdSpecType>(_RouteQueryByIdSpec_QNAME, ByIdSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationServiceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationService")
    public JAXBElement<LocationServiceType> createLocationService(LocationServiceType value) {
        return new JAXBElement<LocationServiceType>(_LocationService_QNAME, LocationServiceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteUpdateSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteUpdateSpec")
    public JAXBElement<RouteUpdateSpecType> createRouteUpdateSpec(RouteUpdateSpecType value) {
        return new JAXBElement<RouteUpdateSpecType>(_RouteUpdateSpec_QNAME, RouteUpdateSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteCreationSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteCreationSpec")
    public JAXBElement<RouteCreationSpecType> createRouteCreationSpec(RouteCreationSpecType value) {
        return new JAXBElement<RouteCreationSpecType>(_RouteCreationSpec_QNAME, RouteCreationSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "DistanceSumQuerySpec")
    public JAXBElement<EmptyType> createDistanceSumQuerySpec(EmptyType value) {
        return new JAXBElement<EmptyType>(_DistanceSumQuerySpec_QNAME, EmptyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ByIdSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteDeleteByIdSpec")
    public JAXBElement<ByIdSpecType> createRouteDeleteByIdSpec(ByIdSpecType value) {
        return new JAXBElement<ByIdSpecType>(_RouteDeleteByIdSpec_QNAME, ByIdSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRouteByDistanceSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "DeleteRouteByDistanceSpec")
    public JAXBElement<DeleteRouteByDistanceSpecType> createDeleteRouteByDistanceSpec(DeleteRouteByDistanceSpecType value) {
        return new JAXBElement<DeleteRouteByDistanceSpecType>(_DeleteRouteByDistanceSpec_QNAME, DeleteRouteByDistanceSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ByIdSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationDeleteByIdSpec")
    public JAXBElement<ByIdSpecType> createLocationDeleteByIdSpec(ByIdSpecType value) {
        return new JAXBElement<ByIdSpecType>(_LocationDeleteByIdSpec_QNAME, ByIdSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoutesQueryByNameSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RoutesQueryByNameSpec")
    public JAXBElement<RoutesQueryByNameSpecType> createRoutesQueryByNameSpec(RoutesQueryByNameSpecType value) {
        return new JAXBElement<RoutesQueryByNameSpecType>(_RoutesQueryByNameSpec_QNAME, RoutesQueryByNameSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationUpdateSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationUpdateSpec")
    public JAXBElement<LocationUpdateSpecType> createLocationUpdateSpec(LocationUpdateSpecType value) {
        return new JAXBElement<LocationUpdateSpecType>(_LocationUpdateSpec_QNAME, LocationUpdateSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationsQueryResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationsQueryResult")
    public JAXBElement<LocationsQueryResultType> createLocationsQueryResult(LocationsQueryResultType value) {
        return new JAXBElement<LocationsQueryResultType>(_LocationsQueryResult_QNAME, LocationsQueryResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DistanceQueryResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "DistanceSumQueryResult")
    public JAXBElement<DistanceQueryResultType> createDistanceSumQueryResult(DistanceQueryResultType value) {
        return new JAXBElement<DistanceQueryResultType>(_DistanceSumQueryResult_QNAME, DistanceQueryResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationsQuerySpec")
    public JAXBElement<QuerySpecType> createLocationsQuerySpec(QuerySpecType value) {
        return new JAXBElement<QuerySpecType>(_LocationsQuerySpec_QNAME, QuerySpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ByIdSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "LocationQueryByIdSpec")
    public JAXBElement<ByIdSpecType> createLocationQueryByIdSpec(ByIdSpecType value) {
        return new JAXBElement<ByIdSpecType>(_LocationQueryByIdSpec_QNAME, ByIdSpecType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationStatusInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "OperationStatusInfo")
    public JAXBElement<OperationStatusInfoType> createOperationStatusInfo(OperationStatusInfoType value) {
        return new JAXBElement<OperationStatusInfoType>(_OperationStatusInfo_QNAME, OperationStatusInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "ErrorInfo")
    public JAXBElement<ErrorInfoType> createErrorInfo(ErrorInfoType value) {
        return new JAXBElement<ErrorInfoType>(_ErrorInfo_QNAME, ErrorInfoType.class, null, value);
    }

}
