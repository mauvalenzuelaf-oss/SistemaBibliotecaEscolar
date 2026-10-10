package dao.impl;

import dao.CategoriaDAO;
import modelo.Categoria;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para las categorías.
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    /**
     * Registra una nueva categoría.
     */
    @Override
    public boolean create(Categoria categoria) {

        String sql =
                "INSERT INTO categorias (nombre) VALUES (?)";

        try {
            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        categoria.getNombre()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las categorías registradas.
     */
    @Override
    public List<Categoria> readAll() {

        List<Categoria> categorias =
                new ArrayList<>();

        String sql =
                "SELECT * FROM categorias ORDER BY id";

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

                    Categoria categoria =
                            new Categoria(
                                    rs.getInt("id"),
                                    rs.getString("nombre")
                            );

                    categorias.add(categoria);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    /**
     * Actualiza una categoría existente.
     */
    @Override
    public boolean update(Categoria categoria) {

        String sql =
                "UPDATE categorias "
                        + "SET nombre = ? "
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
                        categoria.getNombre()
                );

                stmt.setInt(
                        2,
                        categoria.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una categoría según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM categorias WHERE id = ?";

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
}