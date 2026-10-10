package util;

/**
 * Contiene validaciones generales utilizadas
 * por el Sistema de Biblioteca Escolar.
 */
public class Validador {

    /**
     * Valida un nombre de persona.
     */
    public static boolean validarNombre(
            String nombre
    ) {

        if (nombre == null) {
            return false;
        }

        nombre = nombre.trim();

        return nombre.length() >= 2
                && nombre.length() <= 100
                && nombre.matches(
                "[\\p{L} .'-]+"
        );
    }

    /**
     * Valida el formato básico de un RUT.
     * Ejemplo: 12345678-9
     */
    public static boolean validarRut(
            String rut
    ) {

        if (rut == null) {
            return false;
        }

        return rut.trim().matches(
                "\\d{7,8}-[\\dkK]"
        );
    }

    /**
     * Valida el formato básico de un correo.
     */
    public static boolean validarCorreo(
            String correo
    ) {

        if (correo == null) {
            return false;
        }

        correo = correo.trim();

        return correo.length() <= 100
                && correo.matches(
                "^[A-Za-z0-9._%+-]+"
                        + "@[A-Za-z0-9.-]+"
                        + "\\.[A-Za-z]{2,}$"
        );
    }

    /**
     * Valida el formato de un curso.
     * Ejemplo: 3ro Medio A
     */
    public static boolean validarCurso(
            String curso
    ) {

        if (curso == null) {
            return false;
        }

        return curso.trim().matches(
                "^(1ro|2do|3ro|4to) Medio [A-Z]$"
        );
    }

    /**
     * Valida una contraseña.
     * Debe contener al menos seis caracteres,
     * una letra y un número.
     */
    public static boolean validarContrasena(
            String contrasena
    ) {

        if (contrasena == null) {
            return false;
        }

        return contrasena.length() >= 6
                && contrasena.length() <= 50
                && contrasena.matches(
                ".*[A-Za-z].*"
        )
                && contrasena.matches(
                ".*\\d.*"
        );
    }

    /**
     * Valida el título de un libro.
     */
    public static boolean validarTitulo(
            String titulo
    ) {

        if (titulo == null) {
            return false;
        }

        titulo = titulo.trim();

        return titulo.length() >= 1
                && titulo.length() <= 200;
    }

    /**
     * Valida el nombre de un autor.
     */
    public static boolean validarAutor(
            String autor
    ) {

        if (autor == null) {
            return false;
        }

        autor = autor.trim();

        return autor.length() >= 2
                && autor.length() <= 100
                && autor.matches(
                "[\\p{L} .'-]+"
        );
    }

    /**
     * Valida un ISBN de 10 o 13 dígitos.
     */
    public static boolean validarIsbn(
            String isbn
    ) {

        if (isbn == null) {
            return false;
        }

        String isbnLimpio =
                isbn.replace("-", "")
                        .replace(" ", "")
                        .trim();

        return isbnLimpio.matches(
                "\\d{9}[\\dXx]|\\d{13}"
        );
    }

    /**
     * Valida el nombre de una editorial.
     */
    public static boolean validarEditorial(
            String editorial
    ) {

        if (editorial == null) {
            return false;
        }

        editorial = editorial.trim();

        return editorial.length() >= 2
                && editorial.length() <= 100;
    }

    /**
     * Valida que el stock no sea negativo.
     */
    public static boolean validarStock(
            int stock
    ) {

        return stock >= 0;
    }
}
