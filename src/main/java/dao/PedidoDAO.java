package dao;
import modelo.Pedido;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Define las operaciones de acceso a datos de los pedidos.
public interface PedidoDAO {

    // Registra un nuevo pedido en la base de datos.
    void create(Pedido pedido) throws SQLException;
    List<Pedido> readAll() throws SQLException;
    // Consulta un pedido por su ID.
    Optional<Pedido> buscarPorId(int id) throws SQLException;

    // Actualiza los datos de un pedido existente.
    void update(Pedido pedido) throws SQLException;

    // Elimina un pedido por su ID.
    void delete(int id) throws SQLException;
}