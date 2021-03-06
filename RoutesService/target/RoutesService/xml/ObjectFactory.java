//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.17 at 03:43:35 PM MSK 
//


package main.webapp.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the main.webapp.xml package. 
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
    private final static QName _DeleteRouteByDistanceSpec_QNAME = new QName("main.webapp.xml", "DeleteRouteByDistanceSpec");
    private final static QName _Route_QNAME = new QName("main.webapp.xml", "Route");
    private final static QName _RoutesQueryResult_QNAME = new QName("main.webapp.xml", "RoutesQueryResult");
    private final static QName _DistanceSumQueryResult_QNAME = new QName("main.webapp.xml", "DistanceSumQueryResult");
    private final static QName _OperationStatusInfo_QNAME = new QName("main.webapp.xml", "OperationStatusInfo");
    private final static QName _RouteCreationSpec_QNAME = new QName("main.webapp.xml", "RouteCreationSpec");
    private final static QName _ErrorInfo_QNAME = new QName("main.webapp.xml", "ErrorInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: main.webapp.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DistanceQueryResultType }
     * 
     */
    public DistanceQueryResultType createDistanceQueryResultType() {
        return new DistanceQueryResultType();
    }

    /**
     * Create an instance of {@link OperationStatusInfoType }
     * 
     */
    public OperationStatusInfoType createOperationStatusInfoType() {
        return new OperationStatusInfoType();
    }

    /**
     * Create an instance of {@link RouteCreationSpecType }
     * 
     */
    public RouteCreationSpecType createRouteCreationSpecType() {
        return new RouteCreationSpecType();
    }

    /**
     * Create an instance of {@link ErrorInfoType }
     * 
     */
    public ErrorInfoType createErrorInfoType() {
        return new ErrorInfoType();
    }

    /**
     * Create an instance of {@link RouteServiceType }
     * 
     */
    public RouteServiceType createRouteServiceType() {
        return new RouteServiceType();
    }

    /**
     * Create an instance of {@link DeleteRouteByDistanceSpecType }
     * 
     */
    public DeleteRouteByDistanceSpecType createDeleteRouteByDistanceSpecType() {
        return new DeleteRouteByDistanceSpecType();
    }

    /**
     * Create an instance of {@link RouteType }
     * 
     */
    public RouteType createRouteType() {
        return new RouteType();
    }

    /**
     * Create an instance of {@link RoutesQueryResultType }
     * 
     */
    public RoutesQueryResultType createRoutesQueryResultType() {
        return new RoutesQueryResultType();
    }

    /**
     * Create an instance of {@link ActionType }
     * 
     */
    public ActionType createActionType() {
        return new ActionType();
    }

    /**
     * Create an instance of {@link ActionsListType }
     * 
     */
    public ActionsListType createActionsListType() {
        return new ActionsListType();
    }

    /**
     * Create an instance of {@link DiscoverabilityType }
     * 
     */
    public DiscoverabilityType createDiscoverabilityType() {
        return new DiscoverabilityType();
    }

    /**
     * Create an instance of {@link LocalDate }
     * 
     */
    public LocalDate createLocalDate() {
        return new LocalDate();
    }

    /**
     * Create an instance of {@link CoordinatesType }
     * 
     */
    public CoordinatesType createCoordinatesType() {
        return new CoordinatesType();
    }

    /**
     * Create an instance of {@link RoutesListType }
     * 
     */
    public RoutesListType createRoutesListType() {
        return new RoutesListType();
    }

    /**
     * Create an instance of {@link LinksListType }
     * 
     */
    public LinksListType createLinksListType() {
        return new LinksListType();
    }

    /**
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link LocationType }
     * 
     */
    public LocationType createLocationType() {
        return new LocationType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRouteByDistanceSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "DeleteRouteByDistanceSpec")
    public JAXBElement<DeleteRouteByDistanceSpecType> createDeleteRouteByDistanceSpec(DeleteRouteByDistanceSpecType value) {
        return new JAXBElement<DeleteRouteByDistanceSpecType>(_DeleteRouteByDistanceSpec_QNAME, DeleteRouteByDistanceSpecType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DistanceQueryResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "DistanceSumQueryResult")
    public JAXBElement<DistanceQueryResultType> createDistanceSumQueryResult(DistanceQueryResultType value) {
        return new JAXBElement<DistanceQueryResultType>(_DistanceSumQueryResult_QNAME, DistanceQueryResultType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteCreationSpecType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "main.webapp.xml", name = "RouteCreationSpec")
    public JAXBElement<RouteCreationSpecType> createRouteCreationSpec(RouteCreationSpecType value) {
        return new JAXBElement<RouteCreationSpecType>(_RouteCreationSpec_QNAME, RouteCreationSpecType.class, null, value);
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
