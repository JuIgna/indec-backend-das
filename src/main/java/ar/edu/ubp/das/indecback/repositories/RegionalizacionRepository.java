package ar.edu.ubp.das.indecback.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.indecback.beans.CategoriaBean;
import ar.edu.ubp.das.indecback.beans.IdiomaBean;
import ar.edu.ubp.das.indecback.beans.RubroBean;
import ar.edu.ubp.das.indecback.beans.TipoProductoBean;
import ar.edu.ubp.das.indecback.components.SimpleJdbcCallFactory;

@Repository
public class RegionalizacionRepository {
    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    // Para regionalizacion
    public List<IdiomaBean> obtenerIdiomas() {
        return jdbcCallFactory.executeQuery("obtener_idiomas", "dbo", "idiomas", IdiomaBean.class);
    }

    // obtener traduccion
    public Map<String, List<?>> obtenerTraduccion(String codIdioma) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cod_idioma", codIdioma);

        Map<String, Object> resultados = jdbcCallFactory.executeQueryWithMultipleResults(
                "obtener_traduccion", "dbo", params);

        Map<String, List<?>> resultado = new HashMap<>();
        resultado.put("rubros", (List<RubroBean>) resultados.get("rubros"));
        resultado.put("categorias", (List<CategoriaBean>) resultados.get("categorias"));
        resultado.put("tipos_productos", (List<TipoProductoBean>) resultados.get("tipos_productos"));

        return resultado;
    }

}
