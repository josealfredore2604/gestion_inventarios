package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase CacheBranch que implementa un sistema de almacenamiento en caché para sucursales.
 * Esta clase permite agregar, obtener y verificar la existencia de sucursales en el caché,
 * utilizando un mapa de datos en memoria.
 */
public class CacheBranch {
    // Mapa para almacenar la información de las sucursales en caché.
    // La clave es el ID de la sucursal y el valor es la información de la sucursal.
    private Map<Integer, String> cache = new HashMap<>();

    /**
     * Añade una sucursal al caché.
     *
     * @param id         El identificador único de la sucursal.
     * @param branchInfo La información de la sucursal (nombre, dirección, teléfono, etc.).
     */
    public void addBranch(int id, String branchInfo) {
        cache.put(id, branchInfo); // Agrega la sucursal al mapa utilizando el ID como clave.
    }

    /**
     * Obtiene la información de una sucursal del caché utilizando su ID.
     *
     * @param id El identificador único de la sucursal.
     * @return La información de la sucursal si está presente en el caché, de lo contrario, devuelve null.
     */
    public String getBranch(int id) {
        return cache.get(id); // Recupera la información de la sucursal desde el mapa.
    }

    /**
     * Verifica si una sucursal está presente en el caché.
     *
     * @param id El identificador único de la sucursal.
     * @return true si la sucursal está en el caché, false de lo contrario.
     */
    public boolean containsBranch(int id) {
        return cache.containsKey(id); // Verifica si el ID de la sucursal existe en el mapa.
    }
}
