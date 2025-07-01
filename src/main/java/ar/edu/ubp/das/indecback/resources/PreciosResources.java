package ar.edu.ubp.das.indecback.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.indecback.beans.PrecioMinProductoBean;
import ar.edu.ubp.das.indecback.repositories.PreciosRepository;
import ar.edu.ubp.das.indecback.requests.CompararPreciosRequest;

@RestController
@RequestMapping("/precios")
public class PreciosResources {
    @Autowired
    private PreciosRepository preciosRepository;

    
    @PostMapping("/compararPrecios")
    public ResponseEntity<List<PrecioMinProductoBean>> obtenerPreciosMinimos(@RequestBody CompararPreciosRequest req) {

        System.out.println("REQUEST RECIBIDO: " + req.getCodigos_barras() + ", " + req.getNro_localidad());
        if (req.getCodigos_barras() == null || req.getCodigos_barras().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<PrecioMinProductoBean> preciosMinimos = preciosRepository.obtenerPreciosMinimosProductos(req.getCodigos_barras(), req.getNro_localidad());
        return ResponseEntity.ok(preciosMinimos);

    }
    
}
