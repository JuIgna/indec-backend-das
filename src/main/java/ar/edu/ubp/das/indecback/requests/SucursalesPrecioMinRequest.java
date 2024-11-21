package ar.edu.ubp.das.indecback.requests;

public class SucursalesPrecioMinRequest {
    private String cod_barra;
    private int nro_supermercado;
    private float precio;

    public SucursalesPrecioMinRequest() {}


    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public int getNro_supermercado() {
        return nro_supermercado;
    }

    public void setNro_supermercado(int nro_supermercado) {
        this.nro_supermercado = nro_supermercado;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
