package ar.edu.ubp.das.indecback.services;

import ar.edu.ubp.das.indecback.beans.*;
import ar.edu.ubp.das.indecback.repositories.PreciosRepository;
import ar.edu.ubp.das.indecback.repositories.ProductosRepository;
import ar.edu.ubp.das.indecback.repositories.SucursalesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class ComparadorFacade {

    @Autowired
    private SupermercadoServiceFactory supermercadoServiceFactory;
    @Autowired
    private SucursalesRepository sucursalesRepository;
    @Autowired
    private PreciosRepository preciosRepository;
    @Autowired
    private ProductosRepository productosRepository;

    private Map<String, Object> config; // Configuracion cargada

    public ComparadorFacade() {
        try {
            this.config = cargarConfiguracionDesdeRecursos("config.json");
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuración JSON", e);
        }
    }

    private Map<String, Object> cargarConfiguracionDesdeRecursos(String rutaArchivo) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new ClassPathResource(rutaArchivo).getInputStream();
        return objectMapper.readValue(inputStream, Map.class);
    }

    public List<SupermercadoBean> obtenerSupermercados() {
        return productosRepository.obtenerSupermercados();
    }

    public void actualizarInformacionOperacion(String tipoOperacion) throws Exception {
        // Obtener la lista de supermercados desde la base de datos de INDEC
        List<SupermercadoBean> supermercados = obtenerSupermercados();

        for (SupermercadoBean supermercado : supermercados) {
            SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado, config);

            System.out.println("Actualizando informacion de supermercado: " + supermercado.getRazon_social());
            System.out.println("URL de Servicio: " + supermercado.getUrl_servicio());
            System.out.println("Realizando opreacion con clave: " + tipoOperacion);


            // Invocar servicio con la operación obtenida del JSON
            String jsonRespuesta = supermercadoService.invocarServicio(tipoOperacion);

            switch (tipoOperacion) {
                case "sucursales":
                        System.out.println("JSON de SUCURSALES " + supermercado.getRazon_social() + " De Servicio " + supermercado.getTipo_servicio());
                        System.out.println(jsonRespuesta);
                        sucursalesRepository.actualizarSucursal(jsonRespuesta, supermercado.getNro_supermercado());
                        break;

                case "productos":
                        System.out.println("JSON de PRODUCTOS " + supermercado.getRazon_social() + " De Servicio " + supermercado.getTipo_servicio());
                        System.out.println(jsonRespuesta);
                        productosRepository.actualizarProductos(jsonRespuesta, supermercado.getNro_supermercado());
                        break;

                case "precios":
                        System.out.println("JSON de PRECIOS " + supermercado.getRazon_social() + " De Servicio " + supermercado.getTipo_servicio());
                        System.out.println(jsonRespuesta);
                    System.out.println("NRO_ " + supermercado.getNro_supermercado());
                        preciosRepository.actualizarPreciosProductos(jsonRespuesta, supermercado.getNro_supermercado());
                        break;
            }
        }
    }

    public void actualizarInformacionSupermerccado (SupermercadoBean supermercado, String tipoOperacion) throws Exception {
        String[] operaciones;
        String jsonRespuesta = "";

        System.out.println("Actualizando informacion de supermercado: " + supermercado.getRazon_social());
        System.out.println("URL de Servicio: " + supermercado.getUrl_servicio());

        SupermercadoService supermercadoService = supermercadoServiceFactory.getService(supermercado, config);

        if (tipoOperacion.isEmpty()) {
            operaciones = new String []{"sucursales", "productos", "precios"};
        }else {
            operaciones = new String [] {tipoOperacion};
        }

        for (String operacion : operaciones) {
            System.out.println("Ejecutando operacion con clave: " + operacion);

            jsonRespuesta = supermercadoService.invocarServicio(operacion);

            switch (operacion) {
                case "sucursales":
                    sucursalesRepository.actualizarSucursal(jsonRespuesta, supermercado.getNro_supermercado());
                    break;
                case "productos":
                    productosRepository.actualizarProductos(jsonRespuesta, supermercado.getNro_supermercado());
                    break;
                case "precios":
                    preciosRepository.actualizarPreciosProductos(jsonRespuesta, supermercado.getNro_supermercado());
                    break;
            }
        }

    }


}