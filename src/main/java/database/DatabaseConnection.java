package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DatabaseConnection que proporciona métodos para conectarse a la base de datos
 * y realizar operaciones CRUD básicas (como verificar, insertar datos) para gestionar
 * un sistema de inventario. Los métodos están diseñados para gestionar sucursales,
 * productos y transacciones en la base de datos.
 */
public class DatabaseConnection {
    // URL de la base de datos, usuario y contraseña.
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_inventarios"; // Cambia a tu URL de base de datos
    private static final String USER = "dbuser"; // Cambia a tu usuario de base de datos
    private static final String PASSWORD = "dbpass"; // Cambia a tu contraseña de base de datos

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return Una conexión válida a la base de datos si la conexión tiene éxito; null en caso de error.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si no se puede establecer la conexión
            return null;
        }
    }

    /**
     * Verifica si un producto con un ID específico existe en la base de datos.
     *
     * @param productoId El identificador único del producto.
     * @return true si el producto existe en la base de datos; false si no existe o si ocurre un error.
     */
    public static boolean productoExists(int productoId) {
        String sql = "SELECT COUNT(*) FROM Productos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si el producto existe
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si ocurre una excepción SQL
        }
        return false; // Retorna false si ocurre un error o si el producto no existe
    }

    /**
     * Inserta una sucursal en la base de datos.
     *
     * @param nombre    El nombre de la sucursal.
     * @param direccion La dirección de la sucursal.
     * @param telefono  El número de teléfono de la sucursal.
     */
    public static void insertarSucursal(String nombre, String direccion, String telefono) {
        String sql = "INSERT INTO Sucursales (nombre, direccion, telefono) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, direccion);
            pstmt.setString(3, telefono);
            pstmt.executeUpdate();
            System.out.println("Sucursal insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si ocurre una excepción SQL
        }
    }

    /**
     * Inserta un producto en la base de datos.
     *
     * @param nombre       El nombre del producto.
     * @param descripcion  La descripción del producto.
     * @param precio       El precio del producto.
     */
    public static void insertarProducto(String nombre, String descripcion, double precio) {
        String sql = "INSERT INTO Productos (nombre, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setDouble(3, precio);
            pstmt.executeUpdate();
            System.out.println("Producto insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si ocurre una excepción SQL
        }
    }

    /**
     * Inserta una transacción genérica en la base de datos.
     *
     * @param mensaje El mensaje de la transacción.
     */
    public static void insertarTransaccion(String mensaje) {
        String sql = "INSERT INTO Transacciones (mensaje) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mensaje);
            pstmt.executeUpdate();
            System.out.println("Transacción insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si ocurre una excepción SQL
        }
    }

    /**
     * Inserta una transacción con detalles específicos en la base de datos.
     *
     * @param tipo          El tipo de transacción (COMPRA o VENTA).
     * @param sucursalId    El ID de la sucursal que realiza la transacción.
     * @param productoId    El ID del producto asociado con la transacción.
     * @param cantidad      La cantidad de producto.
     * @param precioUnitario El precio unitario del producto.
     */
    public static void insertarTransaccion(String tipo, int sucursalId, int productoId, int cantidad, double precioUnitario) {
        String sql = "INSERT INTO Transacciones (tipo, sucursalId, productoId, cantidad, precioUnitario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setInt(2, sucursalId);
            pstmt.setInt(3, productoId);
            pstmt.setInt(4, cantidad);
            pstmt.setDouble(5, precioUnitario);
            pstmt.executeUpdate();
            System.out.println("Transacción insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error si ocurre una excepción SQL
        }
    }
}
