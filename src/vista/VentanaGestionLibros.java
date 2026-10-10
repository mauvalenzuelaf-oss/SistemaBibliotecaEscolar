package vista;

import controlador.ControladorCategorias;
import controlador.ControladorLibros;
import modelo.Categoria;
import modelo.Libro;
import modelo.Usuario;
import util.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar y consultar
 * los libros de la biblioteca.
 */
public class VentanaGestionLibros extends JFrame {

    private final Usuario usuario;
    private final ControladorLibros controladorLibros;
    private final ControladorCategorias controladorCategorias;

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;

    private JComboBox<Categoria> cmbCategoria;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnActualizar;

    /**
     * Inicializa la ventana de libros.
     */
    public VentanaGestionLibros(
            Usuario usuario
    ) {

        this.usuario = usuario;

        controladorLibros =
                new ControladorLibros();

        controladorCategorias =
                new ControladorCategorias();

        configurarVentana();
        crearComponentes();
        cargarCategorias();
        cargarLibros();
        aplicarPermisos();
    }

    /**
     * Configura las características
     * principales de la ventana.
     */
    private void configurarVentana() {

        setTitle(
                "Biblioteca Escolar - Libros"
        );

        setSize(
                900,
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
                                6,
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

        txtTitulo =
                new JTextField();

        txtAutor =
                new JTextField();

        txtIsbn =
                new JTextField();

        txtEditorial =
                new JTextField();

        txtStock =
                new JTextField();

        cmbCategoria =
                new JComboBox<>();

        panelFormulario.add(
                new JLabel("Título:")
        );

        panelFormulario.add(
                txtTitulo
        );

        panelFormulario.add(
                new JLabel("Autor:")
        );

        panelFormulario.add(
                txtAutor
        );

        panelFormulario.add(
                new JLabel("ISBN:")
        );

        panelFormulario.add(
                txtIsbn
        );

        panelFormulario.add(
                new JLabel("Editorial:")
        );

        panelFormulario.add(
                txtEditorial
        );

        panelFormulario.add(
                new JLabel("Stock:")
        );

        panelFormulario.add(
                txtStock
        );

        panelFormulario.add(
                new JLabel("Categoría:")
        );

        panelFormulario.add(
                cmbCategoria
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
                                "Título",
                                "Autor",
                                "ISBN",
                                "Editorial",
                                "Stock",
                                "Categoría"
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

        tablaLibros =
                new JTable(
                        modeloTabla
                );

        tablaLibros.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        add(
                new JScrollPane(
                        tablaLibros
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
                e -> registrarLibro()
        );

        btnEditar.addActionListener(
                e -> editarLibro()
        );

        btnEliminar.addActionListener(
                e -> eliminarLibro()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        btnActualizar.addActionListener(
                e -> actualizarDatos()
        );

        tablaLibros
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                cargarLibroSeleccionado();
                            }
                        }
                );
    }

    /**
     * Aplica los permisos según
     * el rol del usuario.
     */
    private void aplicarPermisos() {

        boolean bibliotecario =
                usuario.getRol()
                        .equals("bibliotecario");

        txtTitulo.setEditable(
                bibliotecario
        );

        txtAutor.setEditable(
                bibliotecario
        );

        txtIsbn.setEditable(
                bibliotecario
        );

        txtEditorial.setEditable(
                bibliotecario
        );

        txtStock.setEditable(
                bibliotecario
        );

        cmbCategoria.setEnabled(
                bibliotecario
        );

        btnRegistrar.setVisible(
                bibliotecario
        );

        btnEditar.setVisible(
                bibliotecario
        );

        btnEliminar.setVisible(
                bibliotecario
        );

        btnLimpiar.setVisible(
                bibliotecario
        );
    }

    /**
     * Carga las categorías desde
     * la base de datos.
     */
    private void cargarCategorias() {

        cmbCategoria.removeAllItems();

        List<Categoria> categorias =
                controladorCategorias
                        .listarCategorias();

        for (Categoria categoria
                : categorias) {

            cmbCategoria.addItem(
                    categoria
            );
        }
    }

    /**
     * Carga los libros registrados.
     */
    private void cargarLibros() {

        modeloTabla.setRowCount(0);

        List<Libro> libros =
                controladorLibros
                        .listarLibros();

        for (Libro libro : libros) {

            modeloTabla.addRow(
                    new Object[]{
                            libro.getId(),
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getIsbn(),
                            libro.getEditorial(),
                            libro.getStock(),
                            obtenerNombreCategoria(
                                    libro.getIdCategoria()
                            )
                    }
            );
        }
    }

    /**
     * Obtiene el nombre de una categoría
     * según su ID.
     */
    private String obtenerNombreCategoria(
            int idCategoria
    ) {

        for (int i = 0;
             i < cmbCategoria.getItemCount();
             i++) {

            Categoria categoria =
                    cmbCategoria.getItemAt(i);

            if (categoria.getId()
                    == idCategoria) {

                return categoria.getNombre();
            }
        }

        return "Sin categoría";
    }

    /**
     * Registra un libro nuevo.
     */
    private void registrarLibro() {

        if (!validarFormulario()) {
            return;
        }

        Categoria categoria =
                (Categoria)
                        cmbCategoria
                                .getSelectedItem();

        int stock =
                Integer.parseInt(
                        txtStock
                                .getText()
                                .trim()
                );

        boolean resultado =
                controladorLibros
                        .registrarLibro(
                                txtTitulo.getText(),
                                txtAutor.getText(),
                                txtIsbn.getText(),
                                txtEditorial.getText(),
                                stock,
                                categoria.getId()
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro registrado correctamente.",
                    "Registro",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarLibros();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el libro.\n"
                            + "El ISBN podría estar registrado anteriormente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el libro seleccionado.
     */
    private void editarLibro() {

        int fila =
                tablaLibros
                        .getSelectedRow();

        if (fila == -1) {

            mostrarValidacion(
                    "Debe seleccionar un libro."
            );

            return;
        }

        if (!validarFormulario()) {
            return;
        }

        Categoria categoria =
                (Categoria)
                        cmbCategoria
                                .getSelectedItem();

        int id =
                (int)
                        modeloTabla.getValueAt(
                                fila,
                                0
                        );

        int stock =
                Integer.parseInt(
                        txtStock
                                .getText()
                                .trim()
                );

        boolean resultado =
                controladorLibros
                        .actualizarLibro(
                                id,
                                txtTitulo.getText(),
                                txtAutor.getText(),
                                txtIsbn.getText(),
                                txtEditorial.getText(),
                                stock,
                                categoria.getId()
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro actualizado correctamente.",
                    "Actualización",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarLibros();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el libro.\n"
                            + "Revise que el ISBN no pertenezca "
                            + "a otro libro.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el libro seleccionado.
     */
    private void eliminarLibro() {

        int fila =
                tablaLibros
                        .getSelectedRow();

        if (fila == -1) {

            mostrarValidacion(
                    "Debe seleccionar un libro."
            );

            return;
        }

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar el libro seleccionado?",
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
                controladorLibros
                        .eliminarLibro(
                                id
                        );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro eliminado correctamente."
            );

            cargarLibros();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el libro.\n"
                            + "Puede estar asociado a un préstamo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Valida los campos utilizados para
     * registrar o actualizar un libro.
     */
    private boolean validarFormulario() {

        if (!Validador.validarTitulo(
                txtTitulo.getText()
        )) {

            mostrarValidacion(
                    "Título inválido.\n"
                            + "Debe ingresar un título "
                            + "de hasta 200 caracteres."
            );

            txtTitulo.requestFocus();
            return false;
        }

        if (!Validador.validarAutor(
                txtAutor.getText()
        )) {

            mostrarValidacion(
                    "Autor inválido.\n"
                            + "Utilice al menos 2 caracteres "
                            + "y solo letras, espacios, puntos, "
                            + "apóstrofes o guiones."
            );

            txtAutor.requestFocus();
            return false;
        }

        if (!Validador.validarIsbn(
                txtIsbn.getText()
        )) {

            mostrarValidacion(
                    "ISBN inválido.\n"
                            + "Debe contener 10 o 13 dígitos."
            );

            txtIsbn.requestFocus();
            return false;
        }

        if (!Validador.validarEditorial(
                txtEditorial.getText()
        )) {

            mostrarValidacion(
                    "Editorial inválida.\n"
                            + "Debe ingresar entre 2 "
                            + "y 100 caracteres."
            );

            txtEditorial.requestFocus();
            return false;
        }

        int stock;

        try {

            stock =
                    Integer.parseInt(
                            txtStock
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException e) {

            mostrarValidacion(
                    "Stock inválido.\n"
                            + "Debe ingresar un número entero."
            );

            txtStock.requestFocus();
            return false;
        }

        if (!Validador.validarStock(
                stock
        )) {

            mostrarValidacion(
                    "Stock inválido.\n"
                            + "El stock no puede ser negativo."
            );

            txtStock.requestFocus();
            return false;
        }

        if (cmbCategoria
                .getSelectedItem() == null) {

            mostrarValidacion(
                    "Debe seleccionar una categoría."
            );

            return false;
        }

        return true;
    }

    /**
     * Carga los datos del libro seleccionado
     * en el formulario.
     */
    private void cargarLibroSeleccionado() {

        int fila =
                tablaLibros
                        .getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtTitulo.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                1
                        )
                        .toString()
        );

        txtAutor.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                2
                        )
                        .toString()
        );

        txtIsbn.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                3
                        )
                        .toString()
        );

        txtEditorial.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                4
                        )
                        .toString()
        );

        txtStock.setText(
                modeloTabla
                        .getValueAt(
                                fila,
                                5
                        )
                        .toString()
        );

        String nombreCategoria =
                modeloTabla
                        .getValueAt(
                                fila,
                                6
                        )
                        .toString();

        for (int i = 0;
             i < cmbCategoria.getItemCount();
             i++) {

            Categoria categoria =
                    cmbCategoria.getItemAt(i);

            if (categoria
                    .getNombre()
                    .equals(nombreCategoria)) {

                cmbCategoria
                        .setSelectedIndex(i);

                break;
            }
        }
    }

    /**
     * Actualiza categorías y libros.
     */
    private void actualizarDatos() {

        cargarCategorias();
        cargarLibros();
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        if (cmbCategoria
                .getItemCount() > 0) {

            cmbCategoria
                    .setSelectedIndex(0);
        }

        tablaLibros
                .clearSelection();

        txtTitulo.requestFocus();
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
