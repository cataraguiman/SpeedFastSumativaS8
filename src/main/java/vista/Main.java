package vista;

import config.ConexionDB;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Comprueba la conexión con la base de datos y muestra el resultado al usuario.
        try {

            String nombreBD =
                    ConexionDB.obtenerNombreBaseDatos();

            System.out.println(
                    "Correcta conexión con la base de datos"
            );

            System.out.println(
                    "Base de datos: " + nombreBD
            );

            JOptionPane.showMessageDialog(
                    null,
                    "Correcta conexión con la base de datos\n"
                            + "Base de datos: " + nombreBD,
                    "Conexión a MySQL",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Abre el menú principal del sistema.
            SwingUtilities.invokeLater(() -> {

                VentanaPrincipal ventana =
                        new VentanaPrincipal();

                ventana.setVisible(true);
            });

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión: "
                            + e.getMessage()
            );

            JOptionPane.showMessageDialog(
                    null,
                    "Error al conectar con la base de datos.\n"
                            + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}