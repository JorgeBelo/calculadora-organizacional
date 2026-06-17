package br.com.calculadoraorganizacional.view;

import javax.swing.*;
import java.awt.*;

public class TelaDashboard extends JFrame {

    private JPanel painelPrincipal;

    private RoundedButton btnFinanciamento;
    private RoundedButton btnPropostas;
    private RoundedButton btnHistorico;
    private RoundedButton btnComparador;
    private RoundedButton btnCronograma;
    private RoundedButton btnCapacidade;
    private RoundedButton btnCustosCompra;
    private RoundedButton btnDarkMode;

    private boolean modoEscuro = true;

    private int usuarioId;
    private String nomeUsuario;

    private static final int Y_BASE = 110;
    private static final int ALTURA_BOTAO = 55;
    private static final int ESPACO = 10;

    public TelaDashboard(String nomeUsuario, int usuarioId) {

        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;

        setTitle("ImobiCalc Pro");
        setSize(650, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        add(painelPrincipal);

        // TÍTULO
        JLabel titulo = new JLabel("Bem-vindo, " + nomeUsuario, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(75, 25, 500, 40);
        painelPrincipal.add(titulo);

        // SUBTÍTULO
        JLabel subtitulo = new JLabel("Sistema de Simulação Imobiliária", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setBounds(75, 60, 500, 25);
        painelPrincipal.add(subtitulo);

        // LINHA 1
        btnFinanciamento = criarBotao("Simulação de Financiamento", Y_BASE, new Color(29, 78, 216));
        btnPropostas = criarBotao("Gerador de Propostas", Y_BASE + ALTURA_BOTAO + ESPACO, new Color(16, 185, 129));

        // LINHA 2
        btnComparador = criarBotao("Comparador Price x SAC", Y_BASE + (ALTURA_BOTAO + ESPACO) * 2, new Color(124, 58, 237));
        btnCronograma = criarBotao("Cronograma de Parcelas", Y_BASE + (ALTURA_BOTAO + ESPACO) * 3, new Color(234, 88, 12));

        // LINHA 3
        btnCapacidade = criarBotao("Capacidade de Crédito", Y_BASE + (ALTURA_BOTAO + ESPACO) * 4, new Color(220, 38, 38));
        btnCustosCompra = criarBotao("Custos Totais de Compra", Y_BASE + (ALTURA_BOTAO + ESPACO) * 5, new Color(5, 150, 105));

        // LINHA 4
        btnHistorico = criarBotao("Histórico de Operações", Y_BASE + (ALTURA_BOTAO + ESPACO) * 6, new Color(59, 130, 246));

        // BOTÃO TEMA
        btnDarkMode = new RoundedButton("Modo Claro");
        btnDarkMode.setBounds(520, 20, 100, 30);
        btnDarkMode.setBackground(new Color(51, 65, 85));
        btnDarkMode.setForeground(Color.WHITE);
        btnDarkMode.setFont(new Font("Arial", Font.BOLD, 11));
        btnDarkMode.addActionListener(e -> alternarModo());
        painelPrincipal.add(btnDarkMode);

        atualizarCores();

        setVisible(true);
    }

    private RoundedButton criarBotao(String texto, int y, Color cor) {

        RoundedButton btn = new RoundedButton(texto);

        btn.setBounds(75, y, 500, ALTURA_BOTAO);

        btn.setBackground(cor);

        btn.setForeground(Color.WHITE);

        btn.setFont(new Font("Arial", Font.BOLD, 16));

        btn.addActionListener(e -> abrirTela(texto));

        painelPrincipal.add(btn);

        return btn;
    }

    private void abrirTela(String tela) {

        switch (tela) {

            case "Simulação de Financiamento":
                new TelaFinanciamento(nomeUsuario, usuarioId);
                break;

            case "Gerador de Propostas":
                new TelaPropostas(nomeUsuario, usuarioId);
                break;

            case "Comparador Price x SAC":
                new TelaComparadorPriceSAC(nomeUsuario, usuarioId);
                break;

            case "Cronograma de Parcelas":
                new TelaCronogramoParcelas(nomeUsuario, usuarioId);
                break;

            case "Capacidade de Crédito":
                new TelaCapacidadeCredito(nomeUsuario, usuarioId);
                break;

            case "Custos Totais de Compra":
                new TelaCustosTotaisCompra(nomeUsuario, usuarioId);
                break;

            case "Histórico de Operações":
                new TelaHistorico(usuarioId, nomeUsuario);
                break;
        }
    }

    private void alternarModo() {

        modoEscuro = !modoEscuro;

        atualizarCores();
    }

    private void atualizarCores() {

        Color fundo = modoEscuro ? new Color(15, 23, 42) : new Color(248, 250, 252);
        Color texto = modoEscuro ? Color.WHITE : new Color(15, 23, 42);
        Color subtexto = modoEscuro ? new Color(203, 213, 225) : new Color(71, 85, 105);

        painelPrincipal.setBackground(fundo);

        for (Component c : painelPrincipal.getComponents()) {

            if (c instanceof JLabel) {

                JLabel lbl = (JLabel) c;

                if (lbl.getFont().getSize() == 24) {

                    lbl.setForeground(texto);

                } else {

                    lbl.setForeground(subtexto);
                }
            }
        }

        btnDarkMode.setText(modoEscuro ? "Modo Claro" : "Modo Escuro");
    }
}
