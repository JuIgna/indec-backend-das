package ar.edu.ubp.das.indecback.beans;

public class SupermercadoIndecBean extends SupermercadoBean {

    private int nro_supermercado;
    private String tipo_servicio;
    private String url_servicio;
    private String token_servicio;

    // Constructor
    public SupermercadoIndecBean() {}

    // Getters and Setters
    public int getNro_supermercado() {
        return nro_supermercado;
    }

    public void setNro_supermercado(int nro_supermercado) {
        this.nro_supermercado = nro_supermercado;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public String getUrl_servicio() {
        return url_servicio;
    }

    public void setUrl_servicio(String url_servicio) {
        this.url_servicio = url_servicio;
    }

    public String getToken_servicio() {
        return token_servicio;
    }

    public void setToken_servicio(String token_servicio) {
        this.token_servicio = token_servicio;
    }
}
