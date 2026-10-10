package modelo;

/**
 * Representa a un usuario del sistema.
 * Hereda los datos generales de Persona
 * e implementa el comportamiento de autenticación.
 */
public class Usuario extends Persona implements Autenticable {

    // Atributos
    private String contrasena;
    private String rol;

    /**
     * Constructor para crear un usuario nuevo.
     */
    public Usuario(
            String nombre,
            String rut,
            String correo,
            String contrasena,
            String rol
    ) {

        super(
                nombre,
                rut,
                correo
        );

        this.contrasena = contrasena;
        this.rol = rol;
    }

    /**
     * Constructor utilizado para recuperar
     * un usuario desde la base de datos.
     */
    public Usuario(
            int id,
            String nombre,
            String rut,
            String correo,
            String contrasena,
            String rol
    ) {

        super(
                id,
                nombre,
                rut,
                correo
        );

        this.contrasena = contrasena;
        this.rol = rol;
    }

    /**
     * Verifica si el correo y la contraseña
     * corresponden al usuario.
     */
    @Override
    public boolean verificarCredenciales(
            String correo,
            String contrasena
    ) {

        if (correo == null
                || contrasena == null) {

            return false;
        }

        return correo.equals(getCorreo())
                && contrasena.equals(this.contrasena);
    }

    /**
     * Obtiene el tipo de persona.
     */
    @Override
    public String getTipoPersona() {
        return "Usuario";
    }

    /**
     * Representación textual del usuario.
     */
    @Override
    public String toString() {

        return getDescripcion()
                + " - "
                + rol;
    }

    // Getters y Setters

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(
            String contrasena
    ) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(
            String rol
    ) {
        this.rol = rol;
    }
}