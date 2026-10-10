package dao;

import java.util.List;

/**
 * Define las consultas utilizadas
 * para generar reportes del sistema.
 */
public interface ReporteDAO {

    List<Object[]> librosMasPrestados();

    List<Object[]> historialPorEstudiante(
            int idEstudiante
    );

    List<Object[]> librosActualmentePrestados();
}
