package controlador;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import modelo.Autenticable;
import modelo.Usuario;

import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los usuarios y la autenticación.
 */
public class ControladorUsuarios {

    private final UsuarioDAO usuarioDAO;

    /**
     * Inicializa el DAO utilizado
     * por el controlador.
     */
    public ControladorUsuarios() {

        usuarioDAO =
                new UsuarioDAOImpl();
    }

    /**
     * Registra un usuario nuevo.
     */
    public boolean registrarUsuario(
            String nombre,
            String rut,
            String correo,
            String contrasena,
            String rol
    ) {

        if (!datosValidos(
                nombre,
                rut,
                correo,
                contrasena,
                rol
        )) {

            return false;
        }

        Usuario usuario =
                new Usuario(
                        nombre.trim(),
                        rut.trim(),
                        correo.trim(),
                        contrasena,
                        rol
                );

        return usuarioDAO.create(usuario);
    }

    /**
     * Obtiene los usuarios registrados.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.readAll();
    }

    /**
     * Actualiza los datos de un usuario.
     */
    public boolean actualizarUsuario(
            int id,
            String nombre,
            String rut,
            String correo,
            String contrasena,
            String rol
    ) {

        if (id <= 0
                || !datosValidos(
                nombre,
                rut,
                correo,
                contrasena,
                rol
        )) {

            return false;
        }

        Usuario usuario =
                new Usuario(
                        id,
                        nombre.trim(),
                        rut.trim(),
                        correo.trim(),
                        contrasena,
                        rol
                );

        return usuarioDAO.update(usuario);
    }

    /**
     * Elimina un usuario según su ID.
     */
    public boolean eliminarUsuario(
            int id
    ) {

        if (id <= 0) {
            return false;
        }

        return usuarioDAO.delete(id);
    }

    /**
     * Autentica un usuario utilizando
     * el comportamiento definido por Autenticable.
     */
    public Usuario autenticar(
            String correo,
            String contrasena
    ) {

        if (correo == null
                || correo.isBlank()
                || contrasena == null
                || contrasena.isBlank()) {

            return null;
        }

        Usuario usuario =
                usuarioDAO.buscarPorCorreo(
                        correo.trim()
                );

        if (usuario == null) {
            return null;
        }

        Autenticable autenticable =
                usuario;

        if (autenticable
                .verificarCredenciales(
                        correo.trim(),
                        contrasena
                )) {

            return usuario;
        }

        return null;
    }

    /**
     * Valida los datos básicos
     * de un usuario.
     */
    private boolean datosValidos(
            String nombre,
            String rut,
            String correo,
            String contrasena,
            String rol
    ) {

        if (nombre == null
                || nombre.isBlank()
                || rut == null
                || rut.isBlank()
                || correo == null
                || correo.isBlank()
                || contrasena == null
                || contrasena.isBlank()
                || rol == null
                || rol.isBlank()) {

            return false;
        }

        return rol.equals("bibliotecario")
                || rol.equals("estudiante");
    }
}
