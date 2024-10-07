package org.example;

import cache.CacheBranch;
import cache.CacheProduct;
import service.Consumidor;
import service.Productor;

public class MainPrueba {
    public static void main(String[] args) {
        // Crear instancias de caché
        CacheProduct cacheProduct = new CacheProduct();
        CacheBranch cacheBranch = new CacheBranch();

        // Añadir algunos datos al caché
        cacheBranch.addBranch(1, "Sucursal Central, Dirección: Calle 123, Teléfono: 555-1234");
        cacheProduct.addProduct(1001, "Producto A, Descripción: Producto de prueba, Precio: 10.50");

        // Crear una instancia de productor
        Productor productor = new Productor();

        // Enviar un mensaje de compra con valores predeterminados
        int cantidad = 5; // Cantidad de unidades a comprar
        int productoId = 1001; // ID del producto

        // Crear mensaje de compra para enviar
        String mensajeCompra = "Compra realizada: " + cantidad + " unidades del producto ID: " + productoId;
        productor.enviarMensaje(mensajeCompra);
        System.out.println("Mensaje de compra enviado: " + mensajeCompra);

        // Crear una instancia de consumidor
        Consumidor consumidor = new Consumidor(cacheBranch, cacheProduct);

        // Iniciar el consumidor en un hilo separado
        Thread consumidorThread = new Thread(consumidor::recibirMensajes);
        consumidorThread.start();

        // Para pruebas, permitimos que el consumidor tenga tiempo para recibir el mensaje
        try {
            Thread.sleep(10000); // Espera 10 segundos antes de finalizar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Terminar el consumidor después de la prueba (si fuera necesario)
        System.out.println("Fin de la prueba.");
        System.exit(0);
    }
}
