package ar.edu.ubp.das.indecback.beans;

public class CategoriaBean {
    private int nro_categoria;
    private int nro_rubro;
    private String nom_categoria;

    public CategoriaBean (){}

    public int getNro_categoria() {
        return nro_categoria;
    }

    public void setNro_categoria(int nro_categoria) {
        this.nro_categoria = nro_categoria;
    }

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public int getNro_rubro() {
        return nro_rubro;
    }

    public void setNro_rubro(int nro_rubro) {
        this.nro_rubro = nro_rubro;
    }
}
