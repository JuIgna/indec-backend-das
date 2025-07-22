package ar.edu.ubp.das.supermercadows.beans;

public class PrecioProductoBean {
    //private int nro_sucursal;
    private String nom_sucursal;
    private String cod_barra;
    private float precio;
    private String vigente;

    public PrecioProductoBean () {}

    // public int getNro_sucursal() {
    //     return nro_sucursal;
    // }

    // public void setNro_sucursal(int nro_sucursal) {
    //     this.nro_sucursal = nro_sucursal;
    // }

    public void setNom_sucursal (String nom_sucursal){
        this.nom_sucursal = nom_sucursal;
    }

    public String getNom_sucursal (){
        return nom_sucursal;
    }

    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }
}
