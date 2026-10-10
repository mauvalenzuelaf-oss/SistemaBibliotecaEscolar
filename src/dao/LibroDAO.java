package dao;

import modelo.Libro;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para los libros.
 */
public interface LibroDAO {

    boolean create(Libro libro);

    List<Libro> readAll();

    boolean update(Libro libro);

    boolean delete(int id);
}
