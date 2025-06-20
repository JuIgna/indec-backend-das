package ar.edu.ubp.das.indecback.utils;

public class SoapConfig {
    private String wsdlUrl;
    private String namespace;
    private String serviceName;
    private String portName;

    // Getters y setters
    public String getWsdlUrl() { return wsdlUrl; }
    public void setWsdlUrl(String wsdlUrl) { this.wsdlUrl = wsdlUrl; }
    public String getNamespace() { return namespace; }
    public void setNamespace(String namespace) { this.namespace = namespace; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }
}
