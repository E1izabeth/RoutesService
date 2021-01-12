package com.example.routes;

import com.ctc.wstx.compat.QNameCreator;
import com.ibm.wsdl.BindingImpl;
import com.ibm.wsdl.DefinitionImpl;
import com.ibm.wsdl.PortImpl;
import com.ibm.wsdl.ServiceImpl;
import com.ibm.wsdl.extensions.soap.SOAPBindingImpl;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.server.endpoint.adapter.method.jaxb.JaxbElementPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.transport.TransportConstants;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.transport.support.TransportUtils;
import org.springframework.ws.wsdl.WsdlDefinition;
import org.springframework.ws.wsdl.WsdlDefinitionException;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl4jDefinition;
import org.springframework.ws.wsdl.wsdl11.provider.DefaultMessagesProvider;
import org.springframework.ws.wsdl.wsdl11.provider.SoapProvider;
import org.springframework.xml.transform.TransformerHelper;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.wsdl.*;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.ResponseWrapper;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    private WsdlDefinition makeWsdlDefinition(XsdSchema xsdSchema, Class serviceType, String locationUri) throws WSDLException {
        SoapProvider pp = new SoapProvider();

        WSDLFactory factory = WSDLFactory.newInstance();

        Definition def = factory.newDefinition();
        def.setTargetNamespace(xsdSchema.getTargetNamespace());
        def.setQName(new QName(def.getTargetNamespace(), serviceType.getSimpleName() + "ServiceDefinition"));
        def.addNamespace("tns", xsdSchema.getTargetNamespace());
        def.addNamespace("soap", "http://schemas.xmlsoap.org/wsdl/soap/");

        // def.setDocumentBaseURI(ctx.ser);

        if (def.getExtensionRegistry() == null)
            def.setExtensionRegistry(factory.newPopulatedExtensionRegistry());

        Types types = def.createTypes();
        Schema schema = (Schema)def.getExtensionRegistry().createExtension(Types.class, new QName("http://www.w3.org/2001/XMLSchema", "schema"));
        schema.setElement(this.getSchemaElement(xsdSchema));
        types.addExtensibilityElement(schema);
        def.setTypes(types);

        Service service = def.createService();
        service.setQName(new QName(def.getTargetNamespace(), serviceType.getSimpleName() + "Service"));
        def.addService(service);

        Port port = def.createPort();
        port.setName(serviceType.getSimpleName() + "Port");
        service.addPort(port);

        PortType portType = def.createPortType();
        portType.setUndefined(false);
        portType.setQName(new QName(def.getTargetNamespace(), serviceType.getSimpleName() + "PortType"));
        def.addPortType(portType);

        SOAPAddress soapAddress = (SOAPAddress)def.getExtensionRegistry().createExtension(Port.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address"));
        soapAddress.setLocationURI(locationUri);
        port.addExtensibilityElement(soapAddress);

        Binding binding = def.createBinding();
        binding.setUndefined(false);
        binding.setPortType(portType);
        binding.setQName(new QName(def.getTargetNamespace(), serviceType.getSimpleName() + "Binding"));
        port.setBinding(binding);
        def.addBinding(binding);

        SOAPBinding soapBinding = (SOAPBinding)def.getExtensionRegistry().createExtension(Binding.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding"));
        soapBinding.setStyle("document");
        soapBinding.setTransportURI("http://schemas.xmlsoap.org/soap/http");
        binding.addExtensibilityElement(soapBinding);


        SOAPBody inputSoapBody = (SOAPBody)def.getExtensionRegistry().createExtension(BindingInput.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body"));
        inputSoapBody.setUse("literal");

        SOAPBody outputSoapBody = (SOAPBody)def.getExtensionRegistry().createExtension(BindingOutput.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body"));
        outputSoapBody.setUse("literal");

        for (Method method : serviceType.getMethods()) {
            if (method.getAnnotation(PayloadRoot.class) == null)
                continue;

            Operation operation = def.createOperation();
            operation.setUndefined(false);
            operation.setName(method.getName());
            //operation.setParameterOrdering();
            portType.addOperation(operation);

            BindingOperation bindingOperation = def.createBindingOperation();
            bindingOperation.setName(method.getName());
            bindingOperation.setOperation(operation);
            binding.addBindingOperation(bindingOperation);

            {
                Parameter[] params = method.getParameters();
                if (params.length != 1)
                    throw new RuntimeException("Operation " + serviceType.getSimpleName() + "." + method.getName() + " has unexpected number of parameters");

                PayloadRoot payloadRoot = method.getAnnotation(PayloadRoot.class);

                Message inputMessage = def.createMessage();
                inputMessage.setUndefined(false);
                inputMessage.setQName(new QName(def.getTargetNamespace(), method.getName() + "RequestMessage"));
                def.addMessage(inputMessage);

                Part inputMessagePart = def.createPart();
                inputMessagePart.setElementName(new QName(payloadRoot.namespace(), payloadRoot.localPart()));
                inputMessagePart.setName(payloadRoot.localPart());
                // inputMessagePart.setTypeName(); // not used?
                inputMessage.addPart(inputMessagePart);

                Input input = def.createInput();
                input.setName(method.getName() + "Request");
                input.setMessage(inputMessage);
                operation.setInput(input);

                BindingInput bindingInput = def.createBindingInput();
                bindingInput.setName(method.getName() + "Request");
                bindingInput.addExtensibilityElement(inputSoapBody);
                bindingOperation.setBindingInput(bindingInput);
            }

            Class opResultType = method.getReturnType();
            if (opResultType.equals(void.class)) {
                operation.setStyle(OperationType.ONE_WAY);
            } else {
                List<Method> wrappers = MySerializer.findXmlTypeRootWrappers(opResultType);
                if (wrappers.size() != 1)
                    throw new RuntimeException("Unable to describe operation " + serviceType.getSimpleName() + "." + method.getName() + "");

                XmlElementDecl elementDecl = wrappers.get(0).getAnnotation(XmlElementDecl.class);

                Message outputMessage = def.createMessage();
                outputMessage.setUndefined(false);
                outputMessage.setQName(new QName(def.getTargetNamespace(), method.getName() + "ResponseMessage"));
                def.addMessage(outputMessage);

                Part outputMessagePart = def.createPart();
                outputMessagePart.setElementName(new QName(elementDecl.namespace(), elementDecl.name()));
                outputMessagePart.setName(elementDecl.name());
                // inputMessagePart.setTypeName(); // not used?
                outputMessage.addPart(outputMessagePart);

                Output output = def.createOutput();
                output.setName(method.getName() + "Response");
                output.setMessage(outputMessage);
                operation.setOutput(output);
                operation.setStyle(OperationType.REQUEST_RESPONSE);

                BindingOutput bindingOutput = def.createBindingOutput();
                bindingOutput.setName(method.getName() + "Response");
                bindingOutput.addExtensibilityElement(outputSoapBody);
                bindingOperation.setBindingOutput(bindingOutput);
            }
        }

//        Wsdl4jDefinition result = new Wsdl4jDefinition(def);
//        DOMSource src = (DOMSource)result.getSource();
//        String str = toString(src);

        return new Wsdl4jDefinition(def);
    }

//    public static String toString(DOMSource src) {
//        try {
//            StringWriter sw = new StringWriter();
//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer transformer = tf.newTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//
//            // transformer.transform(new DOMSource(doc), new StreamResult(sw));
//            transformer.transform(src, new StreamResult(sw));
//            return sw.toString();
//        } catch (Exception ex) {
//            throw new RuntimeException("Error converting to String", ex);
//        }
//    }

    private Element getSchemaElement(XsdSchema schema) {
        try {
            DOMResult result = new DOMResult();

            TransformerHelper transformerHelper = new TransformerHelper();
            transformerHelper.transform(schema.getSource(), result);
            
            Document schemaDocument = (Document)result.getNode();
            return schemaDocument.getDocumentElement();
        } catch (TransformerException var4) {
            throw new WsdlDefinitionException("Could not transform schema source to Document");
        }
    }

    @Override
    public void addArgumentResolvers(List<MethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<MethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(0, new JaxbElementPayloadMethodProcessor() {
            @Override
            protected boolean supportsResponsePayloadReturnType(MethodParameter returnType) {
                // TODO: handle @ResponseWrapper(partName = "rootElementName") if there is more than one suitable root element
                return returnType.hasMethodAnnotation(PayloadRoot.class) && MySerializer.findXmlTypeRootWrappers(returnType.getParameterType()).size() == 1;
//                return MySerializer.findXmlTypeRootWrappers(returnType.getParameterType()).size() == 1 || (
//                    returnType.hasMethodAnnotation(ResponseWrapper.class) && returnType.getMethodAnnotation(ResponseWrapper.class).partName().length() > 0
//                );
            }

            @Override
            protected void handleReturnValueInternal(MessageContext messageContext, MethodParameter returnType, Object returnValue) throws JAXBException {
                try {
                    JAXBElement<?> warppedReturnValue = MySerializer.wrapWithJaxbElement(returnValue);
                    super.handleReturnValueInternal(messageContext, returnType, warppedReturnValue);
                } catch (Throwable e) {
                    throw new RuntimeException("Unable to marshal return value of type " + returnValue.getClass().getName(), e);
                }
            }
        });
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "routesSvc")
    public WsdlDefinition routesWsdl11Definition(XsdSchema schema) throws WSDLException {
        /*DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RoutesEndpoint");
        wsdl11Definition.setLocationUri("/ws/routes");
        wsdl11Definition.setTargetNamespace("main.webapp.xml");
        wsdl11Definition.setSchema(schema);
        wsdl11Definition.setRequestSuffix("Spec");
        wsdl11Definition.setResponseSuffix("Result");
        return wsdl11Definition;*/
        return this.makeWsdlDefinition(schema, RoutesEndpoint.class, "/ws/routes");
    }

    @Bean(name = "locationsSvc")
    public WsdlDefinition locationsWsdl11Definition(XsdSchema schema) throws WSDLException {
        /*DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("LocationsEndpoint");
        wsdl11Definition.setLocationUri("/ws/locations");
        wsdl11Definition.setTargetNamespace("main.webapp.xml");
        wsdl11Definition.setSchema(schema);
        wsdl11Definition.setRequestSuffix("Spec");
        wsdl11Definition.setResponseSuffix("Result");
        return wsdl11Definition;*/
        return this.makeWsdlDefinition(schema, LocationsEndpoint.class, "/ws/locations");
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("routes.xsd"));
    }
}