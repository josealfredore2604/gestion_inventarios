package org.example;

import service.Productor;
import java.util.Scanner;

/**
 * Clase ProductorMain que implementa Runnable para ejecutar el productor de mensajes en un hilo separado.
 * Esta clase permite al usuario interactuar con el productor para enviar mensajes de compra a la cola de ActiveMQ.
 */
public class ProductorMain implements Runnable {

    /**
     * Constructor de la clase ProductorMain.
     * Se puede extender en el futuro para inicializar otros componentes si es necesario.
     */
    public ProductorMain() {
    }

    @Override
    public void run() {
        // Crear una instancia del productor para enviar mensajes
        Productor productor = new Productor();
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar opciones al usuario
            System.out.println("Opciones del productor:");
            System.out.println("1. Ingresar compra");
            System.out.println("2. Salir");

            // Leer la opción seleccionada por el usuario
            int option = scanner.nextInt();

            if (option == 1) {
                // Opción para ingresar una compra
                // Pedir datos al usuario
                System.out.print("Ingrese la cantidad de unidades a comprar: ");
                int cantidad = scanner.nextInt();

                System.out.print("Ingrese el ID del producto: ");
                int productoId = scanner.nextInt();

                // Crear mensaje de compra para enviar
                String mensajeCompra = "Compra realizada: " + cantidad + " unidades del producto ID: " + productoId;

                // Enviar la transacción a través de ActiveMQ
                productor.enviarMensaje(mensajeCompra);
                System.out.println("Mensaje enviado: " + mensajeCompra);
            } else if (option == 2) {
                // Opción para salir del bucle y detener el productor
                System.out.println("Productor detenido.");
                break; // Salir del bucle y detener el hilo
            } else {
                // Opción no válida
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        // Cerrar el objeto Scanner al finalizar
        scanner.close();
    }
}
