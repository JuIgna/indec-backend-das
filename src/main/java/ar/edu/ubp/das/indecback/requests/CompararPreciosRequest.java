package ar.edu.ubp.das.indecback.requests;

import java.util.List;

public class CompararPreciosRequest {
    private List<String> codigos_barras;
    private int nro_localidad;

    public CompararPreciosRequest() {}

    public List<String> getCodigos_barras() {
        return codigos_barras;
    }

    public void setCodigos_barras(List<String> codigos_barras) {
        this.codigos_barras = codigos_barras;
    }

    public int getNro_localidad() {
        return nro_localidad;
    }

    public void setNro_localidad(int nro_localidad) {
        this.nro_localidad = nro_localidad;
    }
}
