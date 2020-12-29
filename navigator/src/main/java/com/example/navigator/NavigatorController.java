package com.example.navigator;


import com.example.navigator.extras.Utils;
import com.example.navigator.xml.*;
import org.springframework.http.MediaType;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081")
@RestController
@RequestMapping("/navigator")
public class NavigatorController {
    /*

    get /route/{id-from}/{id-to}/{shortest} : найти самый короткий (или длинный) маршрут между указанными локациями
    get /routes/{id-from}/{id-to}/{order-by} : найти все маршруты между указанными локациями, отсортировать список по заданному параметру

    */

    final String _routeSvcRoot = System.getenv("NAV_ROUTE_SVC_ROOT"); // = "https://localhost:8586";
    final Logger _log;

    public NavigatorController() {
        _log = Logger.getLogger(getClass().getName());
        _log.severe(getClass().getName() + " INSTANTIATED");
        _log.warning("NAV_ROUTE_SVC_ROOT = " + _routeSvcRoot);
    }

    private String makeQueryUrl(long locFromId, long locToId, String sortKey, int limit) {
        return _routeSvcRoot + "/routes?filter=fromId%3D" + locFromId + "%20and%20toId%3D" + locToId + "&page-size=" + limit + "&sort=" + sortKey;
    }

    private <R> R invoke(Class<R> resultType, String url) throws IOException, JAXBException, SAXException {
        _log.warning("Submitting request to " + url);

        URL target = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)target.openConnection();
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");

        InputStream reader;
        int responseCode = connection.getResponseCode();
        if (200 <= responseCode && responseCode <= 399) {
            reader = connection.getInputStream();
        } else {
            reader = connection.getErrorStream();
        }

        String resultString = Utils.readAsStringTillEnd(reader);
        MySerializer serializer = new MySerializer();

        _log.warning("Response is of code " + responseCode + ": \r\n" + resultString);

        if (responseCode == 200) {
            R result = serializer.deserialize(resultType, new StringReader(resultString));
            return result;
        } else {
            ErrorInfoType errorInfo;
            try { errorInfo = serializer.deserialize(ErrorInfoType.class, new StringReader(resultString)); }
            catch (Throwable ex) { errorInfo = null; }

            if (errorInfo != null) {
                throw new RestApiException(HttpServletResponse.SC_BAD_GATEWAY, "External service fail: " + errorInfo.getMessage(), errorInfo.getStackTrace());
            } else {
                throw new RestApiException(HttpServletResponse.SC_BAD_GATEWAY, resultString);
            }
        }
    }

    private String makeSortKey(String shortest) {
        if (shortest.equals("shortest")) {
            return "distance";
        } else if (shortest.equals("longest")) {
            return "~distance";
        } else {
            throw new RestApiException(400, "Wrong argument");
        }
    }

    @GetMapping(path = "/route/{id-from}/{id-to}/{shortest}", produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getShortestRoute(@PathVariable(name = "id-from") Long id_from, @PathVariable(name = "id-to") Long id_to, @PathVariable(name = "shortest") String shortest) throws Throwable {

        RoutesQueryResultType allRoutes = invoke(RoutesQueryResultType.class, this.makeQueryUrl(id_from, id_to, this.makeSortKey(shortest), 1));
        List<RouteType> routes = allRoutes.getRoutes().getRoute();

        if (routes.size() > 0) {
            RoutesListType resultRoutes = new RoutesListType();
            resultRoutes.getRoute().add(routes.get(0));

            RoutesQueryResultType result = new RoutesQueryResultType();
            result.setRoutes(resultRoutes);
            result.setPageNumber(0);
            result.setPagesCount(1);
            result.setPageSize(1);
            result.setTotalCount(1);

            return result;
        } else {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No such routes");
        }
    }

    @GetMapping(path = "/routes/{id-from}/{id-to}/{order-by}", produces = MediaType.APPLICATION_XML_VALUE)
    public RoutesQueryResultType getOrderedRoutes(@PathVariable(name = "id-from") Long id_from, @PathVariable(name = "id-to") Long id_to, @PathVariable(name = "order-by") String order_by) throws Throwable {
        if (order_by == null || order_by.trim().length() == 0)
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Missing order-by spec2");

        RoutesQueryResultType allRoutes = invoke(RoutesQueryResultType.class, makeQueryUrl(id_from, id_to, order_by, Integer.MAX_VALUE));

        if (allRoutes.getTotalCount() > 0) {
            long resultsCount = allRoutes.getRoutes().getRoute().size();
            RoutesQueryResultType result = new RoutesQueryResultType();
            result.setRoutes(allRoutes.getRoutes());
            result.setPageNumber(0);
            result.setPagesCount(1);
            result.setPageSize(resultsCount);
            result.setTotalCount(resultsCount);
            return result;
        } else {
            throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No such routes");
        }
    }
}