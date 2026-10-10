package vista;

import controlador.ControladorUsuarios;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de autenticación del sistema.
 */
public class VentanaLogin extends JFrame {

    private final ControladorUsuarios controladorUsuarios;

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    /**
     * Inicializa la ventana de autenticación.
     */
    public VentanaLogin() {

        controladorUsuarios =
                new ControladorUsuarios();

        configurarVentana();
        crearComponentes();
    }

    /**
     * Configura las características principales
     * de la ventana.
     */
    private void configurarVentana() {

        setTitle("Biblioteca Escolar - Inicio de Sesión");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Crea y organiza los componentes gráficos.
     */
    private void crearComponentes() {

        JPanel panel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        JLabel lblTitulo =
                new JLabel("Sistema de Biblioteca Escolar");

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(
                lblTitulo,
                gbc
        );

        gbc.gridwidth = 1;

        JLabel lblCorreo =
                new JLabel("Correo:");

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(
                lblCorreo,
                gbc
        );

        txtCorreo =
                new JTextField(20);

        gbc.gridx = 1;

        panel.add(
                txtCorreo,
                gbc
        );

        JLabel lblContrasena =
                new JLabel("Contraseña:");

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(
                lblContrasena,
                gbc
        );

        txtContrasena =
                new JPasswordField(20);

        gbc.gridx = 1;

        panel.add(
                txtContrasena,
                gbc
        );

        btnIngresar =
                new JButton("Ingresar");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(
                btnIngresar,
                gbc
        );

        btnIngresar.addActionListener(
                e -> iniciarSesion()
        );

        add(panel);
    }

    /**
     * Valida las credenciales ingresadas
     * y autentica al usuario.
     */
    private void iniciarSesion() {

        String correo =
                txtCorreo
                        .getText()
                        .trim();

        String contrasena =
                new String(
                        txtContrasena
                                .getPassword()
                );

        if (correo.isBlank()
                || contrasena.isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar correo y contraseña.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Usuario usuario =
                controladorUsuarios.autenticar(
                        correo,
                        contrasena
                );

        if (usuario == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Correo o contraseña incorrectos.",
                    "Autenticación",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        VentanaPrincipal ventanaPrincipal =
                new VentanaPrincipal(usuario);

        ventanaPrincipal.setVisible(true);

        dispose();
    }
}