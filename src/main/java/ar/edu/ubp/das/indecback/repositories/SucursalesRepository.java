package ar.edu.ubp.das.indecback.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.indecback.beans.LocalidadBean;
import ar.edu.ubp.das.indecback.beans.PaisBean;
import ar.edu.ubp.das.indecback.beans.ProvinciaBean;
import ar.edu.ubp.das.indecback.beans.SucursalBean;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;
import ar.edu.ubp.das.indecback.requests.LocalidadesRequest;
import ar.edu.ubp.das.indecback.requests.SucursalRequest;
import ar.edu.ubp.das.indecback.requests.SucursalesLocalidadesRequest;

@Repository
public class SucursalesRepository {
    @Autowired
    SimpleJdbcCallFactory jdbcCallFactory;

    // metodo para obtener paises
    public List<PaisBean> obtenerPaises() {
        return jdbcCallFactory.executeQuery("obtener_paises", "dbo", "paises", PaisBean.class);
    }

    // metodo para obtener provincias
    public List<ProvinciaBean> obtenerProvincias(String codPais) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", codPais);

        return jdbcCallFactory.executeQuery("obtener_provincias", "dbo", params, "provincias", ProvinciaBean.class);

    }

    // metodo para obtener localidades
    public List<LocalidadBean> obtenerLocalidades(LocalidadesRequest req) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_pais", req.getCod_pais())
                .addValue("cod_provincia", req.getCod_provincia());

        return jdbcCallFactory.executeQuery("obtener_localidades", "dbo", params, "localidades", LocalidadBean.class);
    }

    // Obtener sucursales de una localidad
    public List<SucursalBean> obtenerSucursalesLocalidad(SucursalesLocalidadesRequest req) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", req.getNro_localidad());

        return jdbcCallFactory.executeQuery("obtener_sucursales_por_localidad", "dbo", params, "sucursalesLocalidad",
                SucursalBean.class);

    }

    // Obtener el nro de localidad de indec, para mapear localicadades en base al
    // nom_localidad, cod_provincia y cod_pais
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

    // Metodo para obtener las sucursales del precio minimo de un supermercado
    public List<SucursalBean> obtenerSucursalesPreciosMinimosLocalidad(SucursalRequest req) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", req.getNro_localidad())
                .addValue("cod_provincia", req.getCod_barra())
                .addValue("nro_supermercado", req.getNro_supermercado())
                .addValue("precio", req.getPrecio());

        return jdbcCallFactory.executeQuery("obtener_sucursales_precios_minimos", "dbo", params,
                "sucursalesPreciosMinimosLocalidad", SucursalBean.class);

    }

    public List<SucursalBean> obtenerSucursalesPreciosMinimos(SucursalRequest req) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_barra", req.getCod_barra())
                .addValue("precio", req.getPrecio())
                .addValue("nro_supermercado", req.getNro_supermercado());

        return jdbcCallFactory.executeQuery("obtener_sucursales_por_producto_precio", "dbo", params,
                "sucursalesPreciosMinimos", SucursalBean.class);

    }

}
