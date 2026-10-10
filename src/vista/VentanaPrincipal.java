package vista;

import modelo.Persona;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del Sistema de Biblioteca Escolar.
 * Muestra las opciones disponibles según el rol del usuario.
 */
public class VentanaPrincipal extends JFrame {

    private final Usuario usuario;

    private JButton btnLibros;
    private JButton btnEstudiantes;
    private JButton btnPrestamos;
    private JButton btnReportes;
    private JButton btnCerrarSesion;

    /**
     * Inicializa la ventana principal
     * según el usuario autenticado.
     */
    public VentanaPrincipal(
            Usuario usuario
    ) {

        this.usuario = usuario;

        configurarVentana();
        crearComponentes();
    }

    /**
     * Configura las características principales
     * de la ventana.
     */
    private void configurarVentana() {

        setTitle(
                "Biblioteca Escolar - Menú Principal"
        );

        setSize(
                500,
                420
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Crea los componentes según
     * el rol del usuario autenticado.
     */
    private void crearComponentes() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        50,
                        25,
                        50
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "Sistema de Biblioteca Escolar"
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        /*
         * Se utiliza una referencia de tipo Persona
         * para demostrar polimorfismo.
         */
        Persona persona =
                usuario;

        JLabel lblUsuario =
                new JLabel(
                        persona.getDescripcion()
                                + " | Rol: "
                                + usuario.getRol()
                );

        lblUsuario.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(lblTitulo);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(lblUsuario);

        panel.add(
                Box.createVerticalStrut(25)
        );

        // Botones
        btnLibros =
                new JButton();

        btnEstudiantes =
                new JButton(
                        "Gestión de Estudiantes"
                );

        btnPrestamos =
                new JButton(
                        "Préstamos y Devoluciones"
                );

        btnReportes =
                new JButton(
                        "Reportes"
                );

        btnCerrarSesion =
                new JButton(
                        "Cerrar Sesión"
                );

        configurarBoton(btnLibros);
        configurarBoton(btnEstudiantes);
        configurarBoton(btnPrestamos);
        configurarBoton(btnReportes);
        configurarBoton(btnCerrarSesion);

        /*
         * Opciones disponibles según el rol.
         */
        if (usuario.getRol()
                .equals("bibliotecario")) {

            btnLibros.setText(
                    "Gestión de Libros"
            );

            panel.add(btnLibros);

            panel.add(
                    Box.createVerticalStrut(10)
            );

            panel.add(btnEstudiantes);

            panel.add(
                    Box.createVerticalStrut(10)
            );

            panel.add(btnPrestamos);

            panel.add(
                    Box.createVerticalStrut(10)
            );

            panel.add(btnReportes);

            panel.add(
                    Box.createVerticalStrut(10)
            );

        } else {

            btnLibros.setText(
                    "Consulta de Libros"
            );

            panel.add(btnLibros);

            panel.add(
                    Box.createVerticalStrut(10)
            );

            panel.add(btnPrestamos);

            panel.add(
                    Box.createVerticalStrut(10)
            );
        }

        panel.add(
                Box.createVerticalStrut(15)
        );

        panel.add(btnCerrarSesion);

        // Eventos
        btnLibros.addActionListener(
                e -> abrirModuloLibros()
        );

        btnEstudiantes.addActionListener(
                e -> abrirModuloEstudiantes()
        );

        btnPrestamos.addActionListener(
                e -> abrirModuloPrestamos()
        );

        btnReportes.addActionListener(
                e -> abrirModuloReportes()
        );

        btnCerrarSesion.addActionListener(
                e -> cerrarSesion()
        );

        add(panel);
    }

    /**
     * Configura el formato común
     * de los botones.
     */
    private void configurarBoton(
            JButton boton
    ) {

        boton.setMaximumSize(
                new Dimension(
                        300,
                        40
                )
        );

        boton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );
    }

    /**
     * Abre el módulo de libros.
     */
    private void abrirModuloLibros() {

        VentanaGestionLibros ventana =
                new VentanaGestionLibros(
                        usuario
                );

        ventana.setVisible(true);
    }

    /**
     * Abre el módulo de gestión
     * de estudiantes.
     */
    private void abrirModuloEstudiantes() {

        VentanaGestionEstudiantes ventana =
                new VentanaGestionEstudiantes();

        ventana.setVisible(true);
    }

    /**
     * Abre el módulo de préstamos
     * y devoluciones.
     */
    private void abrirModuloPrestamos() {

        VentanaPrestamos ventana =
                new VentanaPrestamos(
                        usuario
                );

        ventana.setVisible(true);
    }

    /**
     * Abre el módulo de reportes.
     */
    private void abrirModuloReportes() {

        VentanaReportes ventana =
                new VentanaReportes();

        ventana.setVisible(true);
    }

    /**
     * Cierra la sesión actual y vuelve
     * a la ventana de autenticación.
     */
    private void cerrarSesion() {

        VentanaLogin login =
                new VentanaLogin();

        login.setVisible(true);

        dispose();
    }
}
