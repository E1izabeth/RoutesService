
package xml.webapp.main;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.5.0-SNAPSHOT-39ff641f3d81d8e0b9487ae8c49ac2a5bf055bc1
 * 2021-01-12T07:22:59.352+03:00
 * Generated source version: 3.5.0-SNAPSHOT
 *
 */
public final class RoutesEndpointPortType_RoutesEndpointPort_Client {

    private static final QName SERVICE_NAME = new QName("main.webapp.xml", "RoutesEndpointService");

    private RoutesEndpointPortType_RoutesEndpointPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RoutesEndpointService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        RoutesEndpointService ss = new RoutesEndpointService(wsdlURL, SERVICE_NAME);
        RoutesEndpointPortType port = ss.getRoutesEndpointPort();

        {
        System.out.println("Invoking updateRoute...");
        xml.webapp.main.RouteUpdateSpecType _updateRoute_routeUpdateSpec = new xml.webapp.main.RouteUpdateSpecType();
        port.updateRoute(_updateRoute_routeUpdateSpec);


        }
        {
        System.out.println("Invoking getRoutesByName...");
        xml.webapp.main.RoutesQueryByNameSpecType _getRoutesByName_routesQueryByNameSpec = new xml.webapp.main.RoutesQueryByNameSpecType();
        xml.webapp.main.RoutesQueryResultType _getRoutesByName__return = port.getRoutesByName(_getRoutesByName_routesQueryByNameSpec);
        System.out.println("getRoutesByName.result=" + _getRoutesByName__return);


        }
        {
        System.out.println("Invoking getRoutes...");
        xml.webapp.main.QuerySpecType _getRoutes_routesQuerySpec = new xml.webapp.main.QuerySpecType();
        xml.webapp.main.RoutesQueryResultType _getRoutes__return = port.getRoutes(_getRoutes_routesQuerySpec);
        System.out.println("getRoutes.result=" + _getRoutes__return);


        }
        {
        System.out.println("Invoking deleteRouteByDistance...");
        xml.webapp.main.DeleteRouteByDistanceSpecType _deleteRouteByDistance_deleteRouteByDistanceSpec = new xml.webapp.main.DeleteRouteByDistanceSpecType();
        xml.webapp.main.OperationStatusInfoType _deleteRouteByDistance__return = port.deleteRouteByDistance(_deleteRouteByDistance_deleteRouteByDistanceSpec);
        System.out.println("deleteRouteByDistance.result=" + _deleteRouteByDistance__return);


        }
        {
        System.out.println("Invoking getDistanceSum...");
        xml.webapp.main.EmptyType _getDistanceSum_distanceSumQuerySpec = new xml.webapp.main.EmptyType();
        xml.webapp.main.DistanceQueryResultType _getDistanceSum__return = port.getDistanceSum(_getDistanceSum_distanceSumQuerySpec);
        System.out.println("getDistanceSum.result=" + _getDistanceSum__return);


        }
        {
        System.out.println("Invoking addRoute...");
        xml.webapp.main.RouteCreationSpecType _addRoute_routeCreationSpec = new xml.webapp.main.RouteCreationSpecType();
        xml.webapp.main.RouteType _addRoute__return = port.addRoute(_addRoute_routeCreationSpec);
        System.out.println("addRoute.result=" + _addRoute__return);


        }
        {
        System.out.println("Invoking getRouteById...");
        xml.webapp.main.ByIdSpecType _getRouteById_routeQueryByIdSpec = new xml.webapp.main.ByIdSpecType();
        xml.webapp.main.RouteType _getRouteById__return = port.getRouteById(_getRouteById_routeQueryByIdSpec);
        System.out.println("getRouteById.result=" + _getRouteById__return);


        }
        {
        System.out.println("Invoking deleteRoute...");
        xml.webapp.main.ByIdSpecType _deleteRoute_routeDeleteByIdSpec = new xml.webapp.main.ByIdSpecType();
        port.deleteRoute(_deleteRoute_routeDeleteByIdSpec);


        }

        System.exit(0);
    }

}