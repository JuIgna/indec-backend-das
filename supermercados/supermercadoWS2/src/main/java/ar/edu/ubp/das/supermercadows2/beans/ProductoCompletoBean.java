package ar.edu.ubp.das.supermercadows2.beans;

public class ProductoCompletoBean {
    private String cod_barra;
    private String nom_producto;
    private String desc_producto;
    private String nom_categoria;
    private String nom_rubro;
    private String imagen;
    private String nom_marca;
    private String nom_tipo_producto;
    private String tipo_producto_marca_vigente;
    private String producto_vigente;
    // private int nro_sucursal;
    private String nom_sucursal;

    public ProductoCompletoBean() {}

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

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public String getNom_rubro() {
        return nom_rubro;
    }

    public void setNom_rubro(String nom_rubro) {
        this.nom_rubro = nom_rubro;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getTipo_producto_marca_vigente() {
        return tipo_producto_marca_vigente;
    }

    public void setTipo_producto_marca_vigente(String tipo_producto_marca_vigente) {
        this.tipo_producto_marca_vigente = tipo_producto_marca_vigente;
    }

    public String getProducto_vigente() {
        return producto_vigente;
    }

    public void setProducto_vigente(String producto_vigente) {
        this.producto_vigente = producto_vigente;
    }

    public String getNom_sucursal (){
        return nom_sucursal;
    }

    public void setNom_sucursal(String nom_sucursal){
        this.nom_sucursal = nom_sucursal;
    }

    // public int getNro_sucursal() {
    //     return nro_sucursal;
    // }

    // public void setNro_sucursal(int nro_sucursal) {
    //     this.nro_sucursal = nro_sucursal;
    // }
}
