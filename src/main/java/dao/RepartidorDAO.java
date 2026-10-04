package dao;

import modelo.Repartidor;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Define las operaciones de acceso a datos de los repartidores.
public interface RepartidorDAO {

    // Registra un nuevo repartidor en la base de datos.
    void create(Repartidor repartidor) throws SQLException;
    List<Repartidor> readAll() throws SQLException;
    // Consulta un repartidor por su ID.
    Optional<Repartidor> buscarPorId(int id) throws SQLException;

    // Actualiza los datos de un repartidor existente.
    void update(Repartidor repartidor) throws SQLException;

    // Elimina un repartidor por su ID.
    void delete(int id) throws SQLException;

}