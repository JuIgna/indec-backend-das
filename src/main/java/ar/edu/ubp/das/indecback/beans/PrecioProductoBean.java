package ar.edu.ubp.das.indecback.beans;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "InformacionPreciosProductosResponse")
public class PrecioProductoBean {
    private int nro_sucursal;
    private String cod_barra;
    private float precio;

    public PrecioProductoBean() {}

    public int getNro_sucursal() {
        return nro_sucursal;
    }

    public void setNro_sucursal(int nro_sucursal) {
        this.nro_sucursal = nro_sucursal;
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
}
