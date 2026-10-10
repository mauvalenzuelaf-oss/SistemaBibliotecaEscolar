package vista;

import controlador.ControladorEstudiantes;
import controlador.ControladorReportes;
import modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para visualizar los reportes
 * del Sistema de Biblioteca Escolar.
 */
public class VentanaReportes extends JFrame {

    private final ControladorReportes controladorReportes;
    private final ControladorEstudiantes controladorEstudiantes;

    private JComboBox<Estudiante> cmbEstudiante;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private JButton btnMasPrestados;
    private JButton btnHistorial;
    private JButton btnActualmentePrestados;

    /**
     * Inicializa la ventana de reportes.
     */
    public VentanaReportes() {

        controladorReportes =
                new ControladorReportes();

        controladorEstudiantes =
                new ControladorEstudiantes();

        configurarVentana();
        crearComponentes();
        cargarEstudiantes();
    }

    /**
     * Configura las características
     * principales de la ventana.
     */
    private void configurarVentana() {

        setTitle(
                "Biblioteca Escolar - Reportes"
        );

        setSize(900, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }

    /**
     * Crea y organiza los componentes.
     */
    private void crearComponentes() {

        setLayout(new BorderLayout());

        JPanel panelSuperior =
                new JPanel();

        cmbEstudiante =
                new JComboBox<>();

        btnMasPrestados =
                new JButton(
                        "Libros más prestados"
                );

        btnHistorial =
                new JButton(
                        "Historial por estudiante"
                );

        btnActualmentePrestados =
                new JButton(
                        "Libros actualmente en préstamo"
                );

        panelSuperior.add(
                new JLabel("Estudiante:")
        );

        panelSuperior.add(cmbEstudiante);
        panelSuperior.add(btnHistorial);
        panelSuperior.add(btnMasPrestados);
        panelSuperior.add(btnActualmentePrestados);

        add(
                panelSuperior,
                BorderLayout.NORTH
        );

        modeloTabla =
                new DefaultTableModel();

        tablaReportes =
                new JTable(modeloTabla);

        add(
                new JScrollPane(tablaReportes),
                BorderLayout.CENTER
        );

        btnMasPrestados.addActionListener(
                e -> mostrarLibrosMasPrestados()
        );

        btnHistorial.addActionListener(
                e -> mostrarHistorial()
        );

        btnActualmentePrestados
                .addActionListener(
                        e -> mostrarActualmentePrestados()
                );
    }

    /**
     * Carga los estudiantes disponibles.
     */
    private void cargarEstudiantes() {

        cmbEstudiante.removeAllItems();

        List<Estudiante> estudiantes =
                controladorEstudiantes
                        .listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {
            cmbEstudiante.addItem(estudiante);
        }
    }

    /**
     * Muestra el reporte de libros
     * más prestados.
     */
    private void mostrarLibrosMasPrestados() {

        configurarColumnas(
                new String[]{
                        "ID Libro",
                        "Título",
                        "Total Préstamos"
                }
        );

        List<Object[]> resultados =
                controladorReportes
                        .librosMasPrestados();

        cargarResultados(resultados);
    }

    /**
     * Muestra el historial del
     * estudiante seleccionado.
     */
    private void mostrarHistorial() {

        Estudiante estudiante =
                (Estudiante)
                        cmbEstudiante
                                .getSelectedItem();

        if (estudiante == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        configurarColumnas(
                new String[]{
                        "ID Préstamo",
                        "Libro",
                        "Fecha Préstamo",
                        "Fecha Vencimiento",
                        "Estado"
                }
        );

        List<Object[]> resultados =
                controladorReportes
                        .historialPorEstudiante(
                                estudiante.getId()
                        );

        cargarResultados(resultados);
    }

    /**
     * Muestra los libros que actualmente
     * se encuentran prestados.
     */
    private void mostrarActualmentePrestados() {

        configurarColumnas(
                new String[]{
                        "ID Préstamo",
                        "Estudiante",
                        "Libro",
                        "Fecha Préstamo",
                        "Fecha Vencimiento"
                }
        );

        List<Object[]> resultados =
                controladorReportes
                        .librosActualmentePrestados();

        cargarResultados(resultados);
    }

    /**
     * Configura las columnas de la tabla.
     */
    private void configurarColumnas(
            String[] columnas
    ) {

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        tablaReportes.setModel(
                modeloTabla
        );
    }

    /**
     * Carga los resultados obtenidos
     * en la tabla.
     */
    private void cargarResultados(
            List<Object[]> resultados
    ) {

        modeloTabla.setRowCount(0);

        for (Object[] fila : resultados) {
            modeloTabla.addRow(fila);
        }
    }
}