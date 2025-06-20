package ar.edu.ubp.das.indecback.batch;

import ar.edu.ubp.das.indecback.IndecBackApplication;
import ar.edu.ubp.das.indecback.beans.SupermercadoBean;
import ar.edu.ubp.das.indecback.services.ComparadorFacade;
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

    public void ejecutarActualizacionInformacionPorOperaciones(String tipoOperacion) {
        try {
            comparadorFacade.actualizarInformacionOperacion(tipoOperacion);
            System.out.println("Actualización de " + tipoOperacion + "completada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error durante la actualización de: " + tipoOperacion + e.getMessage());
        }
    }

    public void ejecutarActualizacionInformacionPorSupermercados (SupermercadoBean supermercado, String tipoOperacion) {
        try {
            comparadorFacade.actualizarInformacionSupermerccado(supermercado, tipoOperacion);
            System.out.println("Actualización de " + supermercado.getRazon_social() + " completada exitosamente.");
            if (tipoOperacion.isEmpty()){
                System.out.println("Operacion Actualizada: Todo el Supermercado");
            }else
                System.out.println("Operacion Actualizada: " + tipoOperacion);
        } catch (Exception e) {
            System.err.println("Error durante la actualización de: " + supermercado.getRazon_social() + e.getMessage());
        }
    }



    public void menuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do{
            System.out.println("-------------------------- MENU DE ACTUALIZACION DE DATOS -----------------------------------------------");
            System.out.println("1. Actualizar Datos Por Supermercados ");
            System.out.println("2. Actualizar Datos Por Operaciones");
            System.out.println("3. Finalizar Actualizacion de Datos");
            System.out.println("Ingrese su Opcion --> ");
            System.out.println("------------------------------------------------------------------------------------------------");

            opcion = scanner.nextInt();

            switch (opcion){
                case 1: menuSupermercados();
                    break;
                case 2: menuOperaciones();
                    break;
                default: break;
            }
        }while (opcion != 3);
    }

    public void menuSupermercados() {
        Scanner scanner = new Scanner(System.in);
        List<SupermercadoBean> supermercados = comparadorFacade.obtenerSupermercados();

        int opcion;

        do {
            System.out.println("---------------------------- Actualización Por Supermercados ---------------------------------------------");

            for (int i = 0; i < supermercados.size(); i++) {
                SupermercadoBean supermercado = supermercados.get(i);
                System.out.println(supermercado.getNro_supermercado() + ". " +
                        "Actualizar Datos de Supermercado " + supermercado.getRazon_social() +
                        " De Tipo: " + supermercado.getTipo_servicio());
            }

            System.out.println(supermercados.size() + 1 + ". Salir");
            System.out.println("------------------------------------------------------------------------------------------");

            opcion = scanner.nextInt();

            if (opcion > 0 && opcion <= supermercados.size()) {
                SupermercadoBean supermercadoSeleccionado = supermercados.get(opcion - 1);
                menuOperacionesPorSupermercado(supermercadoSeleccionado);

            } else if (opcion != supermercados.size() + 1) {
                System.out.println("Opción inválida, intente nuevamente.");
            }

        } while (opcion != supermercados.size() + 1);
    }

    public void menuOperacionesPorSupermercado(SupermercadoBean supermercado) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione la operación a actualizar en " + supermercado.getRazon_social() + ":");
            System.out.println("1. Actualizar Sucursales");
            System.out.println("2. Actualizar Productos");
            System.out.println("3. Actualizar Precios de Productos");
            System.out.println("4. Actualizar Todo el Supermercado");
            System.out.println("5. Volver al menú anterior");

            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ejecutarActualizacionInformacionPorSupermercados(supermercado, "sucursales");
                    break;
                case 2:
                    ejecutarActualizacionInformacionPorSupermercados(supermercado, "productos");
                    break;
                case 3:
                    ejecutarActualizacionInformacionPorSupermercados(supermercado, "precios");
                    break;
                case 4:
                    ejecutarActualizacionInformacionPorSupermercados(supermercado,"");
                case 5:
                    System.out.println("Regresando al menú de supermercados...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 5);
    }


    // Método para mostrar el menú y ejecutar la opción seleccionada
    public void menuOperaciones() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

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
                    ejecutarActualizacionInformacionPorOperaciones("sucursales");
                    break;
                case 2:
                    ejecutarActualizacionInformacionPorOperaciones("productos");
                    break;
                case 3:
                    ejecutarActualizacionInformacionPorOperaciones("precios");
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
        ejecutor.menuPrincipal();
    }
}

