package model;

import java.util.Date;

/**
 * Clase Transaccion que representa una transacción general dentro del sistema de gestión de inventarios.
 * Puede ser una compra o una venta realizada por una sucursal.
 * Esta clase proporciona información común sobre una transacción, como el ID, la fecha y hora,
 * la sucursal involucrada, y el tipo de transacción.
 */
public class Transaccion {
    // Atributos de la transacción
    private int id;           // Identificador único de la transacción
    private Date fechaHora;   // Fecha y hora en que se realizó la transacción
    private int sucursalId;   // ID de la sucursal que realizó la transacción
    private String tipo;      // Tipo de transacción (COMPRA o VENTA)

    /**
     * Constructor de la clase Transaccion.
     *
     * @param id         El identificador único de la transacción.
     * @param fechaHora  La fecha y hora en que se realizó la transacción.
     * @param sucursalId El ID de la sucursal que realizó la transacción.
     * @param tipo       El tipo de transacción (puede ser "COMPRA" o "VENTA").
     */
    public Transaccion(int id, Date fechaHora, int sucursalId, String tipo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.sucursalId = sucursalId;
        this.tipo = tipo;
    }

    // Getters para acceder a los atributos de la transacción

    /**
     * Obtiene el identificador único de la transacción.
     *
     * @return El identificador único de la transacción.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la fecha y hora de la transacción.
     *
     * @return La fecha y hora en que se realizó la transacción.
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * Obtiene el ID de la sucursal que realizó la transacción.
     *
     * @return El ID de la sucursal.
     */
    public int getSucursalId() {
        return sucursalId;
    }

    /**
     * Obtiene el tipo de transacción.
     *
     * @return El tipo de transacción ("COMPRA" o "VENTA").
     */
    public String getTipo() {
        return tipo;
    }
}
