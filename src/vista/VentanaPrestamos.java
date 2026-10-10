package vista;

import controlador.ControladorEstudiantes;
import controlador.ControladorPrestamos;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana para registrar préstamos
 * y devoluciones de libros.
 */
public class VentanaPrestamos extends JFrame {

    private final Usuario usuario;
    private final ControladorPrestamos controladorPrestamos;
    private final ControladorEstudiantes controladorEstudiantes;

    private JComboBox<Estudiante> cmbEstudiante;
    private JComboBox<Libro> cmbLibro;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private JButton btnPrestar;
    private JButton btnDevolver;
    private JButton btnActualizar;

    /**
     * Inicializa la ventana de préstamos.
     */
    public VentanaPrestamos(Usuario usuario) {

        this.usuario = usuario;

        controladorPrestamos =
                new ControladorPrestamos();

        controladorEstudiantes =
                new ControladorEstudiantes();

        configurarVentana();
        crearComponentes();
        cargarEstudiantes();
        cargarLibros();
        cargarPrestamos();
        aplicarPermisos();
    }

    /**
     * Configura la ventana.
     */
    private void configurarVentana() {

        setTitle(
                "Biblioteca Escolar - Préstamos y Devoluciones"
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

        // Panel superior
        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        cmbEstudiante =
                new JComboBox<>();

        cmbLibro =
                new JComboBox<>();

        panelFormulario.add(
                new JLabel("Estudiante:")
        );

        panelFormulario.add(cmbEstudiante);

        panelFormulario.add(
                new JLabel("Libro:")
        );

        panelFormulario.add(cmbLibro);

        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla
        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Estudiante",
                                "Libro",
                                "Fecha préstamo",
                                "Fecha vencimiento",
                                "Estado"
                        },
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

        tablaPrestamos =
                new JTable(modeloTabla);

        tablaPrestamos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(
                new JScrollPane(tablaPrestamos),
                BorderLayout.CENTER
        );

        // Botones
        JPanel panelBotones =
                new JPanel();

        btnPrestar =
                new JButton("Registrar Préstamo");

        btnDevolver =
                new JButton("Registrar Devolución");

        btnActualizar =
                new JButton("Actualizar");

        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnActualizar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // Eventos
        btnPrestar.addActionListener(
                e -> registrarPrestamo()
        );

        btnDevolver.addActionListener(
                e -> registrarDevolucion()
        );

        btnActualizar.addActionListener(
                e -> actualizarDatos()
        );
    }

