package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.SupermercadoBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SupermercadoServiceFactory {
    public SupermercadoService getService(SupermercadoBean supermercado, Map<String, Object> config) throws Exception {
        String tipoServicio = supermercado.getTipo_servicio();

        if ("REST".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoRestService(
                    supermercado.getUrl_servicio(),
                    supermercado.getCuit(),
                    supermercado.getToken_servicio(),
                    (Map<String, Object>) config.get("rest")
            );
        } else if ("SOAP".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoSoapService(
                    supermercado.getUrl_servicio(),
                    supermercado.getCuit(),
                    supermercado.getToken_servicio(),
                    (Map<String, Object>) config.get("soap")
            );
        } else {
            throw new IllegalArgumentException("Tipo de servicio no soportado: " + tipoServicio);
        }
    }
}

