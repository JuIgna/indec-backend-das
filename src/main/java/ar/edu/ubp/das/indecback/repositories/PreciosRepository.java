package ar.edu.ubp.das.indecback.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.indecback.beans.PrecioMinProductoBean;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;

@Repository
public class PreciosRepository {
    @Autowired
    SimpleJdbcCallFactory jdbcCallFactory;

    // metodo para actualizar los precios de los productos en indec
    public void actualizarPreciosProductos(String jsonPrecios, int nroSupermercado) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("json_precios", jsonPrecios)
                .addValue("nro_supermercado", nroSupermercado);

        jdbcCallFactory.execute("actualizar_precios_productos", "dbo", params);
    }

    // Metodo para conseguir los precios minimos de los cod_barras seleccionados y
    // mostrar en la tabla de comparador
    public List<PrecioMinProductoBean> obtenerPreciosMinimosProductos(List<String> codigosBarras) {
        // Convertir la lista de códigos de barras en una cadena separada por comas
        String codigos = String.join(",", codigosBarras);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("codigos_barras", codigos);

        return jdbcCallFactory.executeQuery("obtener_precios_minimos", "dbo", params, "precios_minimos",
                PrecioMinProductoBean.class);

    }

    // Metodo para conseguir los precios minimos de los cod_barras seleccionados y
    // mostrar en la tabla de comparador
    public List<PrecioMinProductoBean> obtenerPreciosMinimosProductos(List<String> codigosBarras, int nroLocalidad) {
        // Convertir la lista de códigos de barras en una cadena separada por comas
        String codigos = String.join(",", codigosBarras);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_localidad", nroLocalidad)
                .addValue("codigos_barras", codigos);

        return jdbcCallFactory.executeQuery("comparar_precios", "dbo", params, "precios_minimos",
                PrecioMinProductoBean.class);
    }

}
