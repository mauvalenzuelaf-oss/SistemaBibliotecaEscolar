package dao.impl;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones de acceso
 * a datos para los usuarios.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    /**
     * Registra un usuario en la base de datos.
     */
    @Override
    public boolean create(
            Usuario usuario
    ) {

        String sql =
                "INSERT INTO usuarios "
                        + "(nombre, rut, correo, `contraseña`, rol) "
                        + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        usuario.getNombre()
                );

                stmt.setString(
                        2,
                        usuario.getRut()
                );

                stmt.setString(
                        3,
                        usuario.getCorreo()
                );

                stmt.setString(
                        4,
                        usuario.getContrasena()
                );

                stmt.setString(
                        5,
                        usuario.getRol()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los usuarios registrados.
     */
    @Override
    public List<Usuario> readAll() {

        List<Usuario> usuarios =
                new ArrayList<>();

        String sql =
                "SELECT * FROM usuarios "
                        + "ORDER BY id";

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

                    Usuario usuario =
                            new Usuario(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("rut"),
                                    rs.getString("correo"),
                                    rs.getString("contraseña"),
                                    rs.getString("rol")
                            );

                    usuarios.add(usuario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Actualiza los datos de un usuario.
     */
    @Override
    public boolean update(
            Usuario usuario
    ) {

        String sql =
                "UPDATE usuarios "
                        + "SET nombre = ?, "
                        + "rut = ?, "
                        + "correo = ?, "
                        + "`contraseña` = ?, "
                        + "rol = ? "
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
                        usuario.getNombre()
                );

                stmt.setString(
                        2,
                        usuario.getRut()
                );

                stmt.setString(
                        3,
                        usuario.getCorreo()
                );

                stmt.setString(
                        4,
                        usuario.getContrasena()
                );

                stmt.setString(
                        5,
                        usuario.getRol()
                );

                stmt.setInt(
                        6,
                        usuario.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario según su ID.
     */
    @Override
    public boolean delete(
            int id
    ) {

        String sql =
                "DELETE FROM usuarios "
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
                        id
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un usuario según su correo.
     */
    @Override
    public Usuario buscarPorCorreo(
            String correo
    ) {

        String sql =
                "SELECT * FROM usuarios "
                        + "WHERE correo = ?";

        try {

            Connection conn =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        correo
                );

                try (ResultSet rs =
                             stmt.executeQuery()) {

                    if (rs.next()) {

                        return new Usuario(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("rut"),
                                rs.getString("correo"),
                                rs.getString("contraseña"),
                                rs.getString("rol")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Registra un usuario utilizando
     * una conexión existente.
     */
    public boolean create(
            Connection conn,
            Usuario usuario
    ) throws SQLException {

        String sql =
                "INSERT INTO usuarios "
                        + "(nombre, rut, correo, `contraseña`, rol) "
                        + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    usuario.getNombre()
            );

            stmt.setString(
                    2,
                    usuario.getRut()
            );

            stmt.setString(
                    3,
                    usuario.getCorreo()
            );

            stmt.setString(
                    4,
                    usuario.getContrasena()
            );

            stmt.setString(
                    5,
                    usuario.getRol()
            );

            return stmt.executeUpdate() > 0;
        }
    }
}