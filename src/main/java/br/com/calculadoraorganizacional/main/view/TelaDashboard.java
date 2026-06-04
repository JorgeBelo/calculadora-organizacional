package br.com.calculadoraorganizacional.main.view;

import javax.swing.*;
import java.awt.*;

public class TelaDashboard extends JFrame {

    private JPanel painelPrincipal;

    private JLabel titulo;
    private JLabel subtitulo;

    private RoundedButton btnFinanciamento;
    private RoundedButton btnPropostas;
    private RoundedButton btnHistorico;
    private RoundedButton btnDarkMode;

    private boolean modoEscuro = true;

    public TelaDashboard(String nomeUsuario) {

        setTitle("ImobiCalc Pro");

        setSize(650, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        painelPrincipal = new JPanel();

        painelPrincipal.setLayout(null);

        add(painelPrincipal);

        // TÍTULO

        titulo = new JLabel(
                "Bem-vindo, " + nomeUsuario,
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        titulo.setBounds(75, 25, 500, 40);

        painelPrincipal.add(titulo);

        // SUBTÍTULO

        subtitulo = new JLabel(
                "Sistema de Simulação Imobiliária",
                SwingConstants.CENTER
        );

        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        subtitulo.setBounds(75, 60, 500, 25);

        painelPrincipal.add(subtitulo);

        // FINANCIAMENTO

        btnFinanciamento =
                new RoundedButton("Simular Financiamento");

        btnFinanciamento.setBounds(
                125,
                120,
                400,
                60
        );

        btnFinanciamento.addActionListener(
                e -> new TelaFinanciamento()
        );

        painelPrincipal.add(btnFinanciamento);

        // PROPOSTAS

        btnPropostas =
                new RoundedButton("Gerar Proposta");

        btnPropostas.setBounds(
                125,
                210,
                400,
                60
        );

        btnPropostas.addActionListener(
                e -> new TelaPropostas()
        );

        painelPrincipal.add(btnPropostas);

        // HISTÓRICO

        btnHistorico =
                new RoundedButton("Histórico");

        btnHistorico.setBounds(
                125,
                300,
                400,
                60
        );

        btnHistorico.addActionListener(
                e -> new TelaHistorico()
        );

        painelPrincipal.add(btnHistorico);
        // TEMA

        btnDarkMode =
                new RoundedButton("Alternar Tema");

        btnDarkMode.setBounds(
                200,
                410,
                250,
                50
        );

        btnDarkMode.addActionListener(
                e -> alternarTema()
        );

        painelPrincipal.add(btnDarkMode);

        aplicarTemaEscuro();

        setVisible(true);
    }

    private void alternarTema() {

        modoEscuro = !modoEscuro;

        if (modoEscuro) {
            aplicarTemaEscuro();
        } else {
            aplicarTemaClaro();
        }
    }

    private void aplicarTemaEscuro() {

        painelPrincipal.setBackground(
                new Color(15, 23, 42)
        );

        titulo.setForeground(Color.WHITE);

        subtitulo.setForeground(
                new Color(203, 213, 225)
        );

        Color corBotao =
                new Color(30, 41, 59);

        btnFinanciamento.setBackground(corBotao);
        btnPropostas.setBackground(corBotao);
        btnHistorico.setBackground(corBotao);
        btnDarkMode.setBackground(corBotao);

        btnFinanciamento.setForeground(Color.WHITE);
        btnPropostas.setForeground(Color.WHITE);
        btnHistorico.setForeground(Color.WHITE);
        btnDarkMode.setForeground(Color.WHITE);
    }

    private void aplicarTemaClaro() {

        painelPrincipal.setBackground(
                new Color(248, 250, 252)
        );

        titulo.setForeground(
                new Color(15, 23, 42)
        );

        subtitulo.setForeground(
                new Color(71, 85, 105)
        );

        btnFinanciamento.setBackground(Color.WHITE);
        btnPropostas.setBackground(Color.WHITE);
        btnHistorico.setBackground(Color.WHITE);
        btnDarkMode.setBackground(Color.WHITE);

        btnFinanciamento.setForeground(Color.BLACK);
        btnPropostas.setForeground(Color.BLACK);
        btnHistorico.setForeground(Color.BLACK);
        btnDarkMode.setForeground(Color.BLACK);
    }
}

