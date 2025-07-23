package ar.edu.ubp.das.indecback.beans;

public class PrecioMinProductoBean {
    private String cod_barra;
    private String nom_producto;
    private String imagen;
    private String razon_social;
    private int nro_supermercado;
    private float mejor_precio;
    private int sin_precio;
    private int sin_stock;
    private int sin_precio_actual;

    // private String vigente;

    public PrecioMinProductoBean() {}

    public void setSin_precio(int sin_precio){
        this.sin_precio = sin_precio;
    }

    public int getSin_precio (){
        return sin_precio;
    }

    public void setSin_stock (int sin_stock){
        this.sin_stock = sin_stock;
    }

    public int getSin_stock (){
        return sin_stock;
    }

    public void setSin_precio_actual (int sin_precio_actual){
        this.sin_precio_actual = sin_precio_actual;
    }

    public int getSin_precio_actual (){
        return sin_precio_actual;
    }

    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public String getNom_producto() {
        return nom_producto;
    }

    public void setNom_producto(String nom_producto) {
        this.nom_producto = nom_producto;
    }

    public int getNro_supermercado() {
        return nro_supermercado;
    }

    public void setNro_supermercado(int nro_supermercado) {
        this.nro_supermercado = nro_supermercado;
    }

    public float getMejor_precio() {
        return mejor_precio;
    }

    public void setMejor_precio(float mejor_precio) {
        this.mejor_precio = mejor_precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    // public String getVigente() {
    //     return vigente;
    // }

    // public void setVigente(String vigente) {
    //     this.vigente = vigente;
    // }
}