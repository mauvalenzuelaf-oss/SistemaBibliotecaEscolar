package dao;

import modelo.Usuario;

import java.util.List;

/**
 * Define las operaciones de acceso
 * a datos para los usuarios.
 */
public interface UsuarioDAO {

    boolean create(Usuario usuario);

    List<Usuario> readAll();

    boolean update(Usuario usuario);

    boolean delete(int id);

    Usuario buscarPorCorreo(String correo);
}
