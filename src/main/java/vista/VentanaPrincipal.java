package vista;

import javax.swing.*;
import java.awt.*;

// Ventana principal del sistema SpeedFast.
public class VentanaPrincipal extends JFrame {

    private JButton btnRepartidores;
    private JButton btnPedidos;
    private JButton btnEntregas;
    private JButton btnSalir;

    public VentanaPrincipal() {

        // Configuración de la ventana.
        setTitle("SpeedFast | Centro de Entregas");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal.
        JPanel panelPrincipal = new JPanel(
                new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // Título.
        JLabel lblTitulo = new JLabel(
                " || SPEED FAST ||",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel lblSubtitulo = new JLabel(
                "Sistema de Gestión de Entregas",
                SwingConstants.CENTER
        );

        lblSubtitulo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        // Panel para los títulos.
        JPanel panelTitulo = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSubtitulo);

        // Creamos los botones.
        btnRepartidores = new BotonDegradado("| 👤 | Gestionar Repartidores |");
        btnPedidos = new BotonDegradado("| 📦 | Gestionar Pedidos |");
        btnEntregas = new BotonDegradado("| 🚚 | Gestionar Entregas |");
        btnSalir = new BotonDegradado("Salir");

        // Panel de botones.
        JPanel panelBotones = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);
        panelBotones.add(btnSalir);

        // Agregamos los paneles a la ventana.
        panelPrincipal.add(
                panelTitulo,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        // Eventos de los botones.
        btnRepartidores.addActionListener(e -> {

            VentanaRepartidores ventana =
                    new VentanaRepartidores();

            ventana.setVisible(true);
        });

        btnPedidos.addActionListener(e -> {

            VentanaPedidos ventana =
                    new VentanaPedidos();

            ventana.setVisible(true);
        });

        btnEntregas.addActionListener(e -> {

            VentanaEntregas ventana =
                    new VentanaEntregas();

            ventana.setVisible(true);
        });

        btnSalir.addActionListener(e -> {

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro de que desea salir?",
                            "Salir",
                            JOptionPane.YES_NO_OPTION
                    );

            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}