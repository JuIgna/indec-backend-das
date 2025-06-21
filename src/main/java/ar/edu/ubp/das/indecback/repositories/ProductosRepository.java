package ar.edu.ubp.das.indecback.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import ar.edu.ubp.das.indecback.beans.CategoriaBean;
import ar.edu.ubp.das.indecback.beans.ProductoBean;
import ar.edu.ubp.das.indecback.beans.SupermercadoBean;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.indecback.requests.CategoriaRequest;

public class ProductosRepository {
    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    // Metodo para obtener las categorias, para filtrar por categorias
    public List<CategoriaBean> obtenerCategorias() {
        return jdbcCallFactory.executeQuery("obtener_categorias_productos", "dbo", "categorias", CategoriaBean.class);
    }

    // Metodo para obtener los productos de una categoria, para filtrar productos
    // por categoria
    public List<ProductoBean> obtenerProductosDeCategoria(CategoriaRequest req) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_categoria", req.getNro_categoria());
        return jdbcCallFactory.executeQuery("obtener_productos_de_categorias", "dbo",
                params, "productos_de_categorias", ProductoBean.class);
    }

    // Método para obtener supermercados de indec
    public List<SupermercadoBean> obtenerSupermercados() {
        return jdbcCallFactory.executeQuery("obtener_supermercados", "dbo", "supermercados", SupermercadoBean.class);
    }

    // Método para obtener la información completa de los productos
    public List<ProductoBean> obtenerProductosCompletos() {
        return jdbcCallFactory.executeQuery("obtener_productos_completos", "dbo", "productos_completos",
                ProductoBean.class);
    }

    // Metodo para actualizar los productos en indec
    public void actualizarProductos(String jsonProductos, int nroSupermercado) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("json_productos", jsonProductos)
                .addValue("nro_supermercado", nroSupermercado);

        jdbcCallFactory.execute("actualizar_productos", "dbo", params);
    }

}
