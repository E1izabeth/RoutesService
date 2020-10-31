package main.webapp;

import main.webapp.xml.ErrorInfoType;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "myRouteServlet",
    urlPatterns = {
        Urls.ROUTES_ROOT,
        Urls.ROUTES_CHILDREN,
        Urls.WEB_ROOT,
        Urls.WEB_CHILDREN
    }
)
public class RouteServlet extends HttpServlet {
    /*

    get     /routes?...              получение массива элементов + q,f,o.
    post    /routes?action=add       добавление нового элемента,

    post    /routes?action=del-by-dst  Удалить один (любой) объект, значение поля distance которого эквивалентно заданному.
    post    /routes?action=get-dst    Рассчитать сумму значений поля distance для всех объектов.
    post    /routes?action=get-by-name Вернуть массив объектов, значение поля name которых содержит заданную подстроку.

    get     /routes/{id}             получение элемента по ИД,
    put     /routes/{id}             обновление элемента,
    delete  /routes/{id}             удаление элемента,


    * */

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    private String getRequestPath(HttpServletRequest req) {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        return path;
    }

    private RoutesContext openContext(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // StringBuffer url = request.getRequestURL();
        String baseUrl = getBaseUrl(request);

        return new RoutesContext(baseUrl, request, response);
    }

    private void prepareError(HttpServletResponse resp, Throwable ex) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(buff));
        String info = Utils.readAsStringTillEnd(new ByteArrayInputStream(buff.toByteArray()));

        Logger log = Logger.getLogger(getClass().getName());
        log.severe(info);

        ErrorInfoType errorInfo = new ErrorInfoType();
        errorInfo.setType(ex.getClass().getTypeName());
        errorInfo.setMessage(ex.getMessage());
        errorInfo.setStackTrace(info);

        RestApiException apiEx = Utils.is(RestApiException.class, ex, null);
        if (apiEx != null) {
            resp.setStatus(apiEx.statusCode);
        } else {
            resp.setStatus(500);
        }

        resp.setContentType("text/xml;charset=UTF-8");

        try {
            PrintWriter out = resp.getWriter();
            new MySerializer().serialize(errorInfo, out);
            out.close();
        } catch (Throwable ex2) {
            log.warning("Failed to send error info");
        }
    }

    private void prepareRoutesRootAllowedMethods(HttpServletResponse resp) {
        resp.setHeader("Allow", "GET, POST");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            try (RoutesContext ctx = this.openContext(req, resp)) {
                String path = this.getRequestPath(req);

                if (Urls.isWebUrl(path)) {
                    ctx.handleWebRequest(getBaseUrl(req), this.getRequestPath(req));
                } else if (Urls.isRoutesUrl(path)) {
                    this.prepareRoutesRootAllowedMethods(resp);
                    throw new RestApiException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Collection itself cannot be deleted");
                } else {
                    Long id = Urls.parseRouteUrlId(path);
                    if (id != null) {
                        ctx.deleteRoute(id);
                    } else {
                        throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No resource at " + path);
                    }

                }
            }
        } catch (Throwable ex) {
            this.prepareError(resp, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            try (RoutesContext ctx = this.openContext(req, resp)) {
                String path = this.getRequestPath(req);

                if (Urls.isWebUrl(path)) {
                    ctx.handleWebRequest(getBaseUrl(req), this.getRequestPath(req));
                } else if (Urls.isRoutesUrl(path)) {
                    this.prepareRoutesRootAllowedMethods(resp);
                    throw new RestApiException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Collection itself cannot be updated");
                } else {
                    Long id = Urls.parseRouteUrlId(path);
                    if (id != null) {
                        ctx.updateRoute(id);
                    } else {
                        throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No resource at " + path);
                    }
                }
            }
        } catch (Throwable ex) {
            this.prepareError(resp, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try (RoutesContext ctx = this.openContext(request, response)) {
                String path = this.getRequestPath(request);
                if (Urls.isWebUrl(path)) {
                    ctx.handleWebRequest(getBaseUrl(request), this.getRequestPath(request));
                } else if (Urls.isRoutesUrl(path)) {
                    String actionStr = request.getParameter(Urls.QUERY_PARAM_ACTION);
                    String nameStr = request.getParameter(Urls.QUERY_PARAM_NAME);
                    if (actionStr != null){
                        switch (actionStr) {
                            case Urls.QUERY_ACTION_GET_DISTANCE_SUM: ctx.getDst(); break;
                            case Urls.QUERY_ACTION_GET_ROUTES_BY_NAME: {
                                if (nameStr == null) {
                                    throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                                } else {
                                    ctx.getRouteByName(nameStr);
                                }
                            } break;
                            default:
                                throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid action spec");
                        }
                    } else {
                        String filterStr = request.getParameter(Urls.QUERY_PARAM_FILTER);
                        String sortStr = request.getParameter(Urls.QUERY_PARAM_SORT);
                        Long pageSize = Utils.parseLongOrNothing(request.getParameter(Urls.QUERY_PARAM_PAGE_SIZE));
                        Long pageNum = Utils.parseLongOrNothing(request.getParameter(Urls.QUERY_PARAM_PAGE_NUM));
                        ctx.queryRoutes(filterStr, sortStr, pageSize, pageNum);
                    }
                } else {
                    Long id = Urls.parseRouteUrlId(path);
                    if (id != null) {
                        ctx.getRoute(id);
                    } else {
                        throw new RestApiException(HttpServletResponse.SC_NOT_FOUND, "No resource at " + path);
                    }
                }
            }
        } catch (Throwable ex) {
            this.prepareError(response, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionStr = req.getParameter(Urls.QUERY_PARAM_ACTION);
        if (actionStr == null)
            actionStr = "";

        try {
            try (RoutesContext ctx = this.openContext(req, resp)) {
                String path = this.getRequestPath(req);
                if (Urls.isWebUrl(path)) {
                    ctx.handleWebRequest(getBaseUrl(req), this.getRequestPath(req));
                } else if (Urls.isRoutesUrl(path)) {
                    switch (actionStr) {
                        case Urls.QUERY_ACTION_ADD: ctx.addNewRoute(); break;
                        case Urls.QUERY_ACTION_DELETE_BY_DISTANCE: ctx.delRouteByDst(); break;
                        default:
                            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid action spec");
                    }
                } else {
                    throw new RestApiException(HttpServletResponse.SC_FORBIDDEN, "Post requests are not allowed here");
                }
            }
        } catch (Throwable ex) {
            this.prepareError(resp, ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Expose-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
        response.setHeader("Access-Control-Max-Age", "3600");

        super.service(req, res);
    }
}