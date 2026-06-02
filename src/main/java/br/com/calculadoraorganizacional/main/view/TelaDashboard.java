package br.com.calculadoraorganizacional.view;

import javax.swing.*;

public class TelaDashboard extends JFrame {

    public TelaDashboard(String nomeUsuario) {

        setTitle("Dashboard Imobiliário");

        setSize(800, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        JLabel titulo = new JLabel("Bem-vindo, " + nomeUsuario);

        titulo.setBounds(30, 20, 400, 30);

        add(titulo);

        JButton btnFinanciamento = new JButton("Financiamento");

        btnFinanciamento.setBounds(50, 100, 200, 50);

        btnFinanciamento.addActionListener(e -> {
            new TelaFinanciamento();
        });

        add(btnFinanciamento);

        JButton btnParcelas = new JButton("Parcelas");

        btnParcelas.setBounds(50, 180, 200, 50);

        add(btnParcelas);

        JButton btnJuros = new JButton("Juros");

        btnJuros.setBounds(50, 260, 200, 50);

        add(btnJuros);

        JButton btnHistorico = new JButton("Histórico");

        btnHistorico.setBounds(50, 340, 200, 50);

        add(btnHistorico);

        setVisible(true);
    }
}