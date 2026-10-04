package dao;

import modelo.Entrega;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Define las operaciones de acceso a datos de las entregas.
public interface EntregaDAO {

    // Registra una nueva entrega en la base de datos.
    void create(Entrega entrega) throws SQLException;
    List<Entrega> readAll() throws SQLException;

    Optional<Entrega> buscarPorId(int id) throws SQLException;
    // Actualiza los datos de una entrega existente.
    void update(Entrega entrega) throws SQLException;

    // Elimina una entrega por su ID.
    void delete(int id) throws SQLException;
}