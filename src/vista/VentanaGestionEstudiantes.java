package vista;

import controlador.ControladorEstudiantes;
import modelo.Estudiante;
import util.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los estudiantes
 * registrados en la biblioteca.
 */
public class VentanaGestionEstudiantes extends JFrame {

    private final ControladorEstudiantes controladorEstudiantes;

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCorreo;
    private JTextField txtCurso;
    private JPasswordField txtContrasena;

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnActualizar;

    /**
     * Inicializa la ventana de gestión
     * de estudiantes.
     */
    public VentanaGestionEstudiantes() {

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
                "Biblioteca Escolar - Gestión de Estudiantes"
        );

        setSize(
                850,
                600
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }

    /**
     * Crea y organiza los componentes.
     */
    private void crearComponentes() {

        setLayout(
                new BorderLayout()
        );

        // Formulario
        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(
                                5,
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

        txtNombre =
                new JTextField();

        txtRut =
                new JTextField();

        txtCorreo =
                new JTextField();

        txtCurso =
                new JTextField();

        txtContrasena =
                new JPasswordField();

        panelFormulario.add(
                new JLabel("Nombre:")
        );

        panelFormulario.add(
                txtNombre
        );

        panelFormulario.add(
                new JLabel("RUT:")
        );

        panelFormulario.add(
                txtRut
        );

        panelFormulario.add(
                new JLabel("Correo:")
        );

        panelFormulario.add(
                txtCorreo
        );

        panelFormulario.add(
                new JLabel("Curso:")
        );

        panelFormulario.add(
                txtCurso
        );

        panelFormulario.add(
                new JLabel(
                        "Contraseña (solo al registrar):"
                )
        );

        panelFormulario.add(
                txtContrasena
        );

        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla
        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Nombre",
                                "RUT",
                                "Correo",
                                "Curso"
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

        tablaEstudiantes =
                new JTable(
                        modeloTabla
                );

        tablaEstudiantes.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        add(
                new JScrollPane(
                        tablaEstudiantes
                ),
                BorderLayout.CENTER
        );

        // Botones
        JPanel panelBotones =
                new JPanel();

        btnRegistrar =
                new JButton(
                        "Registrar"
                );

        btnEditar =
                new JButton(
                        "Editar"
                );

        btnEliminar =
                new JButton(
                        "Eliminar"
                );

        btnLimpiar =
                new JButton(
                        "Limpiar"
                );

        btnActualizar =
                new JButton(
                        "Actualizar"
                );

        panelBotones.add(
                btnRegistrar
        );

        panelBotones.add(
                btnEditar
        );

        panelBotones.add(
                btnEliminar
        );

        panelBotones.add(
                btnLimpiar
        );

        panelBotones.add(
                btnActualizar
        );

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // Eventos
        btnRegistrar.addActionListener(
                e -> registrarEstudiante()
        );

        btnEditar.addActionListener(
                e -> editarEstudiante()
        );

        btnEliminar.addActionListener(
                e -> eliminarEstudiante()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        btnActualizar.addActionListener(
                e -> cargarEstudiantes()
        );

        tablaEstudiantes
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                cargarEstudianteSeleccionado();
                            }
                        }
                );
    }

    /**
     * Carga los estudiantes registrados
     * desde la base de datos.
     */
    private void cargarEstudiantes() {

        modeloTabla.setRowCount(0);

        List<Estudiante> estudiantes =
                controladorEstudiantes
                        .listarEstudiantes();

        for (Estudiante estudiante
                : estudiantes) {

            modeloTabla.addRow(
                    new Object[]{
                            estudiante.getId(),
                            estudiante.getNombre(),
                            estudiante.getRut(),
                            estudiante.getCorreo(),
                            estudiante.getCurso()
                    }
            );
        }
    }

    /**
     * Registra un estudiante y crea
     * su cuenta de acceso al sistema.
     */
    private void registrarEstudiante() {

        if (!validarFormulario(true)) {
            return;
        }

        String contrasena =
                new String(
                        txtContrasena
                                .getPassword()
                );

        boolean resultado =
                controladorEstudiantes
                        .registrarEstudiante(
                                txtNombre.getText(),
                                txtRut.getText(),
                                txtCorreo.getText(),
                                txtCurso.getText(),
                                contrasena
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante registrado correctamente.\n"
                            + "También se creó su cuenta de usuario.",
                    "Registro",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el estudiante.\n"
                            + "El RUT podría estar registrado anteriormente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el estudiante seleccionado.
     */
    private void editarEstudiante() {

        int fila =
                tablaEstudiantes
                        .getSelectedRow();

        if (fila == -1) {

            mostrarValidacion(
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        /*
         * Al editar no se solicita contraseña.
         */
        if (!validarFormulario(false)) {
            return;
        }

        int id =
                (int)
                        modeloTabla.getValueAt(
                                fila,
                                0
                        );

        boolean resultado =
                controladorEstudiantes
                        .actualizarEstudiante(
                                id,
                                txtNombre.getText(),
                                txtRut.getText(),
                                txtCorreo.getText(),
                                txtCurso.getText()
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente.",
                    "Actualización",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el estudiante.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el estudiante seleccionado.
     */
    private void eliminarEstudiante() {

        int fila =
                tablaEstudiantes
                        .getSelectedRow();

        if (fila == -1) {

            mostrarValidacion(
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar el estudiante seleccionado?\n"
                                + "También se eliminará su cuenta de usuario.",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta
                != JOptionPane.YES_OPTION) {

            return;
        }

        int id =
                (int)
                        modeloTabla.getValueAt(
                                fila,
                                0
                        );

        boolean resultado =
                controladorEstudiantes
                        .eliminarEstudiante(
                                id
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante eliminado correctamente.",
                    "Eliminación",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el estudiante.\n"
                            + "Puede tener préstamos asociados.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Valida los datos ingresados.
     *
     * @param validarContrasena indica si debe
     *                          validar la contraseña.
     */
    private boolean validarFormulario(
            boolean validarContrasena
    ) {

        if (!Validador.validarNombre(
                txtNombre.getText()
        )) {

            mostrarValidacion(
                    "Nombre inválido.\n"
                            + "Utilice al menos 2 caracteres "
                            + "y solo letras, espacios, puntos, "
                            + "apóstrofes o guiones."
            );

            txtNombre.requestFocus();
            return false;
        }

        if (!Validador.validarRut(
                txtRut.getText()
        )) {

            mostrarValidacion(
                    "RUT inválido.\n"
                            + "Utilice el formato 12345678-9."
            );

            txtRut.requestFocus();
            return false;
        }

        if (!Validador.validarCorreo(
                txtCorreo.getText()
        )) {

            mostrarValidacion(
                    "Correo inválido.\n"
                            + "Ejemplo: nombre@correo.cl"
            );

            txtCorreo.requestFocus();
            return false;
        }

        if (!Validador.validarCurso(
                txtCurso.getText()
        )) {

            mostrarValidacion(
                    "Curso inválido.\n"
                            + "Ejemplo: 3ro Medio A."
            );

            txtCurso.requestFocus();
            return false;
        }

        if (validarContrasena) {

            String contrasena =
                    new String(
                            txtContrasena
                                    .getPassword()
                    );

            if (!Validador.validarContrasena(
                    contrasena
            )) {

                mostrarValidacion(
                        "Contraseña inválida.\n"
                                + "Debe tener entre 6 y 50 caracteres, "
                                + "al menos una letra y un número."
                );

                txtContrasena.requestFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * Carga los datos del estudiante
     * seleccionado en el formulario.
     */
    private void cargarEstudianteSeleccionado() {

        int fila =
                tablaEstudiantes
                        .getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtNombre.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                1
                        )
                        .toString()
        );

        txtRut.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                2
                        )
                        .toString()
        );

        txtCorreo.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                3
                        )
                        .toString()
        );

        txtCurso.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                4
                        )
                        .toString()
        );

        /*
         * La contraseña no se muestra
         * ni se modifica al editar.
         */
        txtContrasena.setText("");
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCorreo.setText("");
        txtCurso.setText("");
        txtContrasena.setText("");

        tablaEstudiantes
                .clearSelection();

        txtNombre.requestFocus();
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
