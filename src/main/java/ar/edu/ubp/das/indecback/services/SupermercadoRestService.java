package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.utils.Httpful;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SupermercadoRestService implements SupermercadoService {

    private final String baseUrl = "http://localhost:8085/api/v1/supermercado1";
    private final String username = "rest123";  // Usuario de autenticación
    private final String password = "rest123";  // Contraseña de autenticación



    @Override
    public List<SucursalBean> obtenerInformacionCompletaSucursales() {
        Httpful client = new Httpful(baseUrl)
                .path("/sucursales")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<SucursalBean>>() {
        }.getType());
    }

    @Override
    public List<ProductoCompletoBean> obtenerInformacionCompletaProductos() {
        Httpful client = new Httpful(baseUrl)
                .path("/informacionCompletaProductos")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<ProductoCompletoBean>>() {
        }.getType());
    }

    @Override
    public List<PrecioProductoBean> obtenerInformacionPrecioProductos(){
        Httpful client = new Httpful(baseUrl)
                .path("/informacionPreciosProductos")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<PrecioProductoBean>>() {
        }.getType());
    }

    @Override
    public List<PaisBean> obtenerPaises (){
        Httpful client = new Httpful(baseUrl)
                .path("/paises")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<PaisBean>>() {
        }.getType());
    }

    @Override
    public List<ProvinciaIndecBean> obtenerProvincias (){
        Httpful client = new Httpful(baseUrl)
                .path("/provincias")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<ProvinciaIndecBean>>() {
        }.getType());
    }

    @Override
    public List<LocalidadIndecBean> obtenerLocalidades (){
        Httpful client = new Httpful(baseUrl)
                .path("/localidades")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<LocalidadIndecBean>>() {
        }.getType());
    }

    @Override
    public List<SupermercadoBean> obtenerSupermercados () {
        Httpful client = new Httpful(baseUrl)
                .path("/supermercado")
                .method("GET")
                .basicAuth(username, password);

        return client.execute(new TypeToken<List<SupermercadoBean>>() {
        }.getType());

    }

}



