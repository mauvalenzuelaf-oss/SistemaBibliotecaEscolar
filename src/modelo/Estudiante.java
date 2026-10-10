package modelo;

/**
 * Representa a un estudiante registrado
 * en el Sistema de Biblioteca Escolar.
 */
public class Estudiante extends Persona {

    // Atributos
    private String curso;

    /**
     * Constructor para crear un estudiante nuevo.
     */
    public Estudiante(
            String nombre,
            String rut,
            String correo,
            String curso
    ) {

        super(
                nombre,
                rut,
                correo
        );

        this.curso = curso;
    }

    /**
     * Constructor utilizado para recuperar
     * un estudiante desde la base de datos.
     */
    public Estudiante(
            int id,
            String nombre,
            String rut,
            String correo,
            String curso
    ) {

        super(
                id,
                nombre,
                rut,
                correo
        );

        this.curso = curso;
    }

    /**
     * Obtiene el tipo de persona.
     */
    @Override
    public String getTipoPersona() {
        return "Estudiante";
    }

    /**
     * Representación textual del estudiante.
     */
    @Override
    public String toString() {

        return getDescripcion()
                + " - "
                + curso;
    }

    // Getters y Setters

    public String getCurso() {
        return curso;
    }

    public void setCurso(
            String curso
    ) {
        this.curso = curso;
    }
}