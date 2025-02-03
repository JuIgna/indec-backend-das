package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.SupermercadoBean;
import org.springframework.stereotype.Component;

@Component
public class SupermercadoServiceFactory {

    public SupermercadoService getService(SupermercadoBean supermercado) throws Exception {
        String tipoServicio = supermercado.getTipo_servicio();

        if ("REST".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoRestService(
                    supermercado.getUrl_servicio(),
                    supermercado.getCuit(),
                    supermercado.getToken_servicio()
            );
        } else if ("SOAP".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoSoapService(
                    supermercado.getUrl_servicio(),
                    supermercado.getCuit(),
                    supermercado.getToken_servicio()
            );
        } else {
            throw new IllegalArgumentException("Tipo de servicio no soportado: " + tipoServicio);
        }
    }
}
