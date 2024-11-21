package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.utils.SOAPClient;

import java.util.List;

public class SupermercadoSoapService implements SupermercadoService {

    private final SOAPClient soapClientSucursalesCompletas;

    private final SOAPClient soapClientPaises;
    private final SOAPClient soapClientProvincias;
    private final SOAPClient soapClientLocalidades;
    private final SOAPClient soapClientProductos;
    private final SOAPClient soapClientPreciosProductos;

    public SupermercadoSoapService() {
        this.soapClientPaises = new SOAPClient.SOAPClientBuilder()
                .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                .serviceName("SupermercadoWSPortService")
                .portName("SupermercadoWSPortSoap11")
                .operationName("ObtenerPaisesRequest")  // Operation para obtener países
                .username("WS123")
                .password("WS123")
                .build();

        this.soapClientProvincias = new SOAPClient.SOAPClientBuilder()
                .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                .serviceName("SupermercadoWSPortService")
                .portName("SupermercadoWSPortSoap11")
                .operationName("ObtenerProvinciasRequest")  // Operation para obtener provincias
                .username("WS123")
                .password("WS123")
                .build();

        this.soapClientLocalidades = new SOAPClient.SOAPClientBuilder()
                .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                .serviceName("SupermercadoWSPortService")
                .portName("SupermercadoWSPortSoap11")
                .operationName("ObtenerLocalidadesRequest")  // Operation para obtener localidades
                .username("WS123")
                .password("WS123")
                .build();

            this.soapClientSucursalesCompletas = new SOAPClient.SOAPClientBuilder()
                    .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                    .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                    .serviceName("SupermercadoWSPortService")
                    .portName("SupermercadoWSPortSoap11")
                    .operationName("ObtenerSucursalesCompletasRequest")
                    .username("WS123")  // Coloca el usuario de autenticación
                    .password("WS123")  // Coloca la contraseña de autenticación
                    .build();

        this.soapClientProductos = new SOAPClient.SOAPClientBuilder()
                .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                .serviceName("SupermercadoWSPortService") // fijate que este bien
                .portName("SupermercadoWSPortSoap11") // fijate que este bien
                .operationName("ObtenerInformacionCompletaProductosRequest") // Aca va el request
                .username("WS123")  // Coloca el usuario de autenticación
                .password("WS123")  // Coloca la contraseña de autenticación
                .build();

        this.soapClientPreciosProductos = new SOAPClient.SOAPClientBuilder()
                .wsdlUrl("http://localhost:8080/services/supermercado.wsdl")
                .namespace("http://services.supermercadows.das.ubp.edu.ar/")
                .serviceName("SupermercadoWSPortService") // fijate que este bien
                .portName("SupermercadoWSPortSoap11") // fijate que este bien
                .operationName("ObtenerInformacionPreciosProductosRequest") // Aca va el request
                .username("WS123")  // Coloca el usuario de autenticación
                .password("WS123")  // Coloca la contraseña de autenticación
                .build();


    }
    @Override
    public List<ProductoCompletoBean> obtenerInformacionCompletaProductos() {
        return soapClientProductos.callServiceForList(ProductoCompletoBean.class, "ObtenerInformacionCompletaProductosResponse");
        // El response va como el segunto parametro
    }

    @Override
    public List<SucursalBean> obtenerInformacionCompletaSucursales (){
        return soapClientSucursalesCompletas.callServiceForList(SucursalBean.class, "ObtenerSucursalesCompletasResponse");
    }

    @Override
    public List<PrecioProductoBean> obtenerInformacionPrecioProductos (){
        return soapClientPreciosProductos.callServiceForList(PrecioProductoBean.class, "ObtenerInformacionPreciosProductosResponse");
    }

    @Override
    public List<PaisBean> obtenerPaises() {
        return soapClientPaises.callServiceForList(PaisBean.class, "ObtenerPaisesResponse");
    }

    @Override
    public List<ProvinciaIndecBean> obtenerProvincias() {
        return soapClientProvincias.callServiceForList(ProvinciaIndecBean.class, "ObtenerProvinciasResponse");
    }

    @Override
    public List<LocalidadIndecBean> obtenerLocalidades() {
        return soapClientLocalidades.callServiceForList(LocalidadIndecBean.class, "ObtenerLocalidadesResponse");
    }


    @Override
    public List<SupermercadoBean> obtenerSupermercados() {
        return List.of();
    }


}
