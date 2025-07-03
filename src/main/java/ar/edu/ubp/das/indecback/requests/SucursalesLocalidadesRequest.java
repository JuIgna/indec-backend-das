package ar.edu.ubp.das.indecback.requests;

import java.util.List;

public class SucursalesLocalidadesRequest {
    private Integer nro_localidad;
    private List<String> lista_supermercados;

    public SucursalesLocalidadesRequest () {}

    public Integer getNro_localidad() {
        return nro_localidad;
    }

    public void setNro_localidad(int nro_localidad) {
        this.nro_localidad = nro_localidad;
    }

    public List<String> getLista_supermercados() {
        return lista_supermercados;
    }

    public void setLista_supermercados(List<String> lista_supermercados) {
        this.lista_supermercados = lista_supermercados;
    }
}
