package controlador;
import dao.PedidoDAO;
import modelo.Pedido;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Permite trabajar con los datos de los pedidos.
public class PedidoController {

    private final PedidoDAO pedidoDAO;

    // Recibe el DAO que usaremos para trabajar con la BD.
    public PedidoController(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    // Registra un nuevo pedido.
    public void crear(String direccion, String tipo, String estado)
            throws SQLException {

        pedidoDAO.create(
                new Pedido(direccion, tipo, estado)
        );
    }

    // Obtiene todos los pedidos.
    public List<Pedido> listar() throws SQLException {
        return pedidoDAO.readAll();
    }

    // Busca un pedido por su ID.
    public Optional<Pedido> buscar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor a 0."
            );
        }

        return pedidoDAO.buscarPorId(id);
    }

    // Actualiza los datos de un pedido.
    public void actualizar(
            int id,
            String direccion,
            String tipo,
            String estado
    ) throws SQLException {

        pedidoDAO.update(
                new Pedido(
                        id,
                        direccion,
                        tipo,
                        estado
                )
        );
    }

    // Elimina un pedido por su ID.
    public void eliminar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor a 0."
            );
        }

        pedidoDAO.delete(id);
    }
}