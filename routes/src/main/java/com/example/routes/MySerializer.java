package com.example.routes;

import com.example.routes.extras.MyHttpMessageConverter;
import com.example.routes.query.StringBuilderEx;
import com.example.routes.xml.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MySerializer {
    //
    // xjc -d ./../../.. -p main.webapp.xml -xmlschema ./mySchema.xsd
    //


    private Serializable wrapWithJaxbElement(Object obj) throws InvocationTargetException, IllegalAccessException {
        Class type = obj.getClass();
        List<Method> methods = Arrays.stream(ObjectFactory.class.getMethods())
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == type && JAXBElement.class.isAssignableFrom(m.getReturnType()))
                .collect(Collectors.toList());

        if (methods.size() != 1)
            throw new RuntimeException("Unable to find corresponding root element info against " + type.getTypeName());

        return (Serializable) methods.get(0).invoke(new ObjectFactory(), obj);
    }

    public void serialize(Object obj, Writer writer) throws JAXBException, InvocationTargetException, IllegalAccessException {
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this.wrapWithJaxbElement(obj), writer);
    }

    public <T> T deserialize(Class<T> type, Reader reader) throws JAXBException, SAXException {
        StringBuilderEx errors = new StringBuilderEx();
        StringBuilderEx allMessages = new StringBuilderEx();

        Logger logger = Logger.getLogger(getClass().getName());

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        InputStream schemaInputStream = MySerializer.class.getClassLoader().getResourceAsStream("/main/webapp/xml/mySchema.xsd");
        Schema schema = sf.newSchema(new StreamSource(schemaInputStream ));

        String packageName = type.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance( packageName );
        Unmarshaller u = jc.createUnmarshaller();

        u.setSchema(schema);
        u.setEventHandler(new ValidationEventHandler() {
            public boolean handleEvent(ValidationEvent event) {

                StringBuilderEx error = new StringBuilderEx();
                error.appendLine("\nEVENT");
                error.appendLine("SEVERITY:  " + event.getSeverity());
                error.appendLine("MESSAGE:  " + event.getMessage());
                error.appendLine("LINKED EXCEPTION:  " + event.getLinkedException());
                error.appendLine("LOCATOR");
                error.appendLine("    LINE NUMBER:  " + event.getLocator().getLineNumber());
                error.appendLine("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
                error.appendLine("    OFFSET:  " + event.getLocator().getOffset());
                error.appendLine("    OBJECT:  " + event.getLocator().getObject());
                error.appendLine("    NODE:  " + event.getLocator().getNode());
                error.appendLine("    URL:  " + event.getLocator().getURL());
                logger.warning(error.toString());
                allMessages.appendLine(error.toString());

                if (event.getSeverity() > ValidationEvent.ERROR) {
                    errors.appendLine(error.toString());
                }

                return true;
            }
        });

        JAXBElement<T> doc = (JAXBElement<T>)u.unmarshal(reader);
        T obj = doc.getValue();

        if (errors.length() > 0)
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Entity validation failed during deserialization\n" + allMessages.toString());

        return obj;
    }

    /*
    public void makeSchema() {
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final OutputStreamWriter writer = new OutputStreamWriter(stream);
            final StreamResult streamResult = new StreamResult(writer);
            streamResult.setSystemId("");

            JAXBContext jaxbContext = JAXBContext.newInstance(Route.class);
            jaxbContext.generateSchema(new SchemaOutputResolver() {
                public Result createOutput(String nameSpaceURI, String suggestedName) throws IOException {
                    return streamResult;
                }
            });

            StringBuilderEx sb = new StringBuilderEx();
            Scanner scanner = new Scanner(new InputStreamReader(new ByteArrayInputStream(stream.toByteArray())));
            while (scanner.hasNext()) {
                sb.appendLine(scanner.nextLine());
            }

            logger.info(sb.toString());
        } catch (Throwable e) {
            logger.severe(e.toString());
        }
    }
    */
}
