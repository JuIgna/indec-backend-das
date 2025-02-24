package ar.edu.ubp.das.indecback.beans;

public class TipoProductoBean {
    private int nro_tipo_producto;
    private String nom_tipo_producto;

    public TipoProductoBean() {}

    public int getNro_tipo_producto() {
        return nro_tipo_producto;
    }

    public void setNro_tipo_producto(int nro_tipo_producto) {
        this.nro_tipo_producto = nro_tipo_producto;
    }

    public String getNom_tipo_producto() {
        return nom_tipo_producto;
    }

    public void setNom_tipo_producto(String nom_tipo_producto) {
        this.nom_tipo_producto = nom_tipo_producto;
    }

}
