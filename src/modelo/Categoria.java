package modelo;

/**
 * Representa una categoría de libros.
 */
public class Categoria {

    // Atributos
    private int id;
    private String nombre;

    /**
     * Constructor para registrar una categoría nueva.
     */
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para recuperar una categoría
     * desde la base de datos.
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}