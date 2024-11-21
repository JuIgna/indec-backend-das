package ar.edu.ubp.das.indecback.beans;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "PaisesResponse")
public class PaisBean {
    private String cod_pais;
    private String nom_pais;

    public PaisBean () {}

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getNom_pais() {
        return nom_pais;
    }

    public void setNom_pais(String nom_pais) {
        this.nom_pais = nom_pais;
    }
}
