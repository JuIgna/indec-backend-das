package ar.edu.ubp.das.indecback.beans;

public class SucursalMinBean {
    private int nro_sucursal;
    private String nom_sucursal;
    private String calle;
    private int nro_calle;
    private String telefonos;
    private float coord_latitud;
    private float coord_longitud;

    public SucursalMinBean() {}

    public int getNro_sucursal() {
        return nro_sucursal;
    }

    public void setNro_sucursal(int nro_sucursal) {
        this.nro_sucursal = nro_sucursal;
    }

    public String getNom_sucursal() {
        return nom_sucursal;
    }

    public void setNom_sucursal(String nom_sucursal) {
        this.nom_sucursal = nom_sucursal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNro_calle() {
        return nro_calle;
    }

    public void setNro_calle(int nro_calle) {
        this.nro_calle = nro_calle;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public float getCoord_latitud() {
        return coord_latitud;
    }

    public void setCoord_latitud(float coord_latitud) {
        this.coord_latitud = coord_latitud;
    }

    public float getCoord_longitud() {
        return coord_longitud;
    }

    public void setCoord_longitud(float coord_longitud) {
        this.coord_longitud = coord_longitud;
    }
}
