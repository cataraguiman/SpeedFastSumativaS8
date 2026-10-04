package controlador;
import dao.RepartidorDAO;
import modelo.Repartidor;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Permite trabajar con los datos de los repartidores.
public class RepartidorController {

    private final RepartidorDAO repartidorDAO;

    // Recibe el DAO que usaremos para trabajar con la BD.
    public RepartidorController(RepartidorDAO repartidorDAO) {
        this.repartidorDAO = repartidorDAO;
    }

    // Registra un nuevo repartidor.
    public void crear(String nombre) throws SQLException {
        repartidorDAO.create(new Repartidor(nombre));
    }

    // Obtiene todos los repartidores.
    public List<Repartidor> listar() throws SQLException {
        return repartidorDAO.readAll();
    }

    // Busca un repartidor por su ID.
    public Optional<Repartidor> buscar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }

        return repartidorDAO.buscarPorId(id);
    }

    // Actualiza los datos de un repartidor.
    public void actualizar(int id, String nombre) throws SQLException {
        repartidorDAO.update(new Repartidor(id, nombre));
    }

    // Elimina un repartidor por su ID.
    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }

        repartidorDAO.delete(id);
    }
}
