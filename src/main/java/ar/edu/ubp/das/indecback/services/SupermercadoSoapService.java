package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.utils.SOAPClient;
import ar.edu.ubp.das.indecback.utils.SoapConfig;
import ar.edu.ubp.das.indecback.utils.WSDLParser;
import java.util.Map;

public class SupermercadoSoapService implements SupermercadoService {
    private SOAPClient soapClient;
    private SoapConfig soapConfig;
    private String wsdlUrl;
    private String cuit;
    private String token;
    private Map<String, Object> config;


    public SupermercadoSoapService(String wsdlUrl, String cuit, String token, Map<String, Object> config) throws Exception {
        this.wsdlUrl = wsdlUrl;
        this.cuit = cuit;
        this.token = token;
        this.soapConfig = WSDLParser.parse(wsdlUrl);
        this.config = config;
    }

    @Override
    public String invocarServicio (String nombreOperacion){
        Map<String, String> operacion = (Map<String, String>) config.get(nombreOperacion);

        if (operacion == null) {
            throw new IllegalArgumentException("Operación no encontrada: " + nombreOperacion);
        }

        try{
            SOAPClient client = new SOAPClient.SOAPClientBuilder()
                    .wsdlUrl(wsdlUrl)
                    .namespace(soapConfig.getNamespace())
                    .serviceName(soapConfig.getServiceName())
                    .portName(soapConfig.getPortName())
                    .operationName(operacion.get("request"))
                    .username(cuit)  // Coloca el usuario de autenticación
                    .password(token)  // Coloca la contraseña de autenticación
                    .build();

            return client.callServiceForJson(operacion.get("response"), operacion.get("object"), null);

        }catch (Exception e){
            e.printStackTrace();
            return "{}";
        }

    }
}


