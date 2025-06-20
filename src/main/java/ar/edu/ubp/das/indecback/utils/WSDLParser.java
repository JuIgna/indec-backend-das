package ar.edu.ubp.das.indecback.utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URL;

public class WSDLParser {

    public static SoapConfig parse(String wsdlUrl) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // importante para WSDL
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new URL(wsdlUrl).openStream());

        // Obtener el targetNamespace del elemento <wsdl:definitions>
        Element definitions = doc.getDocumentElement();
        String targetNamespace = definitions.getAttribute("targetNamespace");

        // Obtener el primer <wsdl:service>
        NodeList serviceNodes = definitions.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "service");
        if (serviceNodes.getLength() == 0) {
            throw new RuntimeException("No se encontró ningún servicio en el WSDL");
        }
        Element serviceElem = (Element) serviceNodes.item(0);
        String serviceName = serviceElem.getAttribute("name");

        // Dentro del servicio, obtener el primer <wsdl:port>
        NodeList portNodes = serviceElem.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "port");
        if (portNodes.getLength() == 0) {
            throw new RuntimeException("No se encontró ningún port en el WSDL");
        }
        Element portElem = (Element) portNodes.item(0);
        String portName = portElem.getAttribute("name");

        // Crear y retornar el objeto de configuración
        SoapConfig config = new SoapConfig();
        config.setWsdlUrl(wsdlUrl);
        config.setNamespace(targetNamespace);
        config.setServiceName(serviceName);
        config.setPortName(portName);
        return config;
    }
}
