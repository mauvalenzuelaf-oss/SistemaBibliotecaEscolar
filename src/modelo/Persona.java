package modelo;

/**
 * Representa los datos generales
 * compartidos por las personas del sistema.
 */
public abstract class Persona {

    // Atributos
    private int id;
    private String nombre;
    private String rut;
    private String correo;

    /**
     * Constructor para crear una persona nueva.
     */
    public Persona(
            String nombre,
            String rut,
            String correo
    ) {

        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
    }

    /**
     * Constructor utilizado para recuperar
     * una persona desde la base de datos.
     */
    public Persona(
            int id,
            String nombre,
            String rut,
            String correo
    ) {

        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
    }

    /**
     * Obtiene el tipo específico de persona.
     */
    public abstract String getTipoPersona();

    /**
     * Obtiene una descripción general de la persona.
     * El tipo depende de la implementación
     * realizada por cada subclase.
     */
    public String getDescripcion() {

        return getTipoPersona()
                + ": "
                + getNombre()
                + " - "
                + getRut();
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(
            int id
    ) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(
            String nombre
    ) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(
            String rut
    ) {
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(
            String correo
    ) {
        this.correo = correo;
    }
}
