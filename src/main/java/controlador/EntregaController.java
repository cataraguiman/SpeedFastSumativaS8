package controlador;

import dao.EntregaDAO;
import modelo.Entrega;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

// Permite trabajar con los datos de las entregas.
public class EntregaController {

    private final EntregaDAO entregaDAO;

    // Recibe el DAO que usaremos para trabajar con la BD.
    public EntregaController(EntregaDAO entregaDAO) {
        this.entregaDAO = entregaDAO;
    }

    // Registra una nueva entrega.
    public void crear(int idPedido, int idRepartidor, LocalDate fecha, LocalTime hora)
            throws SQLException {

        entregaDAO.create(
                new Entrega(idPedido, idRepartidor, fecha, hora)
        );
    }

    // Obtiene todas las entregas.
    public List<Entrega> listar() throws SQLException {
        return entregaDAO.readAll();
    }

    // Busca una entrega por su ID.
    public Optional<Entrega> buscar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }

        return entregaDAO.buscarPorId(id);
    }

    // Actualiza los datos de una entrega.
    public void actualizar(int id, int idPedido, int idRepartidor,
                           LocalDate fecha, LocalTime hora)
            throws SQLException {

        entregaDAO.update(
                new Entrega(id, idPedido, idRepartidor, fecha, hora)
        );
    }

    // Elimina una entrega por su ID.
    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }

        entregaDAO.delete(id);
    }
}