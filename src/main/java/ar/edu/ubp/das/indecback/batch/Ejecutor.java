package ar.edu.ubp.das.indecback.batch;

import ar.edu.ubp.das.indecback.IndecBackApplication;
import ar.edu.ubp.das.indecback.beans.SucursalBean;
import ar.edu.ubp.das.indecback.services.ComparadorFacade;
import ar.edu.ubp.das.indecback.services.SupermercadoRestService;
import ar.edu.ubp.das.indecback.services.SupermercadoSoapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Ejecutor {

    @Autowired
    private ComparadorFacade comparadorFacade;

    public void ejecutarActualizacionSucursales() {
        try {
            comparadorFacade.actualizarSucursales();
            System.out.println("Actualización de sucursales completada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error durante la actualización de sucursales: " + e.getMessage());
        }
    }

    public void ejecutarActualizacionInformacionProductos () {
        try {
            comparadorFacade.actualizarInformacionProductos();
            System.out.println("Actualización de informacion de productos exitoso.");
        } catch (Exception e) {
            System.err.println("Error en la actualizacion de los productos: " + e.getMessage());
        }
    }

    public void ejecutarActualizacionPreciosProductos (){
        try {
            comparadorFacade.actualizarPreciosProductos();
            System.out.println("/n Actualización de informacion de precios de productos exitoso.");
        } catch (Exception e) {
            System.err.println("Error en la actualizacion de los precios de productos: " + e.getMessage());
        }
    }
/*
    public void ejecutarActualizacionUbicaciones (){
        try {
            comparadorFacade.actualizarUbicaciones();
            System.out.println("/nActualización de ubicaciones exitoso.");
        } catch (Exception e) {
            System.err.println("Error en la actualizacion de las ubicaciones: " + e.getMessage());
        }
    }

 */

    // Método para mostrar el menú y ejecutar la opción seleccionada
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("/nSeleccione la acción que desea ejecutar:");
            System.out.println("1. Actualizar Sucursales");
            System.out.println("2. Actualizar Productos");
            System.out.println("3. Actualizar Precios de Productos");
            System.out.println("4. Salir");

            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ejecutarActualizacionSucursales();
                    break;
                case 2:
                    ejecutarActualizacionInformacionProductos();
                    break;
                case 3:
                    ejecutarActualizacionPreciosProductos();
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();  // Línea en blanco para mejor legibilidad

        } while (opcion != 4);

        scanner.close();
    }


    public static void main(String[] args) {
        // Inicializa el contexto de Spring
        ApplicationContext context = new SpringApplicationBuilder(IndecBackApplication.class)
                .web(WebApplicationType.NONE)  // Esto asegura que no se inicie un servidor web
                .run(args);

        // Obtén el bean Ejecutor del contexto de Spring
        Ejecutor ejecutor = context.getBean(Ejecutor.class);


        // Llama al menú
        ejecutor.mostrarMenu();

    }

/*
    public void ejecutarPruebaSucursalesSoap() {
        SupermercadoSoapService soapService = new SupermercadoSoapService();
        try {
            List<SucursalBean> sucursales = soapService.obtenerInformacionCompletaSucursales();

            sucursales.forEach(sucursal -> {
                System.out.println("Sucursal: " + sucursal.getNom_sucursal());
                System.out.println("Calle: " + sucursal.getCalle());
                System.out.println("Teléfonos: " + sucursal.getTelefonos());
                // Agrega más campos si es necesario.
                System.out.println("---------------------------------");
            });
        } catch (Exception e) {
            System.err.println("Error al obtener las sucursales SOAP: " + e.getMessage());
        }
    }

    public void ejecutarPruebaSucursalesRest() {
        SupermercadoRestService restService = new SupermercadoRestService();
        try {
            List<SucursalBean> sucursales = restService.obtenerInformacionCompletaSucursales();
            sucursales.forEach(sucursal -> {
                System.out.println("Sucursal: " + sucursal.getNom_sucursal());
                System.out.println("Calle: " + sucursal.getCalle());
                System.out.println("Teléfonos: " + sucursal.getTelefonos());
                // Agrega más campos si es necesario.
                System.out.println("---------------------------------");
            });
        } catch (Exception e) {
            System.err.println("Error al obtener las sucursales REST: " + e.getMessage());
        }
    }

*/
}

