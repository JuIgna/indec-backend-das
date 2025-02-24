package ar.edu.ubp.das.indecback.batch;

import ar.edu.ubp.das.indecback.IndecBackApplication;
import ar.edu.ubp.das.indecback.services.ComparadorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class Ejecutor {

    @Autowired
    private ComparadorFacade comparadorFacade;

    public void ejecutarActualizacionInformacionSupermercados(String tipoOperacion) {
        try {
            comparadorFacade.actualizarInformacionSupermercados(tipoOperacion);
            System.out.println("Actualización de " + tipoOperacion + "completada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error durante la actualización de: " + tipoOperacion + e.getMessage());
        }
    }

    // Método para mostrar el menú y ejecutar la opción seleccionada
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione la acción que desea ejecutar:");
            System.out.println("1. Actualizar Sucursales");
            System.out.println("2. Actualizar Productos");
            System.out.println("3. Actualizar Precios de Productos");
            System.out.println("4. Salir");

            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ejecutarActualizacionInformacionSupermercados("sucursales");
                    break;
                case 2:
                    ejecutarActualizacionInformacionSupermercados("productos");
                    break;
                case 3:
                    ejecutarActualizacionInformacionSupermercados("precios");
                    break;
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

        // Llama el menú
        ejecutor.mostrarMenu();
    }
}

