package controlador;

import dao.impl.LibroDAOImpl;
import dao.impl.PrestamoDAOImpl;
import modelo.Libro;
import modelo.Prestamo;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los préstamos y devoluciones de libros.
 */
public class ControladorPrestamos {

    private final PrestamoDAOImpl prestamoDAO;
    private final LibroDAOImpl libroDAO;

    /*
     * Bloqueo compartido por todas las instancias
     * del controlador para proteger las operaciones
     * que modifican el stock.
     */
    private static final Object BLOQUEO_PRESTAMOS =
            new Object();

    /**
     * Inicializa los objetos DAO utilizados
     * por el controlador.
     */
    public ControladorPrestamos() {

        prestamoDAO =
                new PrestamoDAOImpl();

        libroDAO =
                new LibroDAOImpl();
    }

    /**
     * Registra un préstamo y actualiza el stock
     * dentro de una misma transacción.
     */
    public boolean registrarPrestamo(
            int idEstudiante,
            int idLibro
    ) {

        synchronized (BLOQUEO_PRESTAMOS) {

            Connection conn = null;

            try {

                conn = DatabaseConnection
                        .getInstance()
                        .getConnection();

                conn.setAutoCommit(false);

                System.out.println(
                        "["
                                + Thread.currentThread().getName()
                                + "] Iniciando préstamo..."
                );

                Libro libro =
                        libroDAO.readById(
                                conn,
                                idLibro
                        );

                if (libro == null) {

                    conn.rollback();

                    System.out.println(
                            "["
                                    + Thread.currentThread().getName()
                                    + "] Libro no encontrado."
                    );

                    return false;
                }

                if (libro.getStock() <= 0) {

                    conn.rollback();

                    System.out.println(
                            "["
                                    + Thread.currentThread().getName()
                                    + "] Sin stock disponible."
                    );

                    return false;
                }

                LocalDate fechaActual =
                        LocalDate.now();

                LocalDate fechaVencimiento =
                        fechaActual.plusDays(7);

                Prestamo prestamo =
                        new Prestamo(
                                idEstudiante,
                                idLibro,
                                Date.valueOf(fechaActual),
                                Date.valueOf(fechaVencimiento),
                                false
                        );

                boolean prestamoRegistrado =
                        prestamoDAO.create(
                                conn,
                                prestamo
                        );

                boolean stockActualizado =
                        libroDAO.updateStock(
                                conn,
                                idLibro,
                                libro.getStock() - 1
                        );

                if (prestamoRegistrado
                        && stockActualizado) {

                    conn.commit();

                    System.out.println(
                            "["
                                    + Thread.currentThread().getName()
                                    + "] Préstamo registrado. "
                                    + "Stock restante: "
                                    + (libro.getStock() - 1)
                    );

                    return true;
                }

                conn.rollback();

                System.out.println(
                        "["
                                + Thread.currentThread().getName()
                                + "] Operación cancelada. "
                                + "Se ejecutó rollback."
                );

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
    }

    /**
     * Registra la devolución de un préstamo
     * y devuelve una unidad al stock del libro.
     */
    public boolean registrarDevolucion(
            int idPrestamo
    ) {

        synchronized (BLOQUEO_PRESTAMOS) {

            Connection conn = null;

            try {

                conn = DatabaseConnection
                        .getInstance()
                        .getConnection();

                conn.setAutoCommit(false);

                System.out.println(
                        "["
                                + Thread.currentThread().getName()
                                + "] Iniciando devolución..."
                );

                Prestamo prestamo =
                        prestamoDAO.readById(
                                conn,
                                idPrestamo
                        );

                if (prestamo == null) {

                    conn.rollback();

                    System.out.println(
                            "Préstamo no encontrado."
                    );

                    return false;
                }

                if (prestamo.isDevuelto()) {

                    conn.rollback();

                    System.out.println(
                            "El préstamo ya fue devuelto."
                    );

                    return false;
                }

                Libro libro =
                        libroDAO.readById(
                                conn,
                                prestamo.getIdLibro()
                        );

                if (libro == null) {

                    conn.rollback();

                    System.out.println(
                            "Libro no encontrado."
                    );

                    return false;
                }

                LocalDate fechaActual =
                        LocalDate.now();

                LocalDate fechaLimite =
                        prestamo
                                .getFechaDevolucion()
                                .toLocalDate();

                boolean atrasado =
                        fechaActual.isAfter(
                                fechaLimite
                        );

                prestamo.setDevuelto(true);

                boolean prestamoActualizado =
                        prestamoDAO.update(
                                conn,
                                prestamo
                        );

                boolean stockActualizado =
                        libroDAO.updateStock(
                                conn,
                                libro.getId(),
                                libro.getStock() + 1
                        );

                if (prestamoActualizado
                        && stockActualizado) {

                    conn.commit();

                    System.out.println(
                            "Devolución registrada correctamente."
                    );

                    System.out.println(
                            "Stock actualizado: "
                                    + (libro.getStock() + 1)
                    );

                    if (atrasado) {

                        System.out.println(
                                "La devolución fue realizada con atraso."
                        );

                    } else {

                        System.out.println(
                                "La devolución fue realizada dentro del plazo."
                        );
                    }

                    return true;
                }

                conn.rollback();

                System.out.println(
                        "Operación cancelada. "
                                + "Se ejecutó rollback."
                );

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
    }

    /**
     * Obtiene todos los préstamos registrados.
     */
    public List<Prestamo> listarPrestamos() {
        return prestamoDAO.readAll();
    }

    /**
     * Obtiene todos los libros registrados.
     */
    public List<Libro> listarLibros() {
        return libroDAO.readAll();
    }
}