package ar.edu.ubp.das.indecback.beans;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ProvinciasResponse")
public class ProvinciaIndecBean extends ProvinciaBean {
    private String cod_pais;

    public ProvinciaIndecBean () {}

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

}
