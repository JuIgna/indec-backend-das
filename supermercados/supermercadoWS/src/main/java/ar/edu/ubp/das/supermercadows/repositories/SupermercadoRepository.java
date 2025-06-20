package ar.edu.ubp.das.supermercadows.repositories;

// Sin cambios para el agregado de seguridad
import ar.edu.ubp.das.supermercadows.beans.*;
import ar.edu.ubp.das.supermercadows.components.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SupermercadoRepository {

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public List<SucursalBean> obtenerSucursalesCompletas() {
        return jdbcCallFactory.executeQuery("obtener_sucursales_completas", "dbo", "sucursales", SucursalBean.class);
    }

    public List<ProductoCompletoBean> obtenerProductosCompletos() {
        return jdbcCallFactory.executeQuery("obtener_informacion_productos_completa", "dbo", "productosCompletos", ProductoCompletoBean.class);
    }

    public List<PrecioProductoBean> obtenerPreciosProductos(){
        return jdbcCallFactory.executeQuery("obtener_precios_productos", "dbo", "preciosProductos", PrecioProductoBean.class);
    }
}
