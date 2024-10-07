package model;

import cache.CacheProduct;
import database.DatabaseConnection;

import java.util.Date;

/**
 * Clase Venta que representa una transacción de venta dentro del sistema de gestión de inventarios.
 * Esta clase extiende la clase base Transaccion e incluye información específica sobre la venta,
 * como el ID del producto, la cantidad vendida y el precio unitario.
 */
public class Venta extends Transaccion {
    // Atributos específicos de la venta
    private int productoId;        // Identificador único del producto vendido
    private int cantidad;          // Cantidad de unidades vendidas
    private double precioUnitario; // Precio por unidad del producto vendido

    /**
     * Constructor de la clase Venta.
     *
     * @param id           El identificador único de la transacción de venta.
     * @param fechaHora    La fecha y hora en que se realizó la venta.
     * @param sucursalId   El ID de la sucursal que realizó la venta.
     * @param productoId   El ID del producto vendido.
     * @param cantidad     La cantidad de unidades vendidas.
     * @param precioUnitario El precio por unidad del producto.
     * @param cacheProduct Una instancia de CacheProduct para verificar si el producto está en el caché.
     */
    public Venta(int id, Date fechaHora, int sucursalId, int productoId, int cantidad, double precioUnitario, CacheProduct cacheProduct) {
        super(id, fechaHora, sucursalId, "VENTA");
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;

        // Verificar si el producto existe en el caché antes de insertar la venta
        if (!cacheProduct.containsProduct(productoId)) {
            System.out.println("El producto no está en el caché. Consultando la base de datos...");
            if (!DatabaseConnection.productoExists(productoId)) {
                System.out.println("El producto con ID " + productoId + " no existe. No se puede completar la venta.");
                return; // Termina si el producto no existe en el caché ni en la base de datos
            }
        } else {
            System.out.println("Información del producto obtenida del caché: " + cacheProduct.getProduct(productoId));
        }
    }

    /**
     * Inserta la venta en la base de datos.
     * Utiliza la conexión de base de datos para insertar la información de la venta.
     */
    public void insertarVenta() {
        DatabaseConnection.insertarTransaccion(getTipo(), getSucursalId(), productoId, cantidad, precioUnitario);
    }

    // Getters y setters para acceder a los atributos específicos de la venta

    /**
     * Obtiene el ID del producto vendido.
     *
     * @return El ID del producto.
     */
    public int getProductoId() {
        return productoId;
    }

    /**
     * Obtiene la cantidad de unidades vendidas.
     *
     * @return La cantidad de unidades vendidas.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene el precio unitario del producto vendido.
     *
     * @return El precio unitario del producto.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
