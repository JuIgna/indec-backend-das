package ar.edu.ubp.das.indecback.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.MarshalException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import org.json.JSONObject;
import org.json.XML;

public class SOAPClient {
    private String wsdlUrl;
    private String namespace;
    private String serviceName;
    private String portName;
    private String operationName;
    private String soapAction;
    private String username;
    private String password;
    private static final Set<Class<?>> SIMPLE_TYPES = new
            HashSet<>(Arrays.asList(
            String.class, Boolean.class, Character.class, Byte.class,
            Short.class, Integer.class, Long.class,
            Float.class, Double.class, BigDecimal.class, BigInteger.class
    ));
    private SOAPClient(SOAPClientBuilder builder) {
        this.wsdlUrl = builder.wsdlUrl;
        this.namespace = builder.namespace;
        this.serviceName = builder.serviceName;
        this.portName = builder.portName;
        this.operationName = builder.operationName;
        this.soapAction = builder.soapAction;
        this.username = builder.username;
        this.password = builder.password;
    }
    public <T> T callServiceForObject(Class<T> clazz, String responseElementName, Map<String, Object> parameters) {
        try {
            SOAPMessage soapRequest = createRequest(parameters);
            soapRequest.writeTo(System.out); // Para depuración
            SOAPMessage soapResponse = sendRequest(soapRequest);
            soapResponse.writeTo(System.out); // Para depuración
            return processResponseForObject(soapResponse, clazz, responseElementName);
        }
        catch (SOAPFaultException e) {
            SOAPFault fault = e.getFault();
            throw new RuntimeException(fault.getFaultCode() + "- " + fault.getFaultString());
        }
        catch (MarshalException | UnmarshalException e) {
            Throwable linkedException = e.getLinkedException();
            if (linkedException != null) {
                throw new RuntimeException(linkedException.getMessage(), e);
            }
            else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public <T> T callServiceForObject(Class<T> clazz, String
            responseElementName) {
        return callServiceForObject(clazz, responseElementName, null);
    }
    public <T> List<T> callServiceForList(Class<T> clazz, String responseElementName, Map<String, Object> parameters) {
        try {
            SOAPMessage soapRequest = createRequest(parameters);
            soapRequest.writeTo(System.out); // Para depuración
            SOAPMessage soapResponse = sendRequest(soapRequest);
            soapResponse.writeTo(System.out); // Para depuración
            return processResponseForList(soapResponse, clazz, responseElementName);
        }
        catch (SOAPFaultException e) {
            SOAPFault fault = e.getFault();
            throw new RuntimeException(fault.getFaultCode() + "- " + fault.getFaultString());
        }
        catch (MarshalException | UnmarshalException e) {
            Throwable linkedException = e.getLinkedException();
            if (linkedException != null) {
                throw new RuntimeException(linkedException.getMessage(), e);
            }
            else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> List<T> callServiceForList(Class<T> clazz, String responseElementName) {
        return callServiceForList(clazz, responseElementName, null);
    }

    public String callServiceForJson(String responseElementName, String objectResponseName, Map<String, Object> parameters) {
        try {
            SOAPMessage soapRequest = createRequest(parameters);
            SOAPMessage soapResponse = sendRequest(soapRequest);

            SOAPBody body = soapResponse.getSOAPBody();
            if (body.hasFault()) {
                SOAPFault fault = body.getFault();
                throw new RuntimeException("SOAP Fault: " + fault.getFaultCode() + " - " + fault.getFaultString());
            }

            // Convertir el cuerpo SOAP en XML
            Iterator<Node> iterator = body.getChildElements();
            StringBuilder xmlBuilder = new StringBuilder();

            while (iterator.hasNext()) {
                Node node = iterator.next();
                if (node instanceof SOAPElement) {
                    SOAPElement element = (SOAPElement) node;
                    if (element.getLocalName().equals(responseElementName)) {
                        xmlBuilder.append(elementToString(element));
                    }
                }
            }

            // Convertir XML a JSON
            JSONObject jsonResponse = XML.toJSONObject(xmlBuilder.toString());

            // Extraer el objeto "SucursalesCompletasResponse"
            JSONObject responseWrapper = jsonResponse.optJSONObject("ns3:" + responseElementName);
            if (responseWrapper != null) {
                Object sucursales = responseWrapper.opt(objectResponseName);
                return sucursales.toString(); // Devolver solo el JSON de sucursales
            }

            return jsonResponse.toString(4); // Si falla, devuelve todo el JSON


        } catch (Exception e) {
            throw new RuntimeException("Error al invocar servicio SOAP: " + e.getMessage(), e);
        }
    }

    private String elementToString(SOAPElement element) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
        return writer.toString();
    }


    private SOAPMessage createRequest(Map<String, Object> parameters) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();

        // Añadir la operación en el cuerpo con el prefijo 'tns' y el namespace correcto
        SOAPElement operation = body.addChildElement(operationName, "tns", namespace);

        // Agregar parámetros si existen
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                addObjectToOperation(operation, entry.getKey(), entry.getValue());
            }
        }

        // Agregar el encabezado de seguridad y guardar los cambios
        addWSSecurityHeader(soapMessage);
        soapMessage.saveChanges();

        // Imprimir el mensaje de solicitud para depuración
        //System.out.println("SOAP Request:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
    }

