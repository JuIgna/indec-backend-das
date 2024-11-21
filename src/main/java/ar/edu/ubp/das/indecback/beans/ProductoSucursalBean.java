package ar.edu.ubp.das.indecback.beans;

public class ProductoSucursalBean {
    private String cod_barra;
    private int nro_sucursal;
    private float precio;
    private char vigente_en_sucursal;

    public ProductoSucursalBean() {}

    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public int getNro_sucursal() {
        return nro_sucursal;
    }

    public void setNro_sucursal(int nro_sucursal) {
        this.nro_sucursal = nro_sucursal;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public char getVigente_en_sucursal() {
        return vigente_en_sucursal;
    }

    public void setVigente_en_sucursal(char vigente) {
        this.vigente_en_sucursal = vigente;
    }
}
