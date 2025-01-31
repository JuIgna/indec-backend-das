package ar.edu.ubp.das.indecback.repositories;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.indecback.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndecRepository {

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    // Método para obtener los códigos de barra de productos desde la base de datos
    public List<String> obtenerCodigosBarras() {
        return jdbcCallFactory.executeQuery("obtener_codigos_barras", "dbo", "codigos_barras", String.class);
    }

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
    public void actualizarSucursal(SucursalBean sucursal, int nroSupermercado, int nroLocalidad) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_supermercado", nroSupermercado)
                .addValue("nro_sucursal", sucursal.getNro_sucursal())
                .addValue("nom_sucursal", sucursal.getNom_sucursal())
                .addValue("calle", sucursal.getCalle())
                .addValue("nro_calle", sucursal.getNro_calle())
                .addValue("telefonos", sucursal.getTelefonos())
                .addValue("coord_latitud", sucursal.getCoord_latitud())
                .addValue("coord_longitud", sucursal.getCoord_longitud())
                .addValue("horario_sucursal", sucursal.getHorario_sucursal())
                .addValue("servicios_disponibles", sucursal.getServicios_disponibles())
                .addValue("nro_localidad", nroLocalidad)
                .addValue("habilitada", sucursal.getHabilitada());

        jdbcCallFactory.execute("actualizar_sucursal", "dbo", params);
    }

    // Metodo para actualizar los productos en indec
    public void actualizarProductos (ProductoCompletoBean producto){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_barra", producto.getCod_barra())
                .addValue("nom_producto", producto.getNom_producto())
                .addValue("desc_producto", producto.getDesc_producto())
                .addValue("nom_categoria", producto.getNom_categoria())
                .addValue("nom_rubro", producto.getNom_rubro())
                .addValue("nom_marca", producto.getNom_marca())
                .addValue("nom_tipo_producto", producto.getNom_tipo_producto())
                .addValue("imagen", producto.getImagen())
                .addValue("tipo_producto_marca_vigente", producto.getTipo_producto_marca_vigente())
                .addValue("producto_vigente", producto.getProducto_vigente());

        jdbcCallFactory.execute("actualizar_productos", "dbo", params);
    }

    // metodo para actualizar los precios de los productos en indec
    public void actualizarPreciosProductos (PrecioProductoBean precioProducto, int nroSupermercado){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_supermercado", nroSupermercado)
                .addValue("nro_sucursal", precioProducto.getNro_sucursal())
                .addValue("cod_barra", precioProducto.getCod_barra())
                .addValue("precio", precioProducto.getPrecio());

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

    // Metodos para insertar o actualizar los paises, provincia y localidades
    public void actualizarPais (PaisBean pais){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", pais.getCod_pais())
                .addValue("nom_pais", pais.getNom_pais());

        jdbcCallFactory.execute("actualizar_pais", "dbo", params);
    }

    public void actualizarProvincia (ProvinciaIndecBean provincia){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", provincia.getCod_pais())
                .addValue("cod_provincia", provincia.getCod_provincia())
                .addValue("nom_provincia", provincia.getNom_provincia());

        jdbcCallFactory.execute("actualizar_provincia", "dbo", params);
    }

    public void actualizarLocalidad (LocalidadIndecBean localidad){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", localidad.getNro_localidad())
                .addValue("nom_localidad", localidad.getNom_localidad())
                .addValue("cod_pais", localidad.getCod_pais())
                .addValue("cod_provincia", localidad.getCod_provincia());

        jdbcCallFactory.execute("actualizar_localidad", "dbo", params);
    }

    public void actualizarSupermercados (SupermercadoBean req){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cuit", req.getCuit())
                .addValue("razon_social", req.getRazon_social())
                .addValue("url_servicio", req.getRazon_social())
                .addValue("tipo_servicio", req.getTipo_servicio())
                .addValue("token_servicio", req.getToken_servicio());

        jdbcCallFactory.execute("actualizar_supermercado", "dbo", params);
    }




}
