package ar.edu.ubp.das.supermercadorest.beans;

public class PrecioProductoBean {
   // private int nro_sucursal;
    private String nom_sucursal;
    private String cod_barra;
    private String vigente;
    private float precio;
    private float precio_promocion;
    private String fecha_fin_promocion;

    public PrecioProductoBean () {}

    // public int getNro_sucursal() {
    //     return nro_sucursal;
    // }

    // nuevos campos
    public void setPrecioPromocion (float precio_promocion){
       this.precio_promocion = precio_promocion;
    }

    public float getPrecio_promocion (){
        return precio_promocion;
    }

    public void setFecha_fin_promocion (String fecha_fin_promocion){
        this.fecha_fin_promocion = fecha_fin_promocion;
    }

    public String getFecha_fin_promocion (){
        return fecha_fin_promocion;
    }

    //

    public void setNom_sucursal (String nom_sucursal){
        this.nom_sucursal = nom_sucursal;
    }

    public String getNom_sucursal () {
        return nom_sucursal;
    }

    // public void setNro_sucursal(int nro_sucursal) {
    //     this.nro_sucursal = nro_sucursal;
    // }

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
