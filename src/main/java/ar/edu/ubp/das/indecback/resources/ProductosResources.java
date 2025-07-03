package ar.edu.ubp.das.indecback.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.indecback.beans.CategoriaBean;
import ar.edu.ubp.das.indecback.beans.ProductoBean;
import ar.edu.ubp.das.indecback.beans.SupermercadoBean;
import ar.edu.ubp.das.indecback.repositories.ProductosRepository;
import ar.edu.ubp.das.indecback.requests.CategoriaRequest;

@RestController
@RequestMapping("/productos")
public class ProductosResources {
    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/obtenerSupermercados")
    public ResponseEntity<List<SupermercadoBean>> obtenerSupermercados() {
        List<SupermercadoBean> supermercados = productosRepository.obtenerSupermercados();
        return ResponseEntity.ok(supermercados);
    }

    @GetMapping("/infoProductos")
    public ResponseEntity<List<ProductoBean>> obtenerProductosCompletos() {
        List<ProductoBean> productos = productosRepository.obtenerProductosCompletos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoriasProductos")
    public ResponseEntity<List<CategoriaBean>> obtenerCategoriasProductos() {
        List<CategoriaBean> categoriasProductos = productosRepository.obtenerCategorias();
        return ResponseEntity.ok(categoriasProductos);
    }

    @PostMapping("/productosDeCategorias")
    public ResponseEntity<List<ProductoBean>> obtenerProductosDeCategorias(@RequestBody CategoriaRequest req) {
        if (req.getNro_categoria() < 0) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<ProductoBean> productosDeCategoria = productosRepository.obtenerProductosDeCategoria(req);
        return ResponseEntity.ok(productosDeCategoria);

    }

}
