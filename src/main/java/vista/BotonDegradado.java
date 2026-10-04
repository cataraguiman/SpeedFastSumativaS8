package vista;

import javax.swing.*;
import java.awt.*;

public class BotonDegradado extends JButton {

    // Colores utilizados para crear el degradado.
    private final Color colorSuperior =
            new Color(248, 252, 255);

    private final Color colorInferior =
            new Color(185, 218, 235);

    public BotonDegradado(String texto) {

        super(texto);

        // Configuración visual del botón.
        setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.BOLD,
                        16
                )
        );

        setForeground(
                new Color(35, 35, 35)
        );

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        // Activamos un renderizado más suave.
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Creamos el degradado vertical.
        GradientPaint degradado =
                new GradientPaint(
                        0,
                        0,
                        colorSuperior,
                        0,
                        getHeight(),
                        colorInferior
                );

        g2.setPaint(degradado);

        // Dibujamos el fondo del botón.
        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        g2.dispose();

        // Dibujamos el texto y los emojis del botón.
        super.paintComponent(g);
    }
}