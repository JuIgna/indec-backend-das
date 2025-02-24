package ar.edu.ubp.das.indecback.resources;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.repositories.IndecRepository;
import ar.edu.ubp.das.indecback.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/indec")
public class IndecResources {

    @Autowired
    private IndecRepository indecRepository;

    @GetMapping("/infoProductos")
    public ResponseEntity<List<ProductoBean>> obtenerProductosCompletos() {
        List<ProductoBean> productos = indecRepository.obtenerProductosCompletos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoriasProductos")
    public ResponseEntity<List<CategoriaBean>> obtenerCategoriasProductos() {
        List<CategoriaBean> categoriasProductos = indecRepository.obtenerCategorias();
        return ResponseEntity.ok(categoriasProductos);
    }

    @PostMapping("/productosDeCategorias")
    public ResponseEntity<List<ProductoBean>> obtenerProductosDeCategorias(@RequestBody CategoriaRequest req) {
        if (req.getNro_categoria() < 0) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<ProductoBean> productosDeCategoria = indecRepository.obtenerProductosDeCategoria(req);
        return ResponseEntity.ok(productosDeCategoria);

    }

    @PostMapping("/compararPrecios")
    public ResponseEntity<List<PrecioMinProductoBean>> obtenerPreciosMinimos(@RequestBody CompararPreciosRequest req) {
        if (req.getCodigos_barras() == null || req.getCodigos_barras().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<PrecioMinProductoBean> preciosMinimos = indecRepository.obtenerPreciosMinimosProductos(req.getCodigos_barras(), req.getNro_localidad());
        return ResponseEntity.ok(preciosMinimos);
    }

    @GetMapping ("/obtenerPaises")
    public ResponseEntity<List<PaisBean>> obtenerPaises() {
        List<PaisBean> paises = indecRepository.obtenerPaises();
        return ResponseEntity.ok(paises);
    }

    @PostMapping ("/obtenerProvincias")
    public ResponseEntity<List<ProvinciaBean>> obtenerProvincias(@RequestBody ProvinciasRequest req) {
        List<ProvinciaBean> provincias = indecRepository.obtenerProvincias(req.getCod_pais());
        return ResponseEntity.ok(provincias);

    }

    @PostMapping ("/obtenerLocalidades")
    public ResponseEntity<List<LocalidadBean>> obtenerLocalidades(@RequestBody LocalidadesRequest req) {
        List<LocalidadBean> localidades = indecRepository.obtenerLocalidades(req);

        return ResponseEntity.ok(localidades);
    }


    @PostMapping ("/obtenerSucursalesLocalidad")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesLocalidad(@RequestBody SucursalesLocalidadesRequest req) {
        List<SucursalBean> sucursales = indecRepository.obtenerSucursalesLocalidad(req);

        return ResponseEntity.ok(sucursales);
    }

    @PostMapping ("/obtenerSucursalesProductoPrecioMinimo")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesPrecioMinimo (@RequestBody SucursalRequest req) {
        List<SucursalBean> sucursales = indecRepository.obtenerSucursalesPreciosMinimos(req);

        return ResponseEntity.ok(sucursales);
    }

    @PostMapping("/obtenerSucursalesProductoPrecioMinimoLocalidad")
    public ResponseEntity<List<SucursalBean>> obtenerSucursalesPrecioMinimoLocalidad(@RequestBody SucursalRequest req) {
        List<SucursalBean> sucursales = indecRepository.obtenerSucursalesPreciosMinimosLocalidad(req);

        return ResponseEntity.ok(sucursales);
    }

}
