package dao.implement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import config.ConexionDB;
import dao.EntregaDAO;
import modelo.Entrega;

// Implementa las operaciones de las entregas en la BD.
public class EntregaDAOImpl implements EntregaDAO {

    // Guarda una nueva entrega en la BD.
    @Override
    public void create(Entrega entrega) throws SQLException {

        // Consulta para agregar una entrega.
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            // Agrega el ID del pedido.
            ps.setInt(1, entrega.getIdPedido());

            // Agrega el ID del repartidor.
            ps.setInt(2, entrega.getIdRepartidor());

            // Agrega la fecha de la entrega.
            ps.setDate(3, Date.valueOf(entrega.getFecha()));

            // Agrega la hora de la entrega.
            ps.setTime(4, Time.valueOf(entrega.getHora()));

            // Guarda la entrega.
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    entrega.setId(claves.getInt(1));
                }
            }
        }
    }

    // Muestra las entregas registradas.
    @Override
    public List<Entrega> readAll() throws SQLException {

        // Busca todas las entregas.
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora " + "FROM entregas ORDER BY id";
        List<Entrega> entregas = new ArrayList<>();

        // Abre la conexión y ejecuta la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre las entregas encontradas.
            while (rs.next()) {
                entregas.add(mapear(rs));
            }
        }
        return entregas;
    }

    // Toma los datos encontrados y crea un objeto Entrega.
    private Entrega mapear(ResultSet rs) throws SQLException {

        return new Entrega(
                rs.getInt("id"),
                rs.getInt("id_pedido"),
                rs.getInt("id_repartidor"),
                rs.getDate("fecha").toLocalDate(),
                rs.getTime("hora").toLocalTime()
        );
    }

    // Busca una entrega por su ID.
    @Override
    public Optional<Entrega> buscarPorId(int id) throws SQLException {

        // Consulta para buscar una entrega.
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora " +
                "FROM entregas WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID a la consulta.
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                // Comprueba si se encontró la entrega.
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Actualiza/cambia los datos de una entrega.
    @Override
    public void update(Entrega entrega) throws SQLException {

        // Consulta para actualizar/cambiar los datos de la entrega.
        String sql = "UPDATE entregas " +
                "SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? " +
                "WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el nuevo ID del pedido.
            ps.setInt(1, entrega.getIdPedido());

            // Agrega el nuevo ID del repartidor.
            ps.setInt(2, entrega.getIdRepartidor());

            // Agrega la nueva fecha.
            ps.setDate(3, Date.valueOf(entrega.getFecha()));

            // Agrega la nueva hora.
            ps.setTime(4, Time.valueOf(entrega.getHora()));

            // Agrega el ID de la entrega.
            ps.setInt(5, entrega.getId());

            // Ejecuta la actualización.
            int filas = ps.executeUpdate();

            // Comprueba si la entrega existe.
            if (filas == 0) {
                throw new SQLException(
                        "La entrega con ID " + entrega.getId() + " no fue encontrada."
                );
            }
        }
    }

    // Elimina una entrega por su ID.
    @Override
    public void delete(int id) throws SQLException {

        // Consulta para eliminar una entrega.
        String sql = "DELETE FROM entregas WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID de la entrega.
            ps.setInt(1, id);

            // Ejecuta la eliminación.
            int filas = ps.executeUpdate();

            // Comprueba si la entrega existe.
            if (filas == 0) {
                throw new SQLException(
                        "La entrega con ID " + id + " no fue encontrada."
                );
            }
        }
    }
}