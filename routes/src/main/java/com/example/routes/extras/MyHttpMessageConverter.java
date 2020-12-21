package com.example.routes.extras;

import com.example.routes.RestApiException;
import com.example.routes.query.StringBuilderEx;
import com.example.routes.xml.ObjectFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
public class MyHttpMessageConverter<T> implements HttpMessageConverter<T> {

    public List<MediaType> supportedMediaTypes = Arrays.asList(MediaType.APPLICATION_XML, MediaType.TEXT_XML);

    private boolean canHandle(Class<?> type) {
        return type.isAnnotationPresent(javax.xml.bind.annotation.XmlType.class);
    }

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return this.canHandle(aClass);
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return this.canHandle(aClass);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    @Override
    public T read(Class<? extends T> type, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        T result;
        StringBuilderEx errors = new StringBuilderEx();
        StringBuilderEx allMessages = new StringBuilderEx();
        try {
            Logger logger = Logger.getLogger(getClass().getName());

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream schemaInputStream = MyHttpMessageConverter.class.getClassLoader().getResourceAsStream("mySchema.xsd");
            Schema schema = sf.newSchema(new StreamSource(schemaInputStream));

            String packageName = type.getPackage().getName();
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();

            u.setSchema(schema);
            u.setEventHandler(new ValidationEventHandler() {
                public boolean handleEvent(ValidationEvent event) {

                    StringBuilderEx error = new StringBuilderEx();
                    error.appendLine("SEVERITY:  " + event.getSeverity());
                    error.appendLine("MESSAGE:  " + event.getMessage());
                    error.appendLine("LINKED EXCEPTION:  " + event.getLinkedException());
                    error.appendLine("LOCATOR");
                    error.appendLine("    LINE NUMBER:  " + event.getLocator().getLineNumber());
                    error.appendLine("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
                    if (event.getLocator().getOffset() != -1)
                        error.appendLine("    OFFSET:  " + event.getLocator().getOffset());
                    if (event.getLocator().getObject() != null)
                        error.appendLine("    OBJECT:  " + event.getLocator().getObject());
                    if (event.getLocator().getNode() != null)
                        error.appendLine("    NODE:  " + event.getLocator().getNode());
                    if (event.getLocator().getURL() != null)
                        error.appendLine("    URL:  " + event.getLocator().getURL());
                    logger.warning(error.toString());
                    allMessages.appendLine(error.toString());

                    if (event.getSeverity() > ValidationEvent.ERROR) {
                        errors.appendLine(error.toString());
                    }

                    return true;
                }
            });

            JAXBElement<T> doc = (JAXBElement<T>) u.unmarshal(httpInputMessage.getBody());
            result = doc.getValue();
        } catch (Throwable ex) {
            throw new RestApiException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error during deserialization", ex);
        }

        if (errors.length() > 0) {
            throw new RestApiException(HttpServletResponse.SC_BAD_REQUEST, "Entity validation failed during deserialization\n" + allMessages.toString());
        }

        return result;
    }

    private Serializable wrapWithJaxbElement(Object obj) throws InvocationTargetException, IllegalAccessException {
        Class type = obj.getClass();
        List<Method> methods = Arrays.stream(ObjectFactory.class.getMethods())
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == type && JAXBElement.class.isAssignableFrom(m.getReturnType()))
                .collect(Collectors.toList());

        if (methods.size() != 1)
            throw new RuntimeException("Unable to find corresponding root element info against " + type.getTypeName());

        return (Serializable) methods.get(0).invoke(new ObjectFactory(), obj);
    }

    @Override
    public void write(T obj, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            httpOutputMessage.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this.wrapWithJaxbElement(obj), httpOutputMessage.getBody());
        } catch (Throwable ex) {
            // TODO throw RestApiException?
            throw new RuntimeException(ex);
        }
    }
}
