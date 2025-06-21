package ar.edu.ubp.das.indecback.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.indecback.beans.IdiomaBean;
import ar.edu.ubp.das.indecback.repositories.RegionalizacionRepository;
import ar.edu.ubp.das.indecback.requests.TraduccionRequest;

@RestController
@RequestMapping("/regionalizacion")
public class RegionalizacionResources {
    @Autowired
    private RegionalizacionRepository regionalizacionRepository;

    
    // Endpoints para regionalizacion
    @GetMapping("/obtenerIdiomas")
    public ResponseEntity<List<IdiomaBean>> obtenerIdiomas() {
        List<IdiomaBean> idiomas = regionalizacionRepository.obtenerIdiomas();
        return ResponseEntity.ok(idiomas);
    }

    @PostMapping("/obtenerTraducciones")
    public ResponseEntity<Map<String, List<?>>> obtenerTraduccion(@RequestBody TraduccionRequest req) {
        Map<String, List<?>> traduccion = regionalizacionRepository.obtenerTraduccion(req.getCod_idioma());

        return ResponseEntity.ok(traduccion);
    }
    
}
