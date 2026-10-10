package controlador;

import dao.CategoriaDAO;
import dao.impl.CategoriaDAOImpl;
import modelo.Categoria;

import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con las categorías.
 */
public class ControladorCategorias {

    private final CategoriaDAO categoriaDAO;

    public ControladorCategorias() {
        categoriaDAO = new CategoriaDAOImpl();
    }

    public boolean registrarCategoria(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            return false;
        }

        Categoria categoria =
                new Categoria(nombre.trim());

        return categoriaDAO.create(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.readAll();
    }

    public boolean actualizarCategoria(
            int id,
            String nombre
    ) {

        if (id <= 0
                || nombre == null
                || nombre.isBlank()) {

            return false;
        }

        Categoria categoria =
                new Categoria(
                        id,
                        nombre.trim()
                );

        return categoriaDAO.update(categoria);
    }

    public boolean eliminarCategoria(int id) {

        if (id <= 0) {
            return false;
        }

        return categoriaDAO.delete(id);
    }
}
