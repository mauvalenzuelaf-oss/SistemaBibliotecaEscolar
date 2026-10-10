package controlador;

import dao.ReporteDAO;
import dao.impl.ReporteDAOImpl;

import java.util.List;

/**
 * Coordina la generación de reportes
 * del Sistema de Biblioteca Escolar.
 */
public class ControladorReportes {

    private final ReporteDAO reporteDAO;

    public ControladorReportes() {
        reporteDAO = new ReporteDAOImpl();
    }

    public List<Object[]> librosMasPrestados() {
        return reporteDAO.librosMasPrestados();
    }

    public List<Object[]> historialPorEstudiante(
            int idEstudiante
    ) {

        if (idEstudiante <= 0) {
            return List.of();
        }

        return reporteDAO.historialPorEstudiante(
                idEstudiante
        );
    }

    public List<Object[]> librosActualmentePrestados() {
        return reporteDAO.librosActualmentePrestados();
    }
}
