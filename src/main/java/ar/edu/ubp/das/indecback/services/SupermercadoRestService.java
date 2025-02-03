package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.utils.Httpful;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;

public class SupermercadoRestService implements SupermercadoService {

    private  String baseUrl;
    private  String username;  // Usuario de autenticación
    private  String password;  // Contraseña de autenticación


    public SupermercadoRestService(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }


    @Override
    public <T> List <T> invocarServicio (Class<T> tipoRespuesta, String nombreOperacion, String metodo) {
        Httpful client = new Httpful(baseUrl)
                .path(nombreOperacion)
                .method(metodo)
                .basicAuth(username, password);

        if (Objects.equals(nombreOperacion, "/sucursales")){
            return client.execute(new TypeToken<List<SucursalBean>>() {
            }.getType());
        }
        else if (Objects.equals(nombreOperacion, "/informacionCompletaProductos")){
            return client.execute(new TypeToken<List<ProductoCompletoBean>>() {
            }.getType());
        }
        else if (Objects.equals(nombreOperacion, "/informacionPreciosProductos")){
            return client.execute(new TypeToken<List<PrecioProductoBean>>() {
            }.getType());
        }

        return null;
    }
}
