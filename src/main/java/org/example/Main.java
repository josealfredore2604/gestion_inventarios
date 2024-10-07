package org.example;

import cache.CacheBranch;
import cache.CacheProduct;
import database.DatabaseConnection;
import service.Consumidor;
import service.Productor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de caché
        CacheProduct cacheProduct = new CacheProduct();
        CacheBranch cacheBranch = new CacheBranch();

        // Crear un scanner para la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione una opción:");
        System.out.println("1. Insertar una sucursal");
        System.out.println("2. Insertar un producto");
        System.out.println("3. Ejecutar productor y consumidor");

        int option = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        switch (option) {
            case 1:
                // Insertar una sucursal
                System.out.print("Ingrese el nombre de la sucursal: ");
                String branchName = scanner.nextLine();
                System.out.print("Ingrese la dirección de la sucursal: ");
                String branchAddress = scanner.nextLine();
                System.out.print("Ingrese el teléfono de la sucursal: ");
                String branchPhone = scanner.nextLine();

                DatabaseConnection.insertarSucursal(branchName, branchAddress, branchPhone);
                cacheBranch.addBranch(1, branchName); // Agregar al caché si es necesario
                System.out.println("Sucursal insertada correctamente.");
                break;

            case 2:
                // Insertar un producto
                System.out.print("Ingrese el nombre del producto: ");
                String productName = scanner.nextLine();
                System.out.print("Ingrese la descripción del producto: ");
                String productDescription = scanner.nextLine();
                System.out.print("Ingrese el precio del producto: ");
                double productPrice = scanner.nextDouble();

                DatabaseConnection.insertarProducto(productName, productDescription, productPrice);
                cacheProduct.addProduct(1001, productName); // Agregar al caché si es necesario
                System.out.println("Producto insertado correctamente.");
                break;

            case 3:
                // Ejecutar productor y consumidor
                // Solicitar datos al usuario para la compra
                System.out.print("Ingrese la cantidad de unidades a comprar: ");
                int cantidad = scanner.nextInt();
                System.out.print("Ingrese el ID del producto: ");
                int productoId = scanner.nextInt();

                // Crear instancia de productor
                Productor productor = new Productor();

                // Crear mensaje de compra para enviar
                String mensajeCompra = "Compra realizada: " + cantidad + " unidades del producto ID: " + productoId;
                productor.enviarMensaje(mensajeCompra);
                System.out.println("Mensaje de compra enviado: " + mensajeCompra);

                // Crear una instancia de consumidor
                Consumidor consumidor = new Consumidor(new CacheBranch(), new CacheProduct());

                // Iniciar el consumidor en un hilo separado
                Thread consumidorThread = new Thread(consumidor::recibirMensajes);
                consumidorThread.start();

                // Para pruebas, permitir que el consumidor tenga tiempo para recibir el mensaje
                try {
                    Thread.sleep(10000); // Espera 10 segundos antes de finalizar
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Finalizar el consumidor
                System.out.println("Productor y consumidor han terminado su ejecución.");
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }

        // Cerrar el escáner
        scanner.close();
        System.out.println("Fin del programa.");
        System.exit(0);
    }
}
