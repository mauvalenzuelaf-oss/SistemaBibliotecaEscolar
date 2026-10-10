package controlador;

import dao.impl.EstudianteDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Estudiante;
import modelo.Usuario;
import util.DatabaseConnection;
import util.Validador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los estudiantes.
 */
public class ControladorEstudiantes {

    private final EstudianteDAOImpl estudianteDAO;
    private final UsuarioDAOImpl usuarioDAO;

    /**
     * Inicializa los DAO utilizados
     * por el controlador.
     */
    public ControladorEstudiantes() {

        estudianteDAO =
                new EstudianteDAOImpl();

        usuarioDAO =
                new UsuarioDAOImpl();
    }

    /**
     * Registra un estudiante y crea su cuenta
     * de usuario en una misma transacción.
     */
    public boolean registrarEstudiante(
            String nombre,
            String rut,
            String correo,
            String curso,
            String contrasena
    ) {

        if (!Validador.validarNombre(nombre)
                || !Validador.validarRut(rut)
                || !Validador.validarCorreo(correo)
                || !Validador.validarCurso(curso)
                || !Validador.validarContrasena(contrasena)) {

            return false;
        }

        Connection conn = null;

        try {

            conn = DatabaseConnection
                    .getInstance()
                    .getConnection();

            conn.setAutoCommit(false);

            Estudiante estudiante =
                    new Estudiante(
                            nombre.trim(),
                            rut.trim(),
                            correo.trim(),
                            curso.trim()
                    );

            Usuario usuario =
                    new Usuario(
                            nombre.trim(),
                            rut.trim(),
                            correo.trim(),
                            contrasena,
                            "estudiante"
                    );

            boolean estudianteRegistrado =
                    estudianteDAO.create(
                            conn,
                            estudiante
                    );

            boolean usuarioRegistrado =
                    usuarioDAO.create(
                            conn,
                            usuario
                    );

            if (estudianteRegistrado
                    && usuarioRegistrado) {

                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Obtiene todos los estudiantes registrados.
     */
    public List<Estudiante> listarEstudiantes() {

        return estudianteDAO.readAll();
    }

    /**
     * Actualiza los datos de un estudiante
     * y de su usuario asociado.
     */
    public boolean actualizarEstudiante(
            int id,
            String nombre,
            String rut,
            String correo,
            String curso
    ) {

        if (id <= 0
                || !Validador.validarNombre(nombre)
                || !Validador.validarRut(rut)
                || !Validador.validarCorreo(correo)
                || !Validador.validarCurso(curso)) {

            return false;
        }

        Estudiante estudianteOriginal =
                buscarEstudiantePorId(id);

        if (estudianteOriginal == null) {
            return false;
        }

        Usuario usuario =
                usuarioDAO.buscarPorCorreo(
                        estudianteOriginal.getCorreo()
                );

        Connection conn = null;

        try {

            conn = DatabaseConnection
                    .getInstance()
                    .getConnection();

            conn.setAutoCommit(false);

            Estudiante estudianteActualizado =
                    new Estudiante(
                            id,
                            nombre.trim(),
                            rut.trim(),
                            correo.trim(),
                            curso.trim()
                    );

            boolean estudianteCorrecto =
                    estudianteDAO.update(
                            estudianteActualizado
                    );

            boolean usuarioCorrecto = true;

            /*
             * Algunos estudiantes del script oficial
             * podrían no tener un usuario asociado.
             */
            if (usuario != null) {

                usuario.setNombre(
                        nombre.trim()
                );

                usuario.setRut(
                        rut.trim()
                );

                usuario.setCorreo(
                        correo.trim()
                );

                usuarioCorrecto =
                        usuarioDAO.update(
                                usuario
                        );
            }

            if (estudianteCorrecto
                    && usuarioCorrecto) {

                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Elimina un estudiante y su cuenta
     * de usuario asociada.
     */
    public boolean eliminarEstudiante(
            int id
    ) {

        if (id <= 0) {
            return false;
        }

        Estudiante estudiante =
                buscarEstudiantePorId(id);

        if (estudiante == null) {
            return false;
        }

        Usuario usuario =
                usuarioDAO.buscarPorCorreo(
                        estudiante.getCorreo()
                );

        Connection conn = null;

        try {

            conn = DatabaseConnection
                    .getInstance()
                    .getConnection();

            conn.setAutoCommit(false);

            boolean estudianteEliminado =
                    estudianteDAO.delete(id);

            if (!estudianteEliminado) {

                conn.rollback();
                return false;
            }

            boolean usuarioEliminado = true;

            if (usuario != null) {

                usuarioEliminado =
                        usuarioDAO.delete(
                                usuario.getId()
                        );
            }

            if (usuarioEliminado) {

                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Busca un estudiante según su ID.
     */
    private Estudiante buscarEstudiantePorId(
            int id
    ) {

        for (Estudiante estudiante
                : estudianteDAO.readAll()) {

            if (estudiante.getId() == id) {
                return estudiante;
            }
        }

        return null;
    }
}