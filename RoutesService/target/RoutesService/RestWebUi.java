package main.webapp;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestWebUi {

    static class DocumentBuilder {
        Document _document;
        Stack<Element> _stack = new Stack<Element>();

        public DocumentBuilder() throws ParserConfigurationException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            //Document doc = new XmlDocument();
            //doc.appendChild(doc.createXmlDeclaration("1.0", "utf-8", null));


            Element root = doc.createElement("html");
            root.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
            root.setAttribute("version", "-//W3C//DTD XHTML 2.0//EN");
            root.setAttribute("xml:lang", "en");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.w3.org/1999/xhtml http://www.w3.org/MarkUp/SCHEMA/xhtml2.xsd");
            doc.appendChild(root);

            _stack.push(root);

            _document = doc;
        }

        private void validateState() {
            if (_document == null)
                throw new IllegalStateException();
        }

        public DocumentBuilder appendTag(String name) {
            return this.openTag(name).closeTag();
        }

        public DocumentBuilder openTag(String name) {
            this.validateState();

            Element el = _document.createElement(name);
            _stack.peek().appendChild(el);
            _stack.push(el);

            return this;
        }

        public DocumentBuilder closeTag() {
            this.closeTag(null);
            return this;
        }

        public DocumentBuilder closeTag(String name) {
            if (_stack.size() <= 1)
                throw new IllegalStateException();

            if (name != null && !_stack.peek().getTagName().equals(name))
                throw new IllegalStateException();

            _stack.pop();

            return this;
        }

        public DocumentBuilder setAttribute(String name, String value) {
            this.validateState();

            _stack.peek().setAttribute(name, value);

            return this;
        }

        public DocumentBuilder appendUnescapedText(String text)
        {
            return this.appendUnescapedText("%s", text);
        }

        public DocumentBuilder appendUnescapedText(String format, Object ... args)
        {
            this.validateState();

            // DocumentFragment node = _document.createDocumentFragment();
            // DocumentFragmentImpl nodeImpl = (DocumentFragmentImpl)node;
            // nodeImpl.setNodeValue(String.format(format, args));

            String rawText = String.format(format, args);

            Node disableEscaping = _document.createProcessingInstruction(StreamResult.PI_DISABLE_OUTPUT_ESCAPING, "&");
            _stack.peek().appendChild(disableEscaping );

            DocumentFragment node = _document.createDocumentFragment();
            node.setTextContent(rawText);
            _stack.peek().appendChild(node);

            Node enableEscaping = _document.createProcessingInstruction(StreamResult.PI_ENABLE_OUTPUT_ESCAPING, "&");
            _stack.peek().appendChild(enableEscaping);

            return this;
        }

        public DocumentBuilder appendText(String text) {
            return this.appendText("%1$s", text);
        }

        public DocumentBuilder appendText(String format, Object... args) {
            this.validateState();

            Text node = _document.createTextNode(String.format(format, args));
            _stack.peek().appendChild(node);

            return this;
        }

        public Document buildDocument() {
            this.validateState();

            Document doc = _document;
            _document = null;
            _stack = null;
            return doc;
        }
    }

    static class HttpMultipartParser
    {
        private final HashMap<String, String> _parameters = new HashMap<String, String>();

        public String get(String fieldName)
        {
            return _parameters.get(fieldName);
        }

        public HttpMultipartParser(InputStream stream)
        {
            this.parse(stream);
        }

        private void parse(InputStream stream)
        {
            String content = Utils.readAsStringTillEnd(stream);

            int delimiterEndIndex = content.indexOf("\r\n");

            if (delimiterEndIndex > -1)
            {
                String delimiter = content.substring(0, delimiterEndIndex);

                String[] sections = content.split(delimiter);

                for (String s : sections)
                {
                    if (s.contains("Content-Disposition"))
                    {
                        // If we find "Content-Disposition", this is a valid multi-part section
                        // Now, look for the "name" parameter
                        Matcher nameMatch = Pattern.compile("name=\".*\"").matcher(s);
                        if (nameMatch.find()) {
                            String name = nameMatch.group().substring(6, nameMatch.group().length() - 1);

                            if (name != null && name.length() > 0)
                            {
                                // Get the start & end indexes of the file contents
                                int startIndex = nameMatch.end() + "\r\n\r\n".length();
                                _parameters.put(name, s.substring(startIndex).trim());
                            }
                        }
                    }
                }
            }
        }
    }

    private static Logger log() {
        Logger log = Logger.getLogger(RestWebUi.class.getName());
        return log;
    }

    public static void handleFreeWebRequest(HttpServletRequest request, HttpServletResponse response, String baseUrl, String requestPath) throws Throwable {
        safeHandleWebRequestImpl(request, response, baseUrl, requestPath);
    }

    private static void safeHandleWebRequestImpl(HttpServletRequest request, HttpServletResponse response, String baseUrl, String requestPath) throws Throwable {
        // try {

        DocumentBuilder doc = new DocumentBuilder();
        handleWebRequestImpl(doc, request, response, baseUrl, requestPath);


//            WebOperationContext.Current.OutgoingResponse.ContentType = "text/html; charset=utf-8";
//
//            if (wc.ResponseHeaders[CRestOperationContext.SessionIdCookieHeaderName] != null)
//                WebOperationContext.Current.OutgoingResponse.Headers[CRestOperationContext.SessionIdCookieHeaderName] = wc.ResponseHeaders[CRestOperationContext.SessionIdCookieHeaderName];
//            if (wc.ResponseHeaders[HttpResponseHeader.SetCookie] != null)
//                WebOperationContext.Current.OutgoingResponse.Headers[HttpResponseHeader.SetCookie] = wc.ResponseHeaders[HttpResponseHeader.SetCookie];

        // String responseBody = doc.BuildDocument().OuterXml;
        //responseBody = responseBody.Replace("9399", "9300");
        // Byte[] bytes = Encoding.UTF8.GetBytes(responseBody);
        // return new MemoryStream(bytes, 0, bytes.Length, false);

        response.setHeader("Content-Type",  "application/xhtml+xml; charset=utf-8");
        javax.xml.transform.TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc.buildDocument()), new StreamResult(response.getOutputStream()));

        // } catch (Throwable ex) {
        // build error webpage
        // }
    }

    private static void beginWebPage(DocumentBuilder doc, String method, String targetMethodHref) throws IOException {
        try (InputStream inputStream = RestWebUi.class.getClassLoader().getResourceAsStream("main/webapp/RestWebUi.css")) {
            String theCss = Utils.readAsStringTillEnd(inputStream);

            doc.openTag("head")
                    .openTag("title")
                        .appendText("%s %s", method, targetMethodHref)
                    .closeTag()
                    .openTag("style")
                        .appendUnescapedText(theCss)
                    .closeTag()
                .closeTag();

            doc.openTag("body");
        }
    }

    private static void endWebPage(DocumentBuilder doc) throws IOException {
        try (InputStream inputStream = RestWebUi.class.getClassLoader().getResourceAsStream("main/webapp/RestWebUi.js")) {
            String theJs = Utils.readAsStringTillEnd(inputStream);

            doc.openTag("form")
                    .setAttribute("name", "webMethodForm")
                    .setAttribute("id", "webMethodForm")
                    .setAttribute("action", "#")
                    .setAttribute("method", "POST")
                    .setAttribute("enctype", "multipart/form-data")
                    .setAttribute("style", "visibility: hidden;");

            doc.openTag("table")
                    .openTag("tr")
                    .openTag("td")
                    .setAttribute("colspan", "2")
                    .setAttribute("align", "center")
                    .appendText("Call web method")
                    .closeTag()
                    .closeTag();

            doc.openTag("tr")
                    .openTag("td")
                    .appendText("HTTP Method")
                    .closeTag()
                    .openTag("td")
                    .openTag("input")
                    .setAttribute("type", "text")
                    .setAttribute("name", "requestMethod")
                    .setAttribute("id", "requestMethod")
                    // .SetAttribute("readonly", "readonly")
                    .setAttribute("size", "100")
                    .closeTag()
                    .closeTag()
                    .closeTag();

            doc.openTag("tr")
                    .openTag("td")
                    .appendText("Target")
                    .closeTag()
                    .openTag("td")
                    .openTag("input")
                    .setAttribute("type", "text")
                    .setAttribute("name", "requestTarget")
                    .setAttribute("id", "requestTarget")
                    .setAttribute("size", "100")
                    .closeTag()
                    .closeTag()
                    .closeTag();

            doc.openTag("tr")
                    .openTag("td")
                        .appendText("Request body")
                    .closeTag()
                    .openTag("td")
                        .openTag("textarea")
                            .setAttribute("name", "requestBody")
                            .setAttribute("rows", "15")
                            .setAttribute("cols", "100")
                            .appendText("")
                        .closeTag()
                    .closeTag()
                .closeTag();

            doc.openTag("tr")
                    .openTag("td")
                    .appendText(" ")
                    .closeTag()
                    .openTag("td")
                    .openTag("input")
                    .setAttribute("type", "submit")
                    .closeTag()
                    .closeTag()
                    .closeTag();

            doc.closeTag("table");
            doc.closeTag("form");

            doc.openTag("script")
                    .setAttribute("type", "text/javascript")
                    .appendUnescapedText(theJs);
        }
    }

    private static void handleWebRequestImpl(DocumentBuilder doc, HttpServletRequest request, HttpServletResponse response, String baseUrl, String relativeUrl) throws IOException, ServletException, ParserConfigurationException, SAXException, URISyntaxException, TransformerException {
        if (relativeUrl == null)
            relativeUrl = "";
        else
            relativeUrl = relativeUrl.substring(4);

        if (relativeUrl.equals("") || relativeUrl.equals("/"))
            relativeUrl = "/routes";

        // method = (requestBodyStream == null ? "GET" : "POST").toUpperCase();
        String method = request.getMethod();

        log().info(String.format("Handling web request [method: {%s}, relativeUrl: {%s}]", method, relativeUrl));

        if (relativeUrl.startsWith("/web"))
            throw new UnsupportedOperationException("Recursive web requests not supported");

        String a = request.getPathInfo();
        String b = request.getPathTranslated();
        String c = request.getQueryString();
        String d = request.getRequestURI();
        StringBuffer e = request.getRequestURL();

        String targetMethodHref = baseUrl + relativeUrl;
        if (request.getQueryString() != null)
            targetMethodHref += "?" + request.getQueryString();

        // WebOperationContext context = WebOperationContext.Current;

        URL target = new URL(targetMethodHref);
        HttpURLConnection connection = (HttpURLConnection)target.openConnection();
        connection.setDoInput(true);
        connection.setUseCaches(false);

        // if (WebOperationContext.Current.IncomingRequest.Headers[HttpRequestHeader.Cookie] != null)
        //     wc.Headers[HttpRequestHeader.Cookie] = WebOperationContext.Current.IncomingRequest.Headers[HttpRequestHeader.Cookie];

        if (method.equals("GET")) {
            connection.setRequestMethod("GET");
            beginWebPage(doc, method, targetMethodHref);
        } else {
            HttpMultipartParser form = new HttpMultipartParser(request.getInputStream());
//                String requestBodyText = Utils.readAsStringTillEnd(request.getPart("requestBody").getInputStream());
//                String requestTarget = Utils.readAsStringTillEnd(request.getPart("requestTarget").getInputStream());
//                String requestMethod = Utils.readAsStringTillEnd(request.getPart("requestMethod").getInputStream());
            String requestBodyText = form.get("requestBody");
            String requestTarget = form.get("requestTarget");
            String requestMethod = form.get("requestMethod");

            if (requestBodyText == null || requestTarget == null || requestMethod == null) {
                beginWebPage(doc, method, targetMethodHref);

                doc.openTag("h1")
                    .appendText("Request parameters are incomplete. Please use request form to submit web request.")
                    .closeTag();

                endWebPage(doc);
                return;
            } else {
                beginWebPage(doc, requestMethod, requestTarget);

                target = new URL(requestTarget);
                connection = (HttpURLConnection)target.openConnection();
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod(requestMethod);

                if (requestMethod.equals("GET")) {
                    connection.setRequestMethod("GET");
                } else {
                    byte[] requestBytes = requestBodyText.getBytes();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
                    connection.setRequestProperty("Content-Length", Integer.toString( requestBytes.length));
                    connection.getOutputStream().write(requestBytes);
                }
            }
        }

        InputStream reader;
        if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
            reader = connection.getInputStream();
        } else {
            reader = connection.getErrorStream();
        }
        String resultString = Utils.readAsStringTillEnd(reader); //connection.getInputStream());

        doc.openTag("h1")
                .appendText(connection.getRequestMethod())
                .appendText(" ")
                .appendText(connection.getURL().toString())
                .closeTag();

        doc.openTag("h1")
                .appendText(Integer.toString(connection.getResponseCode()))
                .appendText(" ")
                .appendText(connection.getResponseMessage())
                .closeTag();

        if (resultString == null || resultString.trim().length() == 0) {
            // doc.appendText("OK");
        } else if (resultString.length() > 0) {
            String contentType = connection.getHeaderField("Content-Type");
            if (contentType != null && contentType.contains("/xml")) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                javax.xml.parsers.DocumentBuilder docBuilder = factory.newDocumentBuilder();
                Document resultBodyDoc = docBuilder.parse(new InputSource(new StringReader(resultString)));

                buildWebPageNode(resultBodyDoc.getDocumentElement(), doc, baseUrl);
            } else {
                doc.openTag("pre")
                    .appendText(resultString)
                    .closeTag();
            }
        } else {
            doc.appendText("Rest Api Web Gui does not support files downloading");
        }

        endWebPage(doc);
    }

    private static void buildWebPageNode(Node node, DocumentBuilder doc, String baseUrl) throws URISyntaxException, TransformerException {
        if (node instanceof Text)
        {
            String textContent = ((Text)node).getNodeValue();
            if (textContent.trim().length() > 0) {
                String[] text = textContent.split("\n", 2);

                doc.openTag("pre")
                        .appendText(text[0])
                        .closeTag();

                if (text.length > 1)
                {
                    doc.openTag("div")
                            .openTag("pre")
                            .appendText(text[1])
                            .closeTag()
                            .closeTag();
                }
            }
        }
        else
        {
            doc.openTag("div");

            if (node instanceof Element)
            {
                Element el = (Element)node;

                boolean hasChilds = el.hasChildNodes();
                boolean isLink = el.getNodeName().equals("Link");
                boolean isAction = el.getNodeName().equals("Action");
                String webHref = el.getAttribute("Href");

                doc.appendText("<%s", node.getNodeName());

                for (int i = 0; i < el.getAttributes().getLength(); i++)
                {
                    Node attr = el.getAttributes().item(i);
                    if (attr.getNodeName().equals("Href"))
                    {
                        URI hrefUri = new URI(webHref);

                        if (hrefUri.isAbsolute() && webHref.startsWith(baseUrl))
                        {
                            webHref = baseUrl + "/web" + webHref.substring(baseUrl.length());
                        }

                        doc.appendText(" Href=\"")
                                .openTag("a");

                        if (isLink) {
                            doc.setAttribute("href", webHref);
                        } else if (isAction) {
                            doc.setAttribute("href", "#webMethodForm")
                               .setAttribute("onclick", "javascript: prepareWebMethod('" + el.getAttribute("Method").toUpperCase() + "', '" + webHref + "', '" + attr.getNodeValue() + "');");

//                                    default:
//                                    {
//                                        doc.SetAttribute("href", "#")
//                                                .SetAttribute("class", "notimpl")
//                                                .SetAttribute("onClick", "javascript: alert('Links handling for relation " + linkRelation + " is not implemented!');");
//                                    } break;
//                            {
//                                // invalid Link
//                                doc.SetAttribute("class", "invalid");
//                            }
                        }
                        else
                        {
                            // simple href, not part of Link
                            doc.setAttribute("href", webHref);
                        }

                        doc.appendText(attr.getNodeValue())
                                .closeTag()
                                .appendText("\"");
                    }
                    else
                    {
                        doc.appendText(" %s=\"", attr.getNodeName())
                                .openTag("span")
                                .setAttribute("class", "attrValue")
                                .appendText(attr.getNodeValue())
                                .closeTag()
                                .appendText("\"");
                    }
                }

                doc.appendText(hasChilds ? ">" : " />");

                for (int i = 0; i < node.getChildNodes().getLength(); i++)
                    buildWebPageNode(node.getChildNodes().item(i), doc, baseUrl);

                if (hasChilds)
                    doc.appendText("</%s>", el.getNodeName());
            }
            else
            {
                doc.setAttribute("class", "unknown")
                        .appendText(outerXml(node));
            }

            doc.closeTag();
        }
    }

    private static String outerXml(Node node) throws TransformerException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", true ? "yes" : "no");
        transformer.transform(new DOMSource(node), new StreamResult(outStream));
        return Utils.readAsStringTillEnd(new ByteArrayInputStream(outStream.toByteArray()));
    }
}
