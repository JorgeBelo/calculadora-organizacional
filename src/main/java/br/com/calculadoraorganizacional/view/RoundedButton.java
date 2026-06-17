package br.com.calculadoraorganizacional.view;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    public RoundedButton(String texto) {

        super(texto);

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        setForeground(Color.WHITE);

        setFont(new Font("Arial", Font.BOLD, 18));

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // sombra

        g2.setColor(new Color(0, 0, 0, 60));

        g2.fillRoundRect(
                4,
                4,
                getWidth() - 4,
                getHeight() - 4,
                25,
                25
        );

        // fundo

        g2.setColor(getBackground());

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 4,
                getHeight() - 4,
                25,
                25
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(100, 116, 139));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 5,
                getHeight() - 5,
                25,
                25
        );

        g2.dispose();
    }
}