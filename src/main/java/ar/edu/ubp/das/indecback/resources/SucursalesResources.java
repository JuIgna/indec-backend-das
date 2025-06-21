package ar.edu.ubp.das.indecback.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.indecback.beans.LocalidadBean;
import ar.edu.ubp.das.indecback.beans.PaisBean;
import ar.edu.ubp.das.indecback.beans.ProvinciaBean;
import ar.edu.ubp.das.indecback.beans.SucursalBean;
import ar.edu.ubp.das.indecback.repositories.SucursalesRepository;
import ar.edu.ubp.das.indecback.requests.LocalidadesRequest;
import ar.edu.ubp.das.indecback.requests.ProvinciasRequest;
import ar.edu.ubp.das.indecback.requests.SucursalRequest;
import ar.edu.ubp.das.indecback.requests.SucursalesLocalidadesRequest;

@RestController
@RequestMapping("/ubicaciones")
public class SucursalesResources {
    
    @Autowired
    private SucursalesRepository sucursalesRepository;

    @GetMapping("/obtenerPaises")
    public ResponseEntity<List<PaisBean>> obtenerPaises() {
        List<PaisBean> paises = sucursalesRepository.obtenerPaises();
        return ResponseEntity.ok(paises);
    }

    @PostMapping("/obtenerProvincias")
    public ResponseEntity<List<ProvinciaBean>> obtenerProvincias(@RequestBody ProvinciasRequest req) {
        List<ProvinciaBean> provincias = sucursalesRepository.obtenerProvincias(req.getCod_pais());
        return ResponseEntity.ok(provincias);

    }

    @PostMapping("/obtenerLocalidades")
    public ResponseEntity<List<LocalidadBean>> obtenerLocalidades(@RequestBody LocalidadesRequest req) {
        List<LocalidadBean> localidades = sucursalesRepository.obtenerLocalidades(req);

        return ResponseEntity.ok(localidades);
    }

    @PostMapping("/obtenerSucursalesLocalidad")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesLocalidad(
            @RequestBody SucursalesLocalidadesRequest req) {
        List<SucursalBean> sucursales = sucursalesRepository.obtenerSucursalesLocalidad(req);

        return ResponseEntity.ok(sucursales);
    }

    @PostMapping("/obtenerSucursalesProductoPrecioMinimo")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesPrecioMinimo(@RequestBody SucursalRequest req) {
        List<SucursalBean> sucursales = sucursalesRepository.obtenerSucursalesPreciosMinimos(req);

        return ResponseEntity.ok(sucursales);
    }

    @PostMapping("/obtenerSucursalesProductoPrecioMinimoLocalidad")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesPrecioMinimoLocalidad(@RequestBody SucursalRequest req) {
        List<SucursalBean> sucursales = sucursalesRepository.obtenerSucursalesPreciosMinimosLocalidad(req);

        return ResponseEntity.ok(sucursales);
    }

}
