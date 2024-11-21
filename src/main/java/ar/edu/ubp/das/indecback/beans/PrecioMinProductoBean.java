package ar.edu.ubp.das.indecback.beans;

public class PrecioMinProductoBean {
    private String cod_barra;
    private String nom_producto;
    private String imagen;
    private String nom_supermercado;
    private int nro_supermercado;
    private float precio_minimo;

    public PrecioMinProductoBean() {}

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

    public float getPrecio_minimo() {
        return precio_minimo;
    }

    public void setPrecio_minimo(float precio_minimo) {
        this.precio_minimo = precio_minimo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNom_supermercado() {
        return nom_supermercado;
    }

    public void setNom_supermercado(String nom_supermercado) {
        this.nom_supermercado = nom_supermercado;
    }
}
