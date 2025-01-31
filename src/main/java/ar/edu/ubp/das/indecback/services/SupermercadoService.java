package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;

import java.util.List;


public interface SupermercadoService {
    List<ProductoCompletoBean> obtenerInformacionCompletaProductos();

    List<SucursalBean> obtenerInformacionCompletaSucursales ();

    List<PrecioProductoBean> obtenerInformacionPrecioProductos();

}
    // SERVICIOS NO NECESARIOS, ELIMINAR
    /*
    List<PaisBean> obtenerPaises ();

    List<ProvinciaIndecBean> obtenerProvincias ();

    List<LocalidadIndecBean> obtenerLocalidades ();

    List<SupermercadoBean> obtenerSupermercados ();

     */

