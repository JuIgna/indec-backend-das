package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.repositories.IndecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComparadorFacade {
    @Autowired
    private SupermercadoServiceFactory supermercadoServiceFactory;

    @Autowired
    private IndecRepository indecRepository;

    public void actualizarSucursales() {
        // Obtener la lista de supermercados desde la base de datos de INDEC

        List<SupermercadoIndecBean> supermercados = indecRepository.obtenerSupermercados();

        for (SupermercadoIndecBean supermercado : supermercados) {
            SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado);

                List<SucursalBean> sucursales = supermercadoService.obtenerInformacionCompletaSucursales();

                for (SucursalBean sucursal : sucursales) {

                    System.out.printf(sucursal.getNom_sucursal());

                    // Buscar el nro_localidad usando nom_localidad, cod_provincia y cod_pais
                    Integer nroLocalidadIndec = indecRepository.obtenerNroLocalidad(
                            sucursal.getNom_localidad(),
                            sucursal.getCod_provincia(),
                            sucursal.getCod_pais()
                    );

                    System.out.println("NRO LOCALIDAD: " + nroLocalidadIndec);

                    if (nroLocalidadIndec != null) {
                        // Actualizar la sucursal en la base de datos de INDEC
                        indecRepository.actualizarSucursal(sucursal, supermercado.getNro_supermercado(), nroLocalidadIndec);
                    } else {
                        System.err.println("No se encontró la localidad en INDEC para la sucursal: " + sucursal.getNom_localidad());
                    }
                }


        }
    }

    public void actualizarInformacionProductos (){
        // Obtener la lista de supermercados desde la base de datos de INDEC
        List<SupermercadoIndecBean> supermercados = indecRepository.obtenerSupermercados();

        for (SupermercadoIndecBean supermercado : supermercados) {
            SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado);

                List<ProductoCompletoBean> productos = supermercadoService.obtenerInformacionCompletaProductos();

                for (ProductoCompletoBean producto : productos) {
                    System.out.println("NOM PRODUCTO : " + producto.getNom_producto());

                    indecRepository.actualizarProductos(producto);
                }


        }

    }

    public void actualizarPreciosProductos() {
        // Obtener la lista de supermercados desde la base de datos de INDEC
        List<SupermercadoIndecBean> supermercados = indecRepository.obtenerSupermercados();

        for (SupermercadoIndecBean supermercado : supermercados) {
            SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado);

                List<PrecioProductoBean> precioProductos = supermercadoService.obtenerInformacionPrecioProductos();

                for (PrecioProductoBean precioProducto : precioProductos) {
                    System.out.println("PRECIO  : " + precioProducto.getPrecio());

                    indecRepository.actualizarPreciosProductos(precioProducto, supermercado.getNro_supermercado());
                }


        }

    }

    public void actualizarUbicaciones() {
        List<SupermercadoIndecBean> supermercados = indecRepository.obtenerSupermercados();

        for (SupermercadoIndecBean supermercado : supermercados) {
            SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado);

                // Obtener información separada de países, provincias y localidades
                List<PaisBean> paises = supermercadoService.obtenerPaises();
                List<ProvinciaIndecBean> provincias = supermercadoService.obtenerProvincias();
                List<LocalidadIndecBean> localidades = supermercadoService.obtenerLocalidades();

                // Actualizar cada entidad en el orden correcto
                for (PaisBean pais : paises) {
                    indecRepository.actualizarPais(pais);
                }
                for (ProvinciaIndecBean provincia : provincias) {
                    indecRepository.actualizarProvincia(provincia);
                }
                for (LocalidadIndecBean localidad : localidades) {
                    indecRepository.actualizarLocalidad(localidad);
                }

        }
    }




}
