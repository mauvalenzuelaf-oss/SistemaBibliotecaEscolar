package dao.impl;

import dao.LibroDAO;
import modelo.Libro;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para los libros.
 */
public class LibroDAOImpl implements LibroDAO {

    /**
     * Registra un nuevo libro.
     */
    @Override
    public boolean create(Libro libro) {

        String sql =
                "INSERT INTO libros "
                        + "(titulo, autor, isbn, editorial, stock, id_categoria) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        libro.getTitulo()
                );

                stmt.setString(
                        2,
                        libro.getAutor()
                );

                stmt.setString(
                        3,
                        libro.getIsbn()
                );

                stmt.setString(
                        4,
                        libro.getEditorial()
                );

                stmt.setInt(
                        5,
                        libro.getStock()
                );

                stmt.setInt(
                        6,
                        libro.getIdCategoria()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los libros registrados.
     */
    @Override
    public List<Libro> readAll() {

        List<Libro> libros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM libros ORDER BY id";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(sql);

                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    Libro libro =
                            new Libro(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getString("isbn"),
                                    rs.getString("editorial"),
                                    rs.getInt("stock"),
                                    rs.getInt("id_categoria")
                            );

                    libros.add(libro);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libros;
    }

    /**
     * Actualiza un libro existente.
     */
    @Override
    public boolean update(Libro libro) {

        String sql =
                "UPDATE libros "
                        + "SET titulo = ?, "
                        + "autor = ?, "
                        + "isbn = ?, "
                        + "editorial = ?, "
                        + "stock = ?, "
                        + "id_categoria = ? "
                        + "WHERE id = ?";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        libro.getTitulo()
                );

                stmt.setString(
                        2,
                        libro.getAutor()
                );

                stmt.setString(
                        3,
                        libro.getIsbn()
                );

                stmt.setString(
                        4,
                        libro.getEditorial()
                );

                stmt.setInt(
                        5,
                        libro.getStock()
                );

                stmt.setInt(
                        6,
                        libro.getIdCategoria()
                );

                stmt.setInt(
                        7,
                        libro.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un libro según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM libros WHERE id = ?";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setInt(1, id);

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un libro según su ID utilizando
     * una conexión existente.
     */
    public Libro readById(
            Connection conn,
            int id
    ) throws SQLException {

        String sql =
                "SELECT * FROM libros WHERE id = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("isbn"),
                            rs.getString("editorial"),
                            rs.getInt("stock"),
                            rs.getInt("id_categoria")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Actualiza el stock de un libro utilizando
     * una conexión existente.
     */
    public synchronized boolean updateStock(
            Connection conn,
            int idLibro,
            int nuevoStock
    ) throws SQLException {

        String sql =
                "UPDATE libros "
                        + "SET stock = ? "
                        + "WHERE id = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idLibro);

            return stmt.executeUpdate() > 0;
        }
    }

}