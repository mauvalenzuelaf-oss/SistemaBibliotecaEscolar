package dao;

import modelo.Categoria;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para las categorías.
 */
public interface CategoriaDAO {

    boolean create(Categoria categoria);

    List<Categoria> readAll();

    boolean update(Categoria categoria);

    boolean delete(int id);
}
