package ar.edu.ubp.das.indecback.beans;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "LocalidadesResponse")
public class LocalidadIndecBean extends LocalidadBean{
    private String cod_pais;
    private String cod_provincia;

    public LocalidadIndecBean() {}

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getCod_provincia() {
        return cod_provincia;
    }

    public void setCod_provincia(String cod_provincia) {
        this.cod_provincia = cod_provincia;
    }
}