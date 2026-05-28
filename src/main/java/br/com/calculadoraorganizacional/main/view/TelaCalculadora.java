package br.com.calculadoraorganizacional.view;

import javax.swing.*;

public class TelaCalculadora extends JFrame {

    public TelaCalculadora(String nomeUsuario) {

        setTitle("Calculadora Organizacional");

        setSize(500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel labelUsuario = new JLabel("Bem-vindo, " + nomeUsuario);

        labelUsuario.setBounds(20, 20, 300, 30);

        add(labelUsuario);

        JTextField visor = new JTextField();

        visor.setBounds(50, 70, 380, 50);

        add(visor);

        JButton botao1 = new JButton("1");
        botao1.setBounds(50, 150, 80, 50);
        add(botao1);

        JButton botao2 = new JButton("2");
        botao2.setBounds(150, 150, 80, 50);
        add(botao2);

        JButton botao3 = new JButton("3");
        botao3.setBounds(250, 150, 80, 50);
        add(botao3);

        JButton botaoSoma = new JButton("+");
        botaoSoma.setBounds(350, 150, 80, 50);
        add(botaoSoma);

        setVisible(true);
    }
}