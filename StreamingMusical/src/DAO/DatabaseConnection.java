package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/streaming_musical";
    private static final String USER = "root";
    private static final String PASSWORD = "Picado12";

    /**
     * Establece una conexión con la base de datos.
     * @return El objeto Connection si la conexión es exitosa, de lo contrario null.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos exitosa.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de JDBC de MySQL no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Cierra la conexión con la base de datos.
     * @param connection El objeto Connection a cerrar.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}