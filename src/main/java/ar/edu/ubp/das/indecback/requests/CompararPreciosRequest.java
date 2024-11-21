package ar.edu.ubp.das.indecback.requests;

import java.util.List;

public class CompararPreciosRequest {
    private List<String> codigos_barras;

    public CompararPreciosRequest() {}

    public List<String> getCodigos_barras() {
        return codigos_barras;
    }

    public void setCodigos_barras(List<String> codigos_barras) {
        this.codigos_barras = codigos_barras;
    }

}
