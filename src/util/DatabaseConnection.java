package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona la conexión con la base de datos
 * utilizando el patrón Singleton.
 */
public class DatabaseConnection {

    // Instancia única
    private static DatabaseConnection instancia;

    // Conexión con MySQL
    private Connection connection;

    // Datos de conexión
    private String url;
    private String usuario;
    private String contrasena;

    /**
     * Constructor privado que carga
     * la configuración local de la base de datos.
     */
    private DatabaseConnection() {
        cargarConfiguracion();
    }

    /**
     * Carga los datos de conexión desde
     * config/database.properties.
     */
    private void cargarConfiguracion() {

        Properties properties =
                new Properties();

        try (FileInputStream archivo =
                     new FileInputStream(
                             "config/database.properties"
                     )) {

            properties.load(archivo);

            url =
                    properties.getProperty(
                            "db.url"
                    );

            usuario =
                    properties.getProperty(
                            "db.usuario"
                    );

            contrasena =
                    properties.getProperty(
                            "db.contrasena"
                    );

        } catch (IOException e) {

            throw new RuntimeException(
                    "No se pudo cargar "
                            + "config/database.properties",
                    e
            );
        }
    }

    /**
     * Obtiene la única instancia
     * de DatabaseConnection.
     */
    public static synchronized DatabaseConnection getInstance() {

        if (instancia == null) {

            instancia =
                    new DatabaseConnection();
        }

        return instancia;
    }

    /**
     * Obtiene la conexión con MySQL.
     */
    public synchronized Connection getConnection()
            throws SQLException {

        if (connection == null
                || connection.isClosed()) {

            connection =
                    DriverManager.getConnection(
                            url,
                            usuario,
                            contrasena
                    );
        }

        return connection;
    }
}