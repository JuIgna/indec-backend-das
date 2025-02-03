package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;

import java.util.List;


public interface SupermercadoService {

    // Metodo sobrecargado para invocar un servicio de tipo Rest
    <T> List <T> invocarServicio (Class<T> tipoRespuesta, String nombreOperacion, String metodo);

}


