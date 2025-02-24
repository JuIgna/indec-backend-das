package ar.edu.ubp.das.indecback.repositories;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.indecback.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IndecRepository {

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    // Metodo para obtener las categorias, para filtrar por categorias
    public List<CategoriaBean> obtenerCategorias() {
        return jdbcCallFactory.executeQuery("obtener_categorias_productos","dbo", "categorias", CategoriaBean.class);
    }

    // Metodo para obtener los productos de una categoria, para filtrar productos por categoria
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
        return jdbcCallFactory.executeQuery("obtener_productos_completos", "dbo", "productos_completos", ProductoBean.class);
    }
    
    // Obtener el nro de localidad de indec, para mapear localicadades en base al nom_localidad, cod_provincia y cod_pais
    public Integer obtenerNroLocalidad(String nomLocalidad, String codProvincia, String codPais) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nom_localidad", nomLocalidad)
                .addValue("cod_provincia", codProvincia)
                .addValue("cod_pais", codPais);

        // Llama al método de SimpleJdbcCallFactory para obtener el nro_localidad
        return jdbcCallFactory.executeQueryForSingleResult("obtener_nro_localidad", "dbo", params);
    }

    // Metodo para actualizar las sucursales en indec
    public void actualizarSucursal(String jsonSucursales, int nroSupermercado) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("json_sucursales", jsonSucursales)
                .addValue("nro_supermercado", nroSupermercado);

        jdbcCallFactory.execute("actualizar_sucursal", "dbo", params);
    }


    // Metodo para actualizar los productos en indec
    public void actualizarProductos(String jsonProductos) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("json_productos", jsonProductos);

        jdbcCallFactory.execute("actualizar_productos", "dbo", params);
    }


    // metodo para actualizar los precios de los productos en indec
    public void actualizarPreciosProductos (String jsonPrecios, int nroSupermercado){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("json_precios", jsonPrecios)
                .addValue("nro_supermercado", nroSupermercado);

        jdbcCallFactory.execute("actualizar_precios_productos", "dbo", params);
    }

    // Metodo para conseguir los precios minimos de los cod_barras seleccionados y mostrar en la tabla de comparador
    public List<PrecioMinProductoBean> obtenerPreciosMinimosProductos(List<String> codigosBarras) {
        // Convertir la lista de códigos de barras en una cadena separada por comas
        String codigos = String.join(",", codigosBarras);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("codigos_barras", codigos);

        return jdbcCallFactory.executeQuery("obtener_precios_minimos", "dbo", params, "precios_minimos", PrecioMinProductoBean.class
        );


    }

    //metodo para obtener paises
    public List<PaisBean> obtenerPaises () {
        return jdbcCallFactory.executeQuery("obtener_paises", "dbo", "paises", PaisBean.class);
    }

    // metodo para obtener provincias
    public List<ProvinciaBean> obtenerProvincias (String codPais) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", codPais);

        return jdbcCallFactory.executeQuery("obtener_provincias", "dbo", params, "provincias", ProvinciaBean.class);

    }

    // metodo para obtener localidades
    public List<LocalidadBean> obtenerLocalidades (LocalidadesRequest req){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", req.getCod_pais())
                .addValue("cod_provincia", req.getCod_provincia());

        return jdbcCallFactory.executeQuery("obtener_localidades", "dbo", params, "localidades", LocalidadBean.class);
    }


    // Metodo para obtener las sucursales del precio minimo de un supermercado
    public List<SucursalBean> obtenerSucursalesPreciosMinimosLocalidad (SucursalRequest req){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", req.getNro_localidad())
                .addValue("cod_provincia", req.getCod_barra())
                .addValue("nro_supermercado", req.getNro_supermercado())
                .addValue("precio", req.getPrecio());

        return jdbcCallFactory.executeQuery("obtener_sucursales_precios_minimos", "dbo", params, "sucursalesPreciosMinimosLocalidad", SucursalBean.class);

    }


    public List<SucursalBean> obtenerSucursalesPreciosMinimos(SucursalRequest req){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_barra", req.getCod_barra())
                .addValue("precio", req.getPrecio())
                .addValue("nro_supermercado", req.getNro_supermercado());

        return jdbcCallFactory.executeQuery("obtener_sucursales_por_producto_precio", "dbo", params, "sucursalesPreciosMinimos", SucursalBean.class);

    }

    // Obtener sucursales de una localidad
    public List<SucursalBean> obtenerSucursalesLocalidad (SucursalesLocalidadesRequest req){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", req.getNro_localidad());

        return jdbcCallFactory.executeQuery("obtener_sucursales_por_localidad", "dbo", params, "sucursalesLocalidad", SucursalBean.class);

    }

    // Metodo para conseguir los precios minimos de los cod_barras seleccionados y mostrar en la tabla de comparador
    public List<PrecioMinProductoBean> obtenerPreciosMinimosProductos(List<String> codigosBarras, int nroLocalidad) {
        // Convertir la lista de códigos de barras en una cadena separada por comas
        String codigos = String.join(",", codigosBarras);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", nroLocalidad)
                .addValue("codigos_barras", codigos);

        return jdbcCallFactory.executeQuery("comparar_precios", "dbo", params, "precios_minimos", PrecioMinProductoBean.class
        );
    }

    // Para regionalizacion
    public List<IdiomaBean> obtenerIdiomas (){
        return jdbcCallFactory.executeQuery("obtener_idiomas","dbo", "idiomas", IdiomaBean.class);
    }

    //obtener traduccion
    public Map<String, List<?>> obtenerTraduccion(String codIdioma) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_idioma", codIdioma);

        Map<String, Object> resultados = jdbcCallFactory.executeQueryWithMultipleResults(
                "obtener_traduccion", "dbo", params
        );

        Map<String, List<?>> resultado = new HashMap<>();
        resultado.put("rubros", (List<RubroBean>) resultados.get("rubros"));
        resultado.put("categorias", (List<CategoriaBean>) resultados.get("categorias"));
        resultado.put("tipos_productos", (List<TipoProductoBean>) resultados.get("tipos_productos"));

        return resultado;
    }

}
