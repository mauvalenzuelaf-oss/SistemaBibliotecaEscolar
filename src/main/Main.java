package main;

import vista.VentanaLogin;

import javax.swing.SwingUtilities;

/**
 * Clase principal del Sistema de Biblioteca Escolar.
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    VentanaLogin ventana =
                            new VentanaLogin();

                    ventana.setVisible(true);
                }
        );
    }
}