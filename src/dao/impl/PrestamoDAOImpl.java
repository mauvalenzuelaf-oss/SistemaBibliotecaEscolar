package dao.impl;

import dao.PrestamoDAO;
import modelo.Prestamo;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para los préstamos.
 */
public class PrestamoDAOImpl implements PrestamoDAO {

    /**
     * Registra un nuevo préstamo.
     */
    @Override
    public boolean create(Prestamo prestamo) {

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            return create(conn, prestamo);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registra un préstamo utilizando
     * una conexión existente.
     */
    public boolean create(
            Connection conn,
            Prestamo prestamo
    ) throws SQLException {

        String sql =
                "INSERT INTO prestamos "
                        + "(id_estudiante, id_libro, fecha_prestamo, "
                        + "fecha_devolucion, devuelto) "
                        + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    prestamo.getIdEstudiante()
            );

            stmt.setInt(
                    2,
                    prestamo.getIdLibro()
            );

            stmt.setDate(
                    3,
                    prestamo.getFechaPrestamo()
            );

            stmt.setDate(
                    4,
                    prestamo.getFechaDevolucion()
            );

            stmt.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene todos los préstamos registrados.
     */
    @Override
    public List<Prestamo> readAll() {

        List<Prestamo> prestamos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM prestamos ORDER BY id";

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

                    Prestamo prestamo =
                            new Prestamo(
                                    rs.getInt("id"),
                                    rs.getInt("id_estudiante"),
                                    rs.getInt("id_libro"),
                                    rs.getDate("fecha_prestamo"),
                                    rs.getDate("fecha_devolucion"),
                                    rs.getBoolean("devuelto")
                            );

                    prestamos.add(prestamo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }

    /**
     * Actualiza un préstamo existente.
     */
    @Override
    public boolean update(Prestamo prestamo) {

        String sql =
                "UPDATE prestamos "
                        + "SET id_estudiante = ?, "
                        + "id_libro = ?, "
                        + "fecha_prestamo = ?, "
                        + "fecha_devolucion = ?, "
                        + "devuelto = ? "
                        + "WHERE id = ?";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setInt(
                        1,
                        prestamo.getIdEstudiante()
                );

                stmt.setInt(
                        2,
                        prestamo.getIdLibro()
                );

                stmt.setDate(
                        3,
                        prestamo.getFechaPrestamo()
                );

                stmt.setDate(
                        4,
                        prestamo.getFechaDevolucion()
                );

                stmt.setBoolean(
                        5,
                        prestamo.isDevuelto()
                );

                stmt.setInt(
                        6,
                        prestamo.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un préstamo según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM prestamos WHERE id = ?";

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
     * Busca un préstamo según su ID utilizando
     * una conexión existente.
     */
    public Prestamo readById(
            Connection conn,
            int id
    ) throws SQLException {

        String sql =
                "SELECT * FROM prestamos WHERE id = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new Prestamo(
                            rs.getInt("id"),
                            rs.getInt("id_estudiante"),
                            rs.getInt("id_libro"),
                            rs.getDate("fecha_prestamo"),
                            rs.getDate("fecha_devolucion"),
                            rs.getBoolean("devuelto")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Actualiza un préstamo utilizando
     * una conexión existente.
     */
    public boolean update(
            Connection conn,
            Prestamo prestamo
    ) throws SQLException {

        String sql =
                "UPDATE prestamos "
                        + "SET id_estudiante = ?, "
                        + "id_libro = ?, "
                        + "fecha_prestamo = ?, "
                        + "fecha_devolucion = ?, "
                        + "devuelto = ? "
                        + "WHERE id = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    prestamo.getIdEstudiante()
            );

            stmt.setInt(
                    2,
                    prestamo.getIdLibro()
            );

            stmt.setDate(
                    3,
                    prestamo.getFechaPrestamo()
            );

            stmt.setDate(
                    4,
                    prestamo.getFechaDevolucion()
            );

            stmt.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            stmt.setInt(
                    6,
                    prestamo.getId()
            );

            return stmt.executeUpdate() > 0;
        }
    }

}
