package dao.impl;

import dao.EstudianteDAO;
import modelo.Estudiante;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para los estudiantes.
 */
public class EstudianteDAOImpl implements EstudianteDAO {

    /**
     * Registra un nuevo estudiante.
     */
    @Override
    public boolean create(Estudiante estudiante) {

        String sql =
                "INSERT INTO estudiantes "
                        + "(nombre, rut, curso, correo) "
                        + "VALUES (?, ?, ?, ?)";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        estudiante.getNombre()
                );

                stmt.setString(
                        2,
                        estudiante.getRut()
                );

                stmt.setString(
                        3,
                        estudiante.getCurso()
                );

                stmt.setString(
                        4,
                        estudiante.getCorreo()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los estudiantes registrados.
     */
    @Override
    public List<Estudiante> readAll() {

        List<Estudiante> estudiantes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM estudiantes ORDER BY id";

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

                    Estudiante estudiante =
                            new Estudiante(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("rut"),
                                    rs.getString("correo"),
                                    rs.getString("curso")
                            );

                    estudiantes.add(estudiante);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }

    /**
     * Actualiza un estudiante existente.
     */
    @Override
    public boolean update(Estudiante estudiante) {

        String sql =
                "UPDATE estudiantes "
                        + "SET nombre = ?, "
                        + "rut = ?, "
                        + "curso = ?, "
                        + "correo = ? "
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
                        estudiante.getNombre()
                );

                stmt.setString(
                        2,
                        estudiante.getRut()
                );

                stmt.setString(
                        3,
                        estudiante.getCurso()
                );

                stmt.setString(
                        4,
                        estudiante.getCorreo()
                );

                stmt.setInt(
                        5,
                        estudiante.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un estudiante según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM estudiantes WHERE id = ?";

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
     * Registra un estudiante utilizando
     * una conexión existente.
     */
    public boolean create(
            Connection conn,
            Estudiante estudiante
    ) throws SQLException {

        String sql =
                "INSERT INTO estudiantes "
                        + "(nombre, rut, curso, correo) "
                        + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    estudiante.getNombre()
            );

            stmt.setString(
                    2,
                    estudiante.getRut()
            );

            stmt.setString(
                    3,
                    estudiante.getCurso()
            );

            stmt.setString(
                    4,
                    estudiante.getCorreo()
            );

            return stmt.executeUpdate() > 0;
        }
    }
}
