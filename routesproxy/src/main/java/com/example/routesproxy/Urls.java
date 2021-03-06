package com.example.routesproxy;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Urls {

    public static boolean isRoutesUrl(String path) {
        return path.equals(ROUTES_ROOT) || path.equals(ROUTES_ROOT + '/');
    }

    public static boolean isWebUrl(String path) {
        return path.equals(WEB_ROOT) || path.startsWith(WEB_ROOT + '/');
    }

    public static Long parseRouteUrlId(String path) {
        if (path.startsWith(ROUTES_ROOT + '/')) {
            return Utils.parseMaybeLong(path.substring(8));
        } else {
            return null;
        }
    }

    private static class HrefQueryPart {
        public final String name;
        public final String value;

        public HrefQueryPart(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return name + "=" + URLEncoder.encode(value); //, StandardCharsets.UTF_8.toString());
        }
    }

    public static final String ROUTES_ROOT = "/routes";
    public static final String LOCATIONS_ROOT = "/locations";
    public static final String ROUTES_CHILDREN = "/routes/*";
    public static final String WEB_ROOT = "/web";
    public static final String WEB_CHILDREN = "/web/*";

    public static final String ROUTES_ROOT_ROUTE = Urls.ROUTES_ROOT + "/" + "{id}";
    public static final String LOCATIONS_ROOT_LOCATION = Urls.LOCATIONS_ROOT + "/" + "{id}";

    public static final String QUERY_ACTION_ADD = "add";
    public static final String QUERY_ACTION_DELETE_BY_DISTANCE = "del-by-dst";
    public static final String QUERY_ACTION_GET_DISTANCE_SUM = "get-dst";
    public static final String QUERY_ACTION_GET_ROUTES_BY_NAME = "get-by-name";
    public static final String QUERY_PARAM_NAME = "name";

    public static final String QUERY_PARAM_ACTION = "action";
    public static final String QUERY_PARAM_FILTER = "filter";
    public static final String QUERY_PARAM_SORT = "sort";
    public static final String QUERY_PARAM_PAGE_SIZE = "page-size";
    public static final String QUERY_PARAM_PAGE_NUM = "page-num";

    public static final String QUERY_ACTION_PARAMETER_ADD = QUERY_PARAM_ACTION + "=" + QUERY_ACTION_ADD ;
    public static final String QUERY_ACTION_PARAMETER_DELETE_BY_DISTANCE = QUERY_PARAM_ACTION + "=" + QUERY_ACTION_DELETE_BY_DISTANCE;
    public static final String QUERY_ACTION_PARAMETER_GET_DISTANCE_SUM = QUERY_PARAM_ACTION +"=" +  QUERY_ACTION_GET_DISTANCE_SUM;
    public static final String QUERY_ACTION_PARAMETER_GET_ROUTES_BY_NAME = QUERY_PARAM_ACTION + "=" + QUERY_ACTION_GET_ROUTES_BY_NAME;

    public static final String ROUTES_ROOT_ACTION_ADD = Urls.ROUTES_ROOT + "?" + QUERY_ACTION_PARAMETER_ADD;
    public static final String ROUTES_ROOT_ACTION_DELETE_BY_DISTANCE = Urls.ROUTES_ROOT + "?" + QUERY_ACTION_PARAMETER_DELETE_BY_DISTANCE;
    public static final String ROUTES_ROOT_ACTION_GET_DISTANCE_SUM = Urls.ROUTES_ROOT + "?" + QUERY_ACTION_PARAMETER_GET_DISTANCE_SUM;
    public static final String ROUTES_ROOT_ACTION_GET_ROUTES_BY_NAME = Urls.ROUTES_ROOT + "?" + QUERY_ACTION_PARAMETER_GET_ROUTES_BY_NAME;

    public static final String LOCATIONS_ROOT_ACTION_ADD = Urls.LOCATIONS_ROOT + "?" + QUERY_ACTION_PARAMETER_ADD;

    private final String _prefix;

    public Urls(String prefix) {
        _prefix = prefix;
    }

    public String forRoot() {
        return _prefix;
    }

    public String forRoutes() {
        return _prefix + Urls.ROUTES_ROOT;
    }

    public String forRoutes(String filter, String sort, Long pageSize, Long pageNum) {
        String href = _prefix + Urls.ROUTES_ROOT;

        ArrayList<HrefQueryPart> parts = new ArrayList<HrefQueryPart>();
        if (filter != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_FILTER, filter));
        if (sort != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_SORT, sort));
        if (pageSize != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_PAGE_SIZE, pageSize.toString()));
        if (pageNum != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_PAGE_NUM, pageNum.toString()));

        if (parts.size() > 0) {
            href += "?" + String.join("&", parts.stream().map(p -> p.toString()).collect(Collectors.toList()));
        }
        return href;
    }

    public String forLocations() {
        return _prefix + Urls.LOCATIONS_ROOT;
    }

    public String forLocations(String filter, String sort, Long pageSize, Long pageNum) {
        String href = _prefix + Urls.LOCATIONS_ROOT;

        ArrayList<HrefQueryPart> parts = new ArrayList<HrefQueryPart>();
        if (filter != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_FILTER, filter));
        if (sort != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_SORT, sort));
        if (pageSize != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_PAGE_SIZE, pageSize.toString()));
        if (pageNum != null)
            parts.add(new HrefQueryPart(Urls.QUERY_PARAM_PAGE_NUM, pageNum.toString()));

        if (parts.size() > 0) {
            href += "?" + String.join("&", parts.stream().map(p -> p.toString()).collect(Collectors.toList()));
        }
        return href;
    }


    public String forRoutesCreateRouteAction() {
        return _prefix + Urls.ROUTES_ROOT_ACTION_ADD;
    }
    public String forLocationsCreateLocationAction() {
        return _prefix + Urls.LOCATIONS_ROOT_ACTION_ADD;
    }

    public String forRoutesDeleteByDistanceAction() {
        return _prefix + Urls.ROUTES_ROOT_ACTION_DELETE_BY_DISTANCE;
    }

    public String forRoutesGetDistanceSumAction() {
        return _prefix + Urls.ROUTES_ROOT_ACTION_GET_DISTANCE_SUM;
    }

    public String forRoutesGetByNameAction() {
        return _prefix + Urls.ROUTES_ROOT_ACTION_GET_ROUTES_BY_NAME;
    }

    public String forRoute(long id) {
        return _prefix + Urls.ROUTES_ROOT + "/" + id;
    }

    public String forLocation(long id) {
        return _prefix + Urls.LOCATIONS_ROOT + "/" + id;
    }

}
