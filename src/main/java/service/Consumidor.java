package service;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import cache.CacheBranch;
import cache.CacheProduct;
import database.DatabaseConnection;
import model.Compra;
import model.Venta;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.*;


/**
 * Clase Consumidor que representa un consumidor de mensajes de la cola ActiveMQ.
 * Esta clase es responsable de recibir mensajes relacionados con las transacciones de compra y venta,
 * procesar esos mensajes y almacenar la información correspondiente en la base de datos.
 */
public class Consumidor {
    private Set<String> processedMessages = new HashSet<>(); // Para rastrear los mensajes que ya han sido procesados

    private CacheBranch cacheBranch; // Caché para almacenar información de sucursales
    private CacheProduct cacheProduct; // Caché para almacenar información de productos

    /**
     * Constructor de la clase Consumidor.
     *
     * @param cacheBranch Instancia de CacheBranch para acceder a información sobre sucursales.
     * @param cacheProduct Instancia de CacheProduct para acceder a información sobre productos.
     */
    public Consumidor(CacheBranch cacheBranch, CacheProduct cacheProduct) {
        this.cacheBranch = cacheBranch;
        this.cacheProduct = cacheProduct;
    }

    /**
     * Método para recibir mensajes de la cola ActiveMQ.
     * Este método se ejecuta en un bucle infinito, esperando y procesando mensajes de forma continua.
     */
    public void recibirMensajes() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            javax.jms.Connection jmsConnection = connectionFactory.createConnection();
            jmsConnection.start();

            Session session = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("TRANSACCIONES_QUEUE");

            MessageConsumer consumer = session.createConsumer(queue);

            // Bucle infinito para seguir recibiendo mensajes
            while (true) {
                TextMessage message = (TextMessage) consumer.receive(); // Espera por un mensaje

                if (message != null) {
                    String mensajeTexto = message.getText(); // Obtiene el texto del mensaje

                    // Verifica si el mensaje ya ha sido procesado
                    if (!processedMessages.contains(mensajeTexto)) {
                        processedMessages.add(mensajeTexto); // Marca el mensaje como procesado
                        System.out.println("Mensaje recibido: " + mensajeTexto);
                        procesarTransaccion(mensajeTexto); // Procesa el mensaje recibido
                    } else {
                        System.out.println("Mensaje duplicado ignorado: " + mensajeTexto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    /**
     * Método para procesar el mensaje recibido.
     * Este método extrae la información de compra o venta del mensaje y realiza las operaciones necesarias.
     *
     * @param mensaje El mensaje de texto recibido que contiene detalles de la transacción.
     */
    private void procesarTransaccion(String mensaje) {
        // Ejemplo: "Compra realizada: 5 unidades del producto ID: 1001"
        String[] parts = mensaje.split(": "); // Dividir en partes usando ": "
        System.out.println("parts = " + Arrays.toString(parts)); // Muestra las partes para depuración
        System.out.println("parts.length = " + parts.length);
        if (parts.length < 2) {
            System.out.println("Formato de mensaje no válido: " + mensaje);
            return; // Termina si el formato no es correcto
        }

        // Trim para eliminar espacios adicionales y separar el detalle
        String detalleParte = parts[1].trim();
        String[] detalle = detalleParte.split(" "); // ["5", "unidades", "del", "producto", "ID:", "1001"]

        ArrayList<String> arrayList = new ArrayList<>(List.of(detalle));
        arrayList.add(parts[2].trim()); // Agregar la parte que contiene el ID del producto

        detalle = arrayList.toArray(new String[arrayList.size()]); // Convertir ArrayList a arreglo

        System.out.println("detalle = " + Arrays.toString(detalle)); // Muestra el detalle para depuración

        // Verifica que el detalle tenga la longitud esperada
        if (detalle.length < 6) {
            System.out.println("Formato de mensaje no válido: " + mensaje);
            return; // Termina si el formato no es correcto
        }

        // Parsear detalles de la transacción
        int cantidad = Integer.parseInt(detalle[0]); // Obtiene la cantidad de unidades

        // Buscar el índice que contiene el ID
        int productoIdIndex = -1;
        for (int i = 0; i < detalle.length; i++) {
            if (detalle[i].equals("ID:") || detalle[i].equals("ID")) {
                productoIdIndex = i + 1; // El siguiente índice debería ser el ID
                break;
            }
        }

        // Verifica si se encontró el índice del ID
        if (productoIdIndex == -1 || productoIdIndex >= detalle.length) {
            System.out.println("No se pudo encontrar el ID del producto en el mensaje: " + mensaje);
            return; // Termina si no se encuentra el ID del producto
        }

        int productoId = Integer.parseInt(detalle[productoIdIndex]); // Asume que el siguiente es el ID
        int sucursalId = 1; // Suponiendo que la sucursal ID es 1, ajusta según tu lógica

        // Verificar si el producto existe en la base de datos
        if (!DatabaseConnection.productoExists(productoId)) {
            System.out.println("El producto con ID " + productoId + " no existe en la base de datos.");
            return; // Termina la ejecución si el producto no existe
        }

        // Crear la instancia de la compra
        Compra compra = new Compra(0, new Date(), sucursalId, productoId, cantidad, 10.50, cacheProduct);
        compra.insertarCompra(); // Inserta la compra en la base de datos

        // Crear la instancia de la venta (puedes modificar la lógica de precio o cantidad según sea necesario)
        Venta venta = new Venta(0, new Date(), sucursalId, productoId, cantidad, 10.50, cacheProduct);
        venta.insertarVenta(); // Inserta la venta en la base de datos
    }
}
