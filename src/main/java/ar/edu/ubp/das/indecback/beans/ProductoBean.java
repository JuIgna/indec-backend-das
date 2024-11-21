package ar.edu.ubp.das.indecback.beans;

public class ProductoBean {
    private String cod_barra;
    private String nom_producto;
    private String desc_producto;
    private int nro_categoria;
    private String nom_categoria;
    private int nro_rubro;

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public int getNro_rubro() {
        return nro_rubro;
    }

    public void setNro_rubro(int nro_rubro) {
        this.nro_rubro = nro_rubro;
    }

    public String getNom_rubro() {
        return nom_rubro;
    }

    public void setNom_rubro(String nom_rubro) {
        this.nom_rubro = nom_rubro;
    }

    public String getNom_marca() {
        return nom_marca;
    }

    public void setNom_marca(String nom_marca) {
        this.nom_marca = nom_marca;
    }

    public String getNom_tipo_producto() {
        return nom_tipo_producto;
    }

    public void setNom_tipo_producto(String nom_tipo_producto) {
        this.nom_tipo_producto = nom_tipo_producto;
    }

    private String nom_rubro;
    private int nro_marca;
    private String nom_marca;
    private int nro_tipo_producto;
    private String nom_tipo_producto;
    private String vigente;
    private String imagen;

    public ProductoBean(){}

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

    public String getDesc_producto() {
        return desc_producto;
    }

    public void setDesc_producto(String desc_producto) {
        this.desc_producto = desc_producto;
    }

    public int getNro_categoria() {
        return nro_categoria;
    }

    public void setNro_categoria(int nro_categoria) {
        this.nro_categoria = nro_categoria;
    }

    public int getNro_marca() {
        return nro_marca;
    }

    public void setNro_marca(int nro_marca) {
        this.nro_marca = nro_marca;
    }

    public int getNro_tipo_producto() {
        return nro_tipo_producto;
    }

    public void setNro_tipo_producto(int nro_tipo_producto) {
        this.nro_tipo_producto = nro_tipo_producto;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen; }
}
