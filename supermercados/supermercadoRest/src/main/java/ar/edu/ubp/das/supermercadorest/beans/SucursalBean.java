package ar.edu.ubp.das.supermercadorest.beans;

public class SucursalBean {
    // no hace falta para el nuevo stored private int nro_sucursal;
    private String nom_sucursal;
    private String calle;
    private int nro_calle;
    private String telefonos;
    private String coord_latitud;
    private String coord_longitud;
    private String nom_localidad;
    private String cod_provincia;
    private String cod_pais;
    private String habilitada;
    private String horario_sucursal;
    private String servicios_disponibles;


    public SucursalBean() {}

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

    public String getCoord_latitud() {
        return coord_latitud;
    }

    public void setCoord_latitud(String coord_latitud) {
        this.coord_latitud = coord_latitud;
    }

    public String getCoord_longitud() {
        return coord_longitud;
    }

    public void setCoord_longitud(String coord_longitud) {
        this.coord_longitud = coord_longitud;
    }

    public String getNom_localidad() {
        return nom_localidad;
    }

    public void setNom_localidad(String nom_localidad) {
        this.nom_localidad = nom_localidad;
    }

    public String getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(String habilitada) {
        this.habilitada = habilitada;
    }

    public String getHorario_sucursal() {
        return horario_sucursal;
    }

    public void setHorario_sucursal(String horario_sucursal) {
        this.horario_sucursal = horario_sucursal;
    }

    public String getServicios_disponibles() {
        return servicios_disponibles;
    }

    public void setServicios_disponibles(String servicios_disponibles) {
        this.servicios_disponibles = servicios_disponibles;
    }

    public String getCod_provincia() {
        return cod_provincia;
    }

    public void setCod_provincia(String cod_provincia) {
        this.cod_provincia = cod_provincia;
    }

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }
}
