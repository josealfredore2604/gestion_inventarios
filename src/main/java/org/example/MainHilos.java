package org.example;

import cache.CacheBranch;
import cache.CacheProduct;
import database.DatabaseConnection;

import java.util.Scanner;

public class MainHilos {
    public static void main(String[] args) {
        // Crear instancias de caché
        CacheProduct cacheProduct = new CacheProduct();
        CacheBranch cacheBranch = new CacheBranch();

        // Crear un scanner para la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Insertar una sucursal");
            System.out.println("2. Insertar un producto");
            System.out.println("3. Ejecutar productor y consumidor");
            System.out.println("4. Salir");

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
                    cacheBranch.addBranch(1, branchName); // Agregar al caché (puedes usar lógica para IDs automáticos)
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
                    cacheProduct.addProduct(1001, productName); // Agregar al caché (puedes usar lógica para IDs automáticos)
                    break;

                case 3:
                    // Ejecutar productor y consumidor en hilos separados
                    Thread productorThread = new Thread(new ProductorMain());
                    Thread consumidorThread = new Thread(new ConsumidorMain(cacheBranch, cacheProduct));

                    // Iniciar los hilos
                    productorThread.start();
                    consumidorThread.start();

                    // Esperar a que los hilos terminen (opcional)
                    try {
                        productorThread.join();
                        consumidorThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Hilo interrumpido: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Salir
                    exit = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        // Cerrar el escáner
        scanner.close();
        System.out.println("Fin del programa.");
    }
}
