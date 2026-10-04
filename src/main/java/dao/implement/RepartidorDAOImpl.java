package dao.implement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import config.ConexionDB;
import dao.RepartidorDAO;
import modelo.Repartidor;

// Implementa las operaciones de los repartidores en la BD.
public class RepartidorDAOImpl implements RepartidorDAO {

    // Registra un nuevo repartidor en la BD.
    @Override
    public void create(Repartidor repartidor) throws SQLException {

        // Consulta para agregar un nuevo repartidor.
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            // Agrega el nombre del repartidor a la consulta.
            ps.setString(1, repartidor.getNombre());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    // Guarda el ID generado en el objeto.
                    repartidor.setId(claves.getInt(1));
                }
            }
        }
    }

    // Obtiene todos los repartidores registrados.
    @Override
    public List<Repartidor> readAll() throws SQLException {

        // Consulta para obtener todos los repartidores.
        String sql = "SELECT id, nombre FROM repartidores ORDER BY id";

        List<Repartidor> repartidores = new ArrayList<>();

        // Abre la conexión y ejecuta la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre los repartidores encontrados.
            while (rs.next()) {
                repartidores.add(mapear(rs));
            }
        }

        // Devuelve la lista de repartidores.
        return repartidores;
    }

    // Toma los datos encontrados en un objeto Repartidor.
    private Repartidor mapear(ResultSet rs) throws SQLException {

        return new Repartidor(
                rs.getInt("id"),
                rs.getString("nombre")
        );
    }

    // Busca un repartidor por su ID.
    @Override
    public Optional<Repartidor> buscarPorId(int id) throws SQLException {

        // Consulta para buscar un repartidor.
        String sql = "SELECT id, nombre FROM repartidores WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID a la consulta.
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                // Comprueba si se encontró el repartidor.
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Actualiza/cambia los datos de un repartidor.
    @Override
    public void update(Repartidor repartidor) throws SQLException {

        // Consulta para actualizar/cambiar el nombre.
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el nuevo nombre.
            ps.setString(1, repartidor.getNombre());

            // Agrega el ID del repartidor.
            ps.setInt(2, repartidor.getId());

            // Ejecuta la actualización.
            int filas = ps.executeUpdate();

            // Comprueba si el repartidor existe.
            if (filas == 0) {
                throw new SQLException(
                        "No existe el repartidor con ID: " + repartidor.getId()
                );
            }
        }
    }

    // Elimina un repartidor por su ID.
    @Override
    public void delete(int id) throws SQLException {

        // Consulta para eliminar un repartidor.
        String sql = "DELETE FROM repartidores WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID del repartidor.
            ps.setInt(1, id);

            // Ejecuta la eliminación.
            int filas = ps.executeUpdate();

            // Comprueba si el repartidor existe.
            if (filas == 0) {
                throw new SQLException(
                        "No se encontró un repartidor con el ID:" + id
                );
            }
        }
    }
}