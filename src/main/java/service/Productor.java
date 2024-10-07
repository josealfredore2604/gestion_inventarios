package service;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Clase Productor que representa un productor de mensajes para la cola ActiveMQ.
 * Esta clase es responsable de enviar mensajes relacionados con transacciones de compra y venta a la cola de mensajes.
 */
public class Productor {
    /**
     * Envía un mensaje a la cola de mensajes de ActiveMQ.
     *
     * @param mensaje El mensaje de texto que se desea enviar a la cola.
     */
    public void enviarMensaje(String mensaje) {
        try {
            // Crear una conexión a ActiveMQ utilizando la URL del broker
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start(); // Iniciar la conexión

            // Crear una sesión sin transacciones y con reconocimiento automático de mensajes
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Crear la cola donde se enviarán los mensajes
            Queue queue = session.createQueue("TRANSACCIONES_QUEUE");

            // Crear un productor de mensajes para la cola
            MessageProducer producer = session.createProducer(queue);
            // Crear un mensaje de texto con el contenido proporcionado
            TextMessage textMessage = session.createTextMessage(mensaje);
            // Enviar el mensaje a la cola
            producer.send(textMessage);

            // Imprimir en consola el mensaje enviado
            System.out.println("Mensaje enviado: " + mensaje);

            // Cerrar la sesión y la conexión después de enviar el mensaje
            session.close();
            connection.close();
        } catch (Exception e) {
            // Manejo de excepciones para cualquier error que ocurra durante el envío del mensaje
            e.printStackTrace();
        }
    }
}

