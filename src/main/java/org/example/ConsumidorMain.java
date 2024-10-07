package org.example;

import cache.CacheBranch;
import cache.CacheProduct;
import service.Consumidor;

/**
 * Clase ConsumidorMain que implementa Runnable para ejecutar el consumidor de mensajes en un hilo separado.
 * Esta clase se encarga de crear una instancia del consumidor,
 * que se utilizará para recibir mensajes de la cola de ActiveMQ.
 */
public class ConsumidorMain implements Runnable {
    // Caché para almacenar la información de sucursales
    private final CacheBranch cacheBranch;
    // Caché para almacenar la información de productos
    private final CacheProduct cacheProduct;

    /**
     * Constructor que acepta el caché de sucursales y productos como parámetros.
     *
     * @param cacheBranch El caché que almacena la información de las sucursales.
     * @param cacheProduct El caché que almacena la información de los productos.
     */
    public ConsumidorMain(CacheBranch cacheBranch, CacheProduct cacheProduct) {
        this.cacheBranch = cacheBranch; // Inicializa el caché de sucursales
        this.cacheProduct = cacheProduct; // Inicializa el caché de productos
    }

    @Override
    public void run() {
        // Crear una instancia del consumidor usando los objetos del caché
        Consumidor consumidor = new Consumidor(cacheBranch, cacheProduct);
        // Inicia el proceso de recibir mensajes desde la cola de ActiveMQ
        consumidor.recibirMensajes();
    }
}