    private void addObjectToOperation(SOAPElement operation, String parameterName, Object parameter) throws Exception {
        if (isSimpleType(parameter.getClass())) {
            operation.addChildElement(parameterName).addTextNode(parameter.toString());
        }
        else {
            JAXBContext jaxbContext = JAXBContext.newInstance(parameter.getClass()); //Serialización
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(parameter, operation);
        }
    }
    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }
    private void addWSSecurityHeader(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPHeader header = envelope.getHeader();

        // Verificar si el encabezado existe; de lo contrario, agregarlo
        if (header == null) {
            header = envelope.addHeader();
        }

        // Agregar el prefijo y el namespace para el encabezado de seguridad
        String wssePrefix = "wsse";
        String wsseNamespaceURI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
        SOAPElement securityElem = header.addChildElement("Security", wssePrefix, wsseNamespaceURI);

        // Crear y agregar el UsernameToken y sus elementos secundarios
        SOAPElement usernameTokenElem = securityElem.addChildElement("UsernameToken", wssePrefix);
        SOAPElement usernameElem = usernameTokenElem.addChildElement("Username", wssePrefix);
        usernameElem.addTextNode(username);

        SOAPElement passwordElem = usernameTokenElem.addChildElement("Password", wssePrefix);
        passwordElem.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        passwordElem.addTextNode(password);
    }


    private SOAPMessage sendRequest(SOAPMessage soapRequest) throws Exception {

        QName serviceQName = new QName(namespace, serviceName); // Asegúrate de que estos valores coincidan
        Service service = Service.create(new URL(wsdlUrl), serviceQName);

        QName portQName = new QName(namespace, portName);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);


        // Configura el SOAPAction vacío si el servicio no lo requiere
        if (soapAction != null && !soapAction.isEmpty()) {
            dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, true);
            dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapAction);
        } else {
            dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, false);
        }

        // Configuración adicional, como timeouts
        dispatch.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 10000);
        dispatch.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 10000);

        SOAPMessage soapResponse;
        try {
            soapResponse = dispatch.invoke(soapRequest);
            // System.out.println("SOAP Response:");
            // soapResponse.writeTo(System.out); // Esto imprimirá la respuesta, si se recibe
            // System.out.println();
        } catch (Exception e) {
            System.err.println("Error en la invocación del servicio: " + e.getMessage());
            throw e; // Re-lanza para ver en qué línea ocurre el problema
        }


        return soapResponse;
    }




    private <T> T processResponseForObject(SOAPMessage soapResponse, Class<T> clazz, String responseElementName) throws Exception {
        List<T> objectList = new LinkedList<>();
        processResponse(soapResponse, clazz, responseElementName, objectList);
        return objectList.isEmpty() ? null : objectList.get(0);
    }
    private <T> List<T> processResponseForList(SOAPMessage soapResponse, Class<T> clazz, String responseElementName) throws Exception {
        List<T> objectList = new LinkedList<>();
        processResponse(soapResponse, clazz, responseElementName, objectList);
        return objectList;
    }
    private <T> void processResponse(SOAPMessage soapResponse, Class<T> clazz, String responseElementName, List<T> objectList) throws Exception {
        SOAPBody body = soapResponse.getSOAPBody();
        if (body.hasFault()) {
            SOAPFault fault = body.getFault();
            throw new RuntimeException("SOAP Fault: " + fault.getFaultCode() + " - " + fault.getFaultString());
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Iterator<Node> iterator = body.getChildElements();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof SOAPElement) {
                SOAPElement element = (SOAPElement) node;
                if (element.getLocalName().equals(responseElementName)) {
                    Iterator<?> childIterator =
                            element.getChildElements();
                    while (childIterator.hasNext()) {
                        Node childNode = (Node) childIterator.next();
                        if (childNode instanceof SOAPElement) {
                            SOAPElement childElement = (SOAPElement) childNode;
                            T object = (T) unmarshaller.unmarshal(childElement);
                            objectList.add(object);
                        }
                    }
                }
            }
        }
    }
    public static class SOAPClientBuilder {
        private String wsdlUrl;
        private String namespace;
        private String serviceName;
        private String portName;
        private String operationName;
        private String soapAction;
        private String username;
        private String password;
        public SOAPClientBuilder wsdlUrl(String wsdlUrl) {
            this.wsdlUrl = wsdlUrl;
            return this;
        }
        public SOAPClientBuilder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }
        public SOAPClientBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }
        public SOAPClientBuilder portName(String portName) {
            this.portName = portName;
            return this;
        }
        public SOAPClientBuilder operationName(String operationName) {
            this.operationName = operationName;
            return this;
        }
        public SOAPClientBuilder soapAction(String soapAction) {
            this.soapAction = soapAction;
            return this;
        }
        public SOAPClientBuilder username(String username) {
            this.username = username;
            return this;
        }
        public SOAPClientBuilder password(String password) {
            this.password = password;
            return this;
        }
        public SOAPClient build() {
            return new SOAPClient(this);
        }
    }
}
