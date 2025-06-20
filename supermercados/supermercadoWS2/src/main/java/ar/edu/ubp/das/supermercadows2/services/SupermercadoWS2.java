package ar.edu.ubp.das.supermercadows2.services;

/* Para la seguridad se añaden estas lineas

@WebService (serviceName = "SupermercadoWS", targetNamespace = "http://services.supermercadows.das.ubp.edu.ar/")

    @WebMethod (operationName = "ObtenerProductos")
    @RequestWrapper (localName = "ObtenerProductosRequest")
    @ResponseWrapper(localName = "ObtenerProductosResponse")
    @WebResult(name = "ProductosResponse")

    antes solo era el @WebMethod sin nada.
*/

import ar.edu.ubp.das.supermercadows2.beans.*;
import ar.edu.ubp.das.supermercadows2.repositories.SupermercadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@WebService (serviceName = "SupermercadoWS2", targetNamespace = "http://services.supermercadows2.das.ubp.edu.ar/")
public class SupermercadoWS2 {

    @Autowired
    private SupermercadoRepository supermercadoRepository;

    @WebMethod(operationName = "ObtenerSucursalesCompletas")
    @RequestWrapper(localName = "ObtenerSucursalesCompletasRequest")
    @ResponseWrapper(localName = "ObtenerSucursalesCompletasResponse")
    @WebResult(name = "SucursalesCompletasResponse")
    public String obtenerSucursalesCompletas() {
        try {
            List<SucursalBean> sucursales = supermercadoRepository.obtenerSucursalesCompletas();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(sucursales);  // Convertimos a JSON
        } catch (Exception e) {
            return "{\"error\":\"Error al generar JSON\"}";  // Respuesta de error en JSON
        }
    }

    @WebMethod(operationName = "ObtenerInformacionCompletaProductos")
    @RequestWrapper(localName = "ObtenerInformacionCompletaProductosRequest")
    @ResponseWrapper(localName = "ObtenerInformacionCompletaProductosResponse")
    @WebResult(name = "InformacionCompletaProductosResponse")
    public String obtenerInformacionCompletaProductos() {
        try {
            List<ProductoCompletoBean> productos = supermercadoRepository.obtenerProductosCompletos();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(productos);
        } catch (Exception e) {
            return "{\"error\":\"Error al generar JSON\"}";
        }
    }

    @WebMethod(operationName = "ObtenerInformacionPreciosProductos")
    @RequestWrapper(localName = "ObtenerInformacionPreciosProductosRequest")
    @ResponseWrapper(localName = "ObtenerInformacionPreciosProductosResponse")
    @WebResult(name = "InformacionPreciosProductosResponse")
    public String obtenerInformacionPreciosProductos() {
        try {
            List<PrecioProductoBean> precios = supermercadoRepository.obtenerPreciosProductos();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(precios);
        } catch (Exception e) {
            return "{\"error\":\"Error al generar JSON\"}";
        }
    }
}
