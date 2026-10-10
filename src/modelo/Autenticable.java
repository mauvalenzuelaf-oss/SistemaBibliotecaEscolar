package modelo;

/**
 * Define el comportamiento que debe tener
 * una clase capaz de autenticarse en el sistema.
 */
public interface Autenticable {

    /**
     * Verifica las credenciales ingresadas.
     */
    boolean verificarCredenciales(
            String correo,
            String contrasena
    );
}