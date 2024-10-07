# Sistema de Gestión de Inventarios

Este proyecto es un sistema de gestión de inventarios que utiliza ActiveMQ para el manejo de transacciones de compra y venta entre sucursales y una base de datos MySQL para el almacenamiento de información. El sistema también implementa un sistema de caché para mejorar el rendimiento.

## Estructura del Proyecto

```
.
├── cache
│   ├── CacheBranch.java
│   └── CacheProduct.java
├── database
│   └── DatabaseConnection.java
├── model
│   ├── Compra.java
│   ├── Transaccion.java
│   └── Venta.java
├── org
│   └── example
│       ├── ConsumidorMain.java
│       ├── MainHilos.java
│       ├── Main.java
│       ├── MainPrueba.java
│       └── ProductorMain.java
├── service
│   ├── Consumidor.java
│   └── Productor.java
```

## Requisitos del Sistema

Para ejecutar este proyecto, necesitas cumplir con los siguientes requisitos:

### Requisitos de Software

1. **Java Development Kit (JDK)**
   - Necesitas tener instalado JDK 11 o superior. Puedes descargarlo desde [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) o [OpenJDK](https://openjdk.java.net/).

2. **Apache ActiveMQ**
   - Debes tener instalado y en ejecución un servidor de ActiveMQ. Puedes descargarlo desde [ActiveMQ](http://activemq.apache.org/download.html).

3. **MySQL**
   - Un servidor de bases de datos MySQL en funcionamiento. Puedes descargarlo desde [MySQL](https://dev.mysql.com/downloads/mysql/).

4. **NetBeans IDE**
   - Se recomienda usar NetBeans IDE para facilitar el desarrollo y la gestión del proyecto. Puedes descargarlo desde [NetBeans](https://netbeans.apache.org/download/index.html).

### Requisitos de Base de Datos

1. **Configuración de la Base de Datos**
   - Crea una base de datos llamada `gestion_inventarios`.
   - Asegúrate de que la base de datos tenga las siguientes tablas:

```sql
CREATE TABLE Sucursales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL
);

CREATE TABLE Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('COMPRA', 'VENTA') NOT NULL,
    sucursalId INT NOT NULL,
    productoId INT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10, 2) NOT NULL,
    fechaHora DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sucursalId) REFERENCES Sucursales(id),
    FOREIGN KEY (productoId) REFERENCES Productos(id)
);
```

### Requisitos de Conexión

1. **Configuración de Conexión en `DatabaseConnection.java`**
    - Modifica las credenciales de conexión a la base de datos en el archivo `DatabaseConnection.java`:
      ```java
      private static final String URL = "jdbc:mysql://localhost:3306/gestion_inventarios";
      private static final String USER = "dbuser"; // Cambia a tu usuario de base de datos
      private static final String PASSWORD = "dbpass"; // Cambia a tu contraseña de base de datos
      ```

## Cómo Ejecutar el Proyecto

1. **Ejecutar el Servidor ActiveMQ**
    - Asegúrate de que el servidor ActiveMQ esté en ejecución en `tcp://localhost:61616`.

2. **Ejecutar el Proyecto en NetBeans**
    - Abre el proyecto en NetBeans y ejecuta la clase `Main`.

3. **Interactuar con el Sistema**
    - Selecciona las opciones para insertar sucursales, insertar productos, o ejecutar el productor y el consumidor según sea necesario.
