package ar.edu.ubp.das.supermercadorest2.resources;

import ar.edu.ubp.das.supermercadorest2.beans.*;
import ar.edu.ubp.das.supermercadorest2.repositories.SupermercadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supermercado3")
public class SupermercadoResources {

    @Autowired
    private SupermercadoRepository supermercadoRepository;

    @GetMapping(value = "/sucursales", produces = "application/json")
    public ResponseEntity<List<SucursalBean>> obtenerSucursales() {
        List<SucursalBean> sucursales = supermercadoRepository.obtenerSucursalesCompletas();
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping(value = "/informacionCompletaProductos", produces = "application/json")
    public ResponseEntity<List<ProductoCompletoBean>> obtenerInformacionCompletaProductos() {
        List<ProductoCompletoBean> productosCompletos = supermercadoRepository.obtenerProductosCompletos();
        return ResponseEntity.ok(productosCompletos);
    }

    @GetMapping(value = "/informacionPreciosProductos", produces = "application/json")
    public ResponseEntity<List<PrecioProductoBean>> obtenerInformacionPreciosProductos() {
        List<PrecioProductoBean> precioProductos = supermercadoRepository.obtenerPreciosProductos();
        return ResponseEntity.ok(precioProductos);
    }

}
