package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.SupermercadoIndecBean;
import org.springframework.stereotype.Component;

@Component
public class SupermercadoServiceFactory {

    public SupermercadoService getService(SupermercadoIndecBean supermercado) {
        String tipoServicio = supermercado.getTipo_servicio();

        if ("REST".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoRestService();
        } else if ("SOAP".equalsIgnoreCase(tipoServicio)) {
            return new SupermercadoSoapService();
        } else {
            throw new IllegalArgumentException("Tipo de servicio no soportado: " + tipoServicio);
        }
    }
}
