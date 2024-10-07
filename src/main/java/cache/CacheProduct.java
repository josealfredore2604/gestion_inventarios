package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase CacheProduct que implementa un sistema de almacenamiento en caché para productos.
 * Esta clase permite agregar, obtener y verificar la existencia de productos en el caché,
 * utilizando un mapa de datos en memoria para mejorar el rendimiento del sistema.
 */
public class CacheProduct {
    // Mapa para almacenar la información de los productos en caché.
    // La clave es el ID del producto y el valor es la información del producto.
    private Map<Integer, String> cache = new HashMap<>();

    /**
     * Añade un producto al caché.
     *
     * @param id          El identificador único del producto.
     * @param productInfo La información del producto (nombre, descripción, precio, etc.).
     */
    public void addProduct(int id, String productInfo) {
        cache.put(id, productInfo); // Agrega el producto al mapa utilizando el ID como clave.
    }

    /**
     * Obtiene la información de un producto del caché utilizando su ID.
     *
     * @param id El identificador único del producto.
     * @return La información del producto si está presente en el caché, de lo contrario, devuelve null.
     */
    public String getProduct(int id) {
        return cache.get(id); // Recupera la información del producto desde el mapa.
    }

    /**
     * Verifica si un producto está presente en el caché.
     *
     * @param id El identificador único del producto.
     * @return true si el producto está en el caché, false de lo contrario.
     */
    public boolean containsProduct(int id) {
        return cache.containsKey(id); // Verifica si el ID del producto existe en el mapa.
    }
}