    /**
     * Aplica las restricciones según
     * el rol del usuario.
     */
    private void aplicarPermisos() {

        if (usuario.getRol()
                .equals("estudiante")) {

            cmbEstudiante.setEnabled(false);
        }
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

            if (usuario.getRol()
                    .equals("bibliotecario")) {

                cmbEstudiante.addItem(
                        estudiante
                );

            } else if (
                    estudiante.getRut()
                            .equals(usuario.getRut())
            ) {

                cmbEstudiante.addItem(
                        estudiante
                );
            }
        }
    }

    /**
     * Carga los libros desde la base de datos.
     */
    private void cargarLibros() {

        cmbLibro.removeAllItems();

        List<Libro> libros =
                controladorPrestamos
                        .listarLibros();

        for (Libro libro : libros) {
            cmbLibro.addItem(libro);
        }
    }

    /**
     * Carga los préstamos registrados.
     */
    private void cargarPrestamos() {

        modeloTabla.setRowCount(0);

        List<Prestamo> prestamos =
                controladorPrestamos
                        .listarPrestamos();

        for (Prestamo prestamo : prestamos) {

            Estudiante estudiante =
                    buscarEstudiante(
                            prestamo.getIdEstudiante()
                    );

            // Un estudiante solo ve sus préstamos
            if (usuario.getRol()
                    .equals("estudiante")) {

                if (estudiante == null
                        || !estudiante.getRut()
                        .equals(usuario.getRut())) {

                    continue;
                }
            }

            Libro libro =
                    buscarLibro(
                            prestamo.getIdLibro()
                    );

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getId(),
                            estudiante != null
                                    ? estudiante.getNombre()
                                    : prestamo.getIdEstudiante(),
                            libro != null
                                    ? libro.getTitulo()
                                    : prestamo.getIdLibro(),
                            prestamo.getFechaPrestamo(),
                            prestamo.getFechaDevolucion(),
                            obtenerEstado(prestamo)
                    }
            );
        }
    }

    /**
     * Registra un préstamo utilizando
     * un hilo independiente.
     */
    private void registrarPrestamo() {

        Estudiante estudiante =
                (Estudiante)
                        cmbEstudiante.getSelectedItem();

        Libro libro =
                (Libro)
                        cmbLibro.getSelectedItem();

        if (estudiante == null) {

            mostrarValidacion(
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        if (libro == null) {

            mostrarValidacion(
                    "Debe seleccionar un libro."
            );

            return;
        }

        btnPrestar.setEnabled(false);

        Thread hiloPrestamo =
                new Thread(
                        () -> {

                            boolean resultado =
                                    controladorPrestamos
                                            .registrarPrestamo(
                                                    estudiante.getId(),
                                                    libro.getId()
                                            );

                            SwingUtilities.invokeLater(
                                    () -> {

                                        btnPrestar.setEnabled(true);

                                        if (resultado) {

                                            JOptionPane.showMessageDialog(
                                                    this,
                                                    "Préstamo registrado correctamente."
                                            );

                                        } else {

                                            JOptionPane.showMessageDialog(
                                                    this,
                                                    "No se pudo registrar el préstamo. "
                                                            + "Verifique el stock disponible.",
                                                    "Préstamo",
                                                    JOptionPane.WARNING_MESSAGE
                                            );
                                        }

                                        actualizarDatos();
                                    }
                            );
                        },
                        "Hilo-Prestamo-GUI"
                );

        hiloPrestamo.start();
    }

    /**
     * Registra la devolución del
     * préstamo seleccionado.
     */
    private void registrarDevolucion() {

        int fila =
                tablaPrestamos.getSelectedRow();

        if (fila == -1) {

            mostrarValidacion(
                    "Debe seleccionar un préstamo."
            );

            return;
        }

        int idPrestamo =
                (int)
                        modeloTabla.getValueAt(
                                fila,
                                0
                        );

        String estado =
                modeloTabla
                        .getValueAt(
                                fila,
                                5
                        )
                        .toString();

        if (estado.equals("DEVUELTO")) {

            mostrarValidacion(
                    "El préstamo ya fue devuelto."
            );

            return;
        }

        btnDevolver.setEnabled(false);

        Thread hiloDevolucion =
                new Thread(
                        () -> {

                            boolean resultado =
                                    controladorPrestamos
                                            .registrarDevolucion(
                                                    idPrestamo
                                            );

                            SwingUtilities.invokeLater(
                                    () -> {

                                        btnDevolver.setEnabled(true);

                                        if (resultado) {

                                            JOptionPane.showMessageDialog(
                                                    this,
                                                    "Devolución registrada correctamente."
                                            );

                                        } else {

                                            JOptionPane.showMessageDialog(
                                                    this,
                                                    "No se pudo registrar la devolución.",
                                                    "Devolución",
                                                    JOptionPane.WARNING_MESSAGE
                                            );
                                        }

                                        actualizarDatos();
                                    }
                            );
                        },
                        "Hilo-Devolucion-GUI"
                );

        hiloDevolucion.start();
    }

    /**
     * Actualiza libros, estudiantes
     * y préstamos.
     */
    private void actualizarDatos() {

        cargarEstudiantes();
        cargarLibros();
        cargarPrestamos();
    }

    /**
     * Busca un estudiante según su ID.
     */
    private Estudiante buscarEstudiante(
            int idEstudiante
    ) {

        List<Estudiante> estudiantes =
                controladorEstudiantes
                        .listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante.getId()
                    == idEstudiante) {

                return estudiante;
            }
        }

        return null;
    }

    /**
     * Busca un libro según su ID.
     */
    private Libro buscarLibro(
            int idLibro
    ) {

        List<Libro> libros =
                controladorPrestamos
                        .listarLibros();

        for (Libro libro : libros) {

            if (libro.getId()
                    == idLibro) {

                return libro;
            }
        }

        return null;
    }

    /**
     * Determina el estado visual
     * de un préstamo.
     */
    private String obtenerEstado(
            Prestamo prestamo
    ) {

        if (prestamo.isDevuelto()) {
            return "DEVUELTO";
        }

        LocalDate fechaActual =
                LocalDate.now();

        LocalDate fechaLimite =
                prestamo
                        .getFechaDevolucion()
                        .toLocalDate();

        if (fechaActual.isAfter(fechaLimite)) {
            return "ATRASADO";
        }

        return "EN PRÉSTAMO";
    }

    /**
     * Muestra un mensaje de validación.
     */
    private void mostrarValidacion(
            String mensaje
    ) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Validación",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
