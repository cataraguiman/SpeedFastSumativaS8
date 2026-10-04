package dao.implement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import config.ConexionDB;
import dao.PedidoDAO;
import modelo.Pedido;

// Implementa las operaciones de los pedidos en la BD.
public class PedidoDAOImpl implements PedidoDAO {

    // Guarda un nuevo pedido en la BD.
    @Override
    public void create(Pedido pedido) throws SQLException {

        // Consulta para agregar pedidos.
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            // Agrega la dirección del pedido.
            ps.setString(1, pedido.getDireccion());

            // Agrega el tipo de pedido.
            ps.setString(2, pedido.getTipo());

            // Agrega el estado del pedido.
            ps.setString(3, pedido.getEstado());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    pedido.setId(claves.getInt(1));
                }
            }
        }
    }

    // Muestra los pedidos agregados/registrados.
    @Override
    public List<Pedido> readAll() throws SQLException {

        // Busca todos los pedidos.
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos ORDER BY id";
        List<Pedido> pedidos = new ArrayList<>();

        // Abre la conexión y ejecuta la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre los pedidos encontrados.
            while (rs.next()) {
                pedidos.add(mapear(rs));
            }
        }

        // Devuelve la lista de pedidos.
        return pedidos;
    }

    // Toma los datos encontrados y crea un objeto Pedido.
    private Pedido mapear(ResultSet rs) throws SQLException {

        return new Pedido(
                rs.getInt("id"),
                rs.getString("direccion"),
                rs.getString("tipo"),
                rs.getString("estado")
        );
    }

    // Busca un pedido por su ID.
    @Override
    public Optional<Pedido> buscarPorId(int id) throws SQLException {

        // Consulta para buscar un pedido.
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID a la consulta.
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                // Comprueba si se encontró el pedido.
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Actualiza/cambia los datos de un pedido.
    @Override
    public void update(Pedido pedido) throws SQLException {

        // Consulta para actualizar/cambiar los datos del pedido.
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega la nueva dirección.
            ps.setString(1, pedido.getDireccion());

            // Agrega el nuevo tipo.
            ps.setString(2, pedido.getTipo());

            // Agrega el nuevo estado.
            ps.setString(3, pedido.getEstado());

            // Agrega el ID del pedido.
            ps.setInt(4, pedido.getId());

            // Ejecuta la actualización.
            int filas = ps.executeUpdate();

            // Comprueba si el pedido existe.
            if (filas == 0) {
                throw new SQLException(
                        "El pedido con ID \" + pedido.getId() + \" no fue encontrado."
                );
            }
        }
    }

    // Elimina un pedido por su ID.
    @Override
    public void delete(int id) throws SQLException {

        // Consulta para eliminar un pedido.
        String sql = "DELETE FROM pedidos WHERE id = ?";

        // Abre la conexión y prepara la consulta.
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Agrega el ID del pedido.
            ps.setInt(1, id);

            // Ejecuta la eliminación.
            int filas = ps.executeUpdate();

            // Comprueba si el pedido existe.
            if (filas == 0) {
                throw new SQLException(
                        "El ID " + id + " no corresponde a un pedido."
                );
            }
        }
    }
}