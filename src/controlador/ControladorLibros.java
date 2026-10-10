package controlador;

import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import modelo.Libro;
import util.Validador;

import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los libros.
 */
public class ControladorLibros {

    private final LibroDAO libroDAO;

    /**
     * Inicializa el DAO utilizado
     * por el controlador.
     */
    public ControladorLibros() {

        libroDAO =
                new LibroDAOImpl();
    }

    /**
     * Registra un libro nuevo.
     */
    public boolean registrarLibro(
            String titulo,
            String autor,
            String isbn,
            String editorial,
            int stock,
            int idCategoria
    ) {

        if (!Validador.validarTitulo(titulo)
                || !Validador.validarAutor(autor)
                || !Validador.validarIsbn(isbn)
                || !Validador.validarEditorial(editorial)
                || !Validador.validarStock(stock)
                || idCategoria <= 0) {

            return false;
        }

        String isbnLimpio =
                isbn.replace("-", "")
                        .replace(" ", "")
                        .trim();

        Libro libro =
                new Libro(
                        titulo.trim(),
                        autor.trim(),
                        isbnLimpio,
                        editorial.trim(),
                        stock,
                        idCategoria
                );

        return libroDAO.create(libro);
    }

    /**
     * Obtiene todos los libros registrados.
     */
    public List<Libro> listarLibros() {
        return libroDAO.readAll();
    }

    /**
     * Actualiza los datos de un libro.
     */
    public boolean actualizarLibro(
            int id,
            String titulo,
            String autor,
            String isbn,
            String editorial,
            int stock,
            int idCategoria
    ) {

        if (id <= 0
                || !Validador.validarTitulo(titulo)
                || !Validador.validarAutor(autor)
                || !Validador.validarIsbn(isbn)
                || !Validador.validarEditorial(editorial)
                || !Validador.validarStock(stock)
                || idCategoria <= 0) {

            return false;
        }

        String isbnLimpio =
                isbn.replace("-", "")
                        .replace(" ", "")
                        .trim();

        Libro libro =
                new Libro(
                        id,
                        titulo.trim(),
                        autor.trim(),
                        isbnLimpio,
                        editorial.trim(),
                        stock,
                        idCategoria
                );

        return libroDAO.update(libro);
    }

    /**
     * Elimina un libro según su ID.
     */
    public boolean eliminarLibro(
            int id
    ) {

        if (id <= 0) {
            return false;
        }

        return libroDAO.delete(id);
    }
}