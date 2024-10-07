package model;

import cache.CacheProduct;
import database.DatabaseConnection;

import java.util.Date;

/**
 * Clase Compra que extiende de Transaccion.
 * Representa una transacción de compra realizada en una sucursal.
 * Esta clase contiene detalles específicos de la compra, como el producto,
 * la cantidad, el precio unitario, y verifica si el producto está en caché antes de proceder.
 */
public class Compra extends Transaccion {
    // Atributos específicos de la compra
    private int productoId;          // ID del producto comprado
    private int cantidad;            // Cantidad de unidades compradas
    private double precioUnitario;   // Precio unitario del producto

    /**
     * Constructor de la clase Compra.
     *
     * @param id           El identificador único de la transacción.
     * @param fechaHora    La fecha y hora de la transacción.
     * @param sucursalId   El ID de la sucursal donde se realiza la compra.
     * @param productoId   El ID del producto comprado.
     * @param cantidad     La cantidad de producto comprada.
     * @param precioUnitario El precio unitario del producto.
     * @param cacheProduct Objeto de tipo CacheProduct utilizado para verificar si el producto está en caché.
     */
    public Compra(int id, Date fechaHora, int sucursalId, int productoId, int cantidad, double precioUnitario, CacheProduct cacheProduct) {
        super(id, fechaHora, sucursalId, "COMPRA");
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;

        // Verificar si el producto existe en el caché antes de insertar la compra
        if (!cacheProduct.containsProduct(productoId)) {
            System.out.println("El producto no está en el caché. Consultando la base de datos...");
            if (!DatabaseConnection.productoExists(productoId)) {
                System.out.println("El producto con ID " + productoId + " no existe. No se puede completar la compra.");
                return; // Si el producto no existe en la base de datos, no se puede realizar la compra
            }
        } else {
            System.out.println("Información del producto obtenida del caché: " + cacheProduct.getProduct(productoId));
        }
    }

    /**
     * Método para insertar la compra en la base de datos.
     * Llama al método de DatabaseConnection para registrar la transacción con detalles específicos.
     */
    public void insertarCompra() {
        DatabaseConnection.insertarTransaccion(getTipo(), getSucursalId(), productoId, cantidad, precioUnitario);
    }

    // Getters y setters para acceder a los atributos de la compra

    /**
     * Obtiene el ID del producto comprado.
     *
     * @return El ID del producto.
     */
    public int getProductoId() {
        return productoId;
    }

    /**
     * Obtiene la cantidad de unidades compradas.
     *
     * @return La cantidad de unidades compradas.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return El precio unitario del producto.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }
}

