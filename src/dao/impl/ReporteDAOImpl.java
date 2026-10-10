package dao.impl;

import dao.ReporteDAO;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las consultas utilizadas
 * para generar reportes de la biblioteca.
 */
public class ReporteDAOImpl implements ReporteDAO {

    /**
     * Obtiene los libros ordenados según
     * la cantidad de préstamos registrados.
     */
    @Override
    public List<Object[]> librosMasPrestados() {

        List<Object[]> resultados =
                new ArrayList<>();

        String sql =
                "SELECT l.id, l.titulo, "
                        + "COUNT(p.id) AS total_prestamos "
                        + "FROM libros l "
                        + "LEFT JOIN prestamos p "
                        + "ON l.id = p.id_libro "
                        + "GROUP BY l.id, l.titulo "
                        + "ORDER BY total_prestamos DESC, "
                        + "l.titulo";

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

                    resultados.add(
                            new Object[]{
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getInt("total_prestamos")
                            }
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Obtiene el historial de préstamos
     * de un estudiante.
     */
    @Override
    public List<Object[]> historialPorEstudiante(
            int idEstudiante
    ) {

        List<Object[]> resultados =
                new ArrayList<>();

        String sql =
                "SELECT p.id, l.titulo, "
                        + "p.fecha_prestamo, "
                        + "p.fecha_devolucion, "
                        + "p.devuelto "
                        + "FROM prestamos p "
                        + "INNER JOIN libros l "
                        + "ON p.id_libro = l.id "
                        + "WHERE p.id_estudiante = ? "
                        + "ORDER BY p.fecha_prestamo DESC";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setInt(
                        1,
                        idEstudiante
                );

                try (ResultSet rs =
                             stmt.executeQuery()) {

                    while (rs.next()) {

                        resultados.add(
                                new Object[]{
                                        rs.getInt("id"),
                                        rs.getString("titulo"),
                                        rs.getDate("fecha_prestamo"),
                                        rs.getDate("fecha_devolucion"),
                                        rs.getBoolean("devuelto")
                                                ? "DEVUELTO"
                                                : "EN PRÉSTAMO"
                                }
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Obtiene los libros que actualmente
     * se encuentran en préstamo.
     */
    @Override
    public List<Object[]> librosActualmentePrestados() {

        List<Object[]> resultados =
                new ArrayList<>();

        String sql =
                "SELECT p.id, e.nombre AS estudiante, "
                        + "l.titulo, "
                        + "p.fecha_prestamo, "
                        + "p.fecha_devolucion "
                        + "FROM prestamos p "
                        + "INNER JOIN estudiantes e "
                        + "ON p.id_estudiante = e.id "
                        + "INNER JOIN libros l "
                        + "ON p.id_libro = l.id "
                        + "WHERE p.devuelto = FALSE "
                        + "ORDER BY p.fecha_devolucion";

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

                    resultados.add(
                            new Object[]{
                                    rs.getInt("id"),
                                    rs.getString("estudiante"),
                                    rs.getString("titulo"),
                                    rs.getDate("fecha_prestamo"),
                                    rs.getDate("fecha_devolucion")
                            }
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
