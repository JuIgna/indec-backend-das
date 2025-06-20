
package ar.edu.ubp.das.supermercadows2.endpoints;

// Para seguridad no hay cambios, si se busca regenerar comentar toda la clase
import ar.edu.ubp.das.supermercadows2.services.SupermercadoWS2;
import ar.edu.ubp.das.supermercadows2.services.jaxws.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SupermercadoEndpoint {
    private static final String NAMESPACE_URI = "http://services.supermercadows2.das.ubp.edu.ar/";

    @Autowired
    private SupermercadoWS2 service;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObtenerSucursalesCompletasRequest")
    @ResponsePayload
    public ObtenerSucursalesCompletasResponse obtenerSucursalesCompletas() {

        String sucursales = service.obtenerSucursalesCompletas();

        ObtenerSucursalesCompletasResponse response = new ObtenerSucursalesCompletasResponse();
        response.setSucursalesCompletasResponse(sucursales);
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObtenerInformacionCompletaProductosRequest")
    @ResponsePayload
    public ObtenerInformacionCompletaProductosResponse obtenerInformacionCompletaProductos () {

        String productos = service.obtenerInformacionCompletaProductos();

        ObtenerInformacionCompletaProductosResponse response = new ObtenerInformacionCompletaProductosResponse();
        response.setInformacionCompletaProductosResponse(productos);
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObtenerInformacionPreciosProductosRequest")
    @ResponsePayload
    public ObtenerInformacionPreciosProductosResponse obtenerInformacionPreciosProductos () {

        String preciosProductos = service.obtenerInformacionPreciosProductos();

        ObtenerInformacionPreciosProductosResponse response = new ObtenerInformacionPreciosProductosResponse();
        response.setInformacionPreciosProductosResponse(preciosProductos);
        return response;

    }

}
