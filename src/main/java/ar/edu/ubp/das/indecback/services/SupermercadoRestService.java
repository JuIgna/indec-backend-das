package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.utils.Httpful;
import java.util.Map;

public class SupermercadoRestService implements SupermercadoService {

    private  String baseUrl;
    private  String username;  // Usuario de autenticación
    private  String password; // Contraseña de autenticación
    private Map<String, Object> config;

    public SupermercadoRestService(String baseUrl, String username, String password, Map<String, Object> config) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.config = config;
    }

    @Override
    public String invocarServicio (String nombreOperacion) {
        Map<String, String> operacion = (Map<String, String>) config.get(nombreOperacion);

        if (operacion == null){
            throw new IllegalArgumentException("No se ha encontrado el servicio: " + nombreOperacion);
        }

        Httpful client = new Httpful(baseUrl)
                .path(operacion.get("operacion"))
                .method(operacion.get("metodo"))
                .basicAuth(username, password);

        return client.execute();
    }
}
