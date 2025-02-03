package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.utils.SOAPClient;
import ar.edu.ubp.das.indecback.utils.SoapConfig;
import ar.edu.ubp.das.indecback.utils.WSDLParser;


import java.util.List;
import java.util.Objects;

public class SupermercadoSoapService implements SupermercadoService {


    private SOAPClient soapClient;
    private SoapConfig soapConfig;
    private String wsdlUrl;
    private String cuit;
    private String token;


    public SupermercadoSoapService(String wsdlUrl, String cuit, String token) throws Exception {

        this.wsdlUrl = wsdlUrl;
        this.cuit = cuit;
        this.token = token;
        this.soapConfig = WSDLParser.parse(wsdlUrl);

    }

    @Override
    public <T> List <T> invocarServicio (Class<T> tipoRespuesta, String nombreOperacion, String metodo){

        try{
            this.soapClient = new SOAPClient.SOAPClientBuilder()
                    .wsdlUrl(wsdlUrl)
                    .namespace(soapConfig.getNamespace())
                    .serviceName(soapConfig.getServiceName())
                    .portName(soapConfig.getPortName())
                    .operationName(nombreOperacion)
                    .username(cuit)  // Coloca el usuario de autenticación
                    .password(token)  // Coloca la contraseña de autenticación
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        if (Objects.equals(nombreOperacion, "ObtenerSucursalesCompletasRequest")){
            return (List<T>) soapClient.callServiceForList(SucursalBean.class, "ObtenerSucursalesCompletasResponse");
        }
        else if (Objects.equals(nombreOperacion, "ObtenerInformacionCompletaProductosRequest")){
            return (List<T>) soapClient.callServiceForList(ProductoCompletoBean.class, "ObtenerInformacionCompletaProductosResponse");
        }
        else if (Objects.equals(nombreOperacion, "ObtenerInformacionPreciosProductosRequest")){
            return (List<T>) soapClient.callServiceForList(PrecioProductoBean.class, "ObtenerInformacionPreciosProductosResponse");
        }

        return null;
    }

}


