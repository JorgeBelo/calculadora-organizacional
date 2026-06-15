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
    private RoundedButton btnComparador;
    private RoundedButton btnCronograma;
    private RoundedButton btnCapacidade;
    private RoundedButton btnCustosCompra;
    private RoundedButton btnRelatorio;
    private RoundedButton btnDarkMode;

    private boolean modoEscuro = true;

    private int usuarioId;
    private String nomeUsuario;

    public TelaDashboard(String nomeUsuario, int usuarioId) {

        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;

        setTitle("ImobiCalc Pro");

        setSize(650, 720);

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

        int yBase = 110;
        int altura = 50;
        int espaco = 10;

        // FINANCIAMENTO

        btnFinanciamento = new RoundedButton("Simular Financiamento");
        btnFinanciamento.setBounds(125, yBase, 400, altura);
        btnFinanciamento.addActionListener(e -> new TelaFinanciamento());
        painelPrincipal.add(btnFinanciamento);

        // PROPOSTAS

        btnPropostas = new RoundedButton("Gerar Proposta");
        btnPropostas.setBounds(125, yBase + (altura + espaco), 400, altura);
        btnPropostas.addActionListener(e -> new TelaPropostas());
        painelPrincipal.add(btnPropostas);

        // HISTÓRICO

        btnHistorico = new RoundedButton("Histórico");
        btnHistorico.setBounds(125, yBase + 2 * (altura + espaco), 400, altura);
        btnHistorico.addActionListener(e -> new TelaHistorico(usuarioId));
        painelPrincipal.add(btnHistorico);

        // COMPARADOR PRICE x SAC

        btnComparador = new RoundedButton("Comparador Price x SAC");
        btnComparador.setBounds(125, yBase + 3 * (altura + espaco), 400, altura);
        btnComparador.addActionListener(e -> new TelaComparadorPriceSAC(nomeUsuario, usuarioId));
        painelPrincipal.add(btnComparador);

        // CRONOGRAMA DE PARCELAS

        btnCronograma = new RoundedButton("Cronograma de Parcelas");
        btnCronograma.setBounds(125, yBase + 4 * (altura + espaco), 400, altura);
        btnCronograma.addActionListener(e -> new TelaCronogramoParcelas(nomeUsuario, usuarioId));
        painelPrincipal.add(btnCronograma);

        // CAPACIDADE DE CRÉDITO

        btnCapacidade = new RoundedButton("Capacidade de Crédito");
        btnCapacidade.setBounds(125, yBase + 5 * (altura + espaco), 400, altura);
        btnCapacidade.addActionListener(e -> new TelaCapacidadeCredito(nomeUsuario, usuarioId));
        painelPrincipal.add(btnCapacidade);

        // CUSTOS TOTAIS DE COMPRA

        btnCustosCompra = new RoundedButton("Custos Totais de Compra");
        btnCustosCompra.setBounds(125, yBase + 6 * (altura + espaco), 400, altura);
        btnCustosCompra.addActionListener(e -> new TelaCustosTotaisCompra(nomeUsuario, usuarioId));
        painelPrincipal.add(btnCustosCompra);

        // RELATÓRIO DE CLIENTES

        btnRelatorio = new RoundedButton("Relatório de Clientes");
        btnRelatorio.setBounds(125, yBase + 7 * (altura + espaco), 400, altura);
        btnRelatorio.addActionListener(e -> new TelaRelatorioCliente(nomeUsuario, usuarioId));
        painelPrincipal.add(btnRelatorio);

        // TEMA

        btnDarkMode = new RoundedButton("Alternar Tema");
        btnDarkMode.setBounds(200, yBase + 8 * (altura + espaco) + 5, 250, 45);
        btnDarkMode.addActionListener(e -> alternarTema());
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
        btnComparador.setBackground(corBotao);
        btnCronograma.setBackground(corBotao);
        btnCapacidade.setBackground(corBotao);
        btnCustosCompra.setBackground(corBotao);
        btnRelatorio.setBackground(corBotao);
        btnDarkMode.setBackground(corBotao);

        btnFinanciamento.setForeground(Color.WHITE);
        btnPropostas.setForeground(Color.WHITE);
        btnHistorico.setForeground(Color.WHITE);
        btnComparador.setForeground(Color.WHITE);
        btnCronograma.setForeground(Color.WHITE);
        btnCapacidade.setForeground(Color.WHITE);
        btnCustosCompra.setForeground(Color.WHITE);
        btnRelatorio.setForeground(Color.WHITE);
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
        btnComparador.setBackground(Color.WHITE);
        btnCronograma.setBackground(Color.WHITE);
        btnCapacidade.setBackground(Color.WHITE);
        btnCustosCompra.setBackground(Color.WHITE);
        btnRelatorio.setBackground(Color.WHITE);
        btnDarkMode.setBackground(Color.WHITE);

        btnFinanciamento.setForeground(Color.BLACK);
        btnPropostas.setForeground(Color.BLACK);
        btnHistorico.setForeground(Color.BLACK);
        btnComparador.setForeground(Color.BLACK);
        btnCronograma.setForeground(Color.BLACK);
        btnCapacidade.setForeground(Color.BLACK);
        btnCustosCompra.setForeground(Color.BLACK);
        btnRelatorio.setForeground(Color.BLACK);
        btnDarkMode.setForeground(Color.BLACK);
    }
}