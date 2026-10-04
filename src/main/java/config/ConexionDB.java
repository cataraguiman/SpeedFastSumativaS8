package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// CLASE DE CONFIGURACIÓN, DE INFRAESTRUCTURA DEL CÓDIGO.
public class ConexionDB {

    // Datos de conexión a nuestra base de datos.
    private static final String URL = "jdbc:mysql://localhost:3306/databasePOOII";
    private static final String USUARIO = "cataraguiman";
    private static final String PASSWORD = "6429567";

    // Constructor vacío
    public ConexionDB() {

    }

    // Método público.
    public static Connection obtenerConexion() throws SQLException {

        // Entrega la conexión mediante DriverManager.
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    // Método para comprobar el nombre de la base de datos.
    public static String obtenerNombreBaseDatos() throws SQLException {

        String sql = "SELECT DATABASE()";

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString(1);
            }
        }

        return null;
    }
}