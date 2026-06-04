package br.com.calculadoraorganizacional.main.view;

import javax.swing.*;
import java.awt.*;

public class TelaPropostas extends JFrame {

    private JTextField campoNome;
    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JTextArea areaResultado;

    public TelaPropostas() {

        setTitle("Gerador de Propostas");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("Gerador de Proposta");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(210, 20, 300, 40);
        add(titulo);

        JLabel lblNome = new JLabel("Cliente:");
        lblNome.setBounds(50, 90, 150, 30);
        add(lblNome);

        campoNome = new JTextField();
        campoNome.setBounds(220, 90, 350, 30);
        add(campoNome);

        JLabel lblValor = new JLabel("Valor do Imóvel:");
        lblValor.setBounds(50, 140, 150, 30);
        add(lblValor);

        campoValorImovel = new JTextField();
        campoValorImovel.setBounds(220, 140, 350, 30);
        add(campoValorImovel);

        JLabel lblEntrada = new JLabel("Entrada:");
        lblEntrada.setBounds(50, 190, 150, 30);
        add(lblEntrada);

        campoEntrada = new JTextField();
        campoEntrada.setBounds(220, 190, 350, 30);
        add(campoEntrada);

        JLabel lblJuros = new JLabel("Juros (% ao mês):");
        lblJuros.setBounds(50, 240, 150, 30);
        add(lblJuros);

        campoJuros = new JTextField();
        campoJuros.setBounds(220, 240, 350, 30);
        add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");
        lblPrazo.setBounds(50, 290, 150, 30);
        add(lblPrazo);

        campoPrazo = new JTextField();
        campoPrazo.setBounds(220, 290, 350, 30);
        add(campoPrazo);

        JButton btnGerar = new JButton("Gerar Proposta");
        btnGerar.setBounds(230, 350, 220, 45);
        add(btnGerar);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(areaResultado);

        scroll.setBounds(50, 430, 580, 140);

        add(scroll);

        btnGerar.addActionListener(e -> gerarProposta());

        setVisible(true);
    }

    private void gerarProposta() {

        try {

            String nome = campoNome.getText();

            double valorImovel =
                    Double.parseDouble(campoValorImovel.getText());

            double entrada =
                    Double.parseDouble(campoEntrada.getText());

            double juros =
                    Double.parseDouble(campoJuros.getText()) / 100;

            int prazo =
                    Integer.parseInt(campoPrazo.getText());

            double valorFinanciado =
                    valorImovel - entrada;

            double parcela =
                    (valorFinanciado * juros)
                            / (1 - Math.pow(1 + juros, -prazo));

            double rendaMinima =
                    parcela * 3;

            String proposta =
                    "PROPOSTA DE FINANCIAMENTO\n\n" +
                            "Cliente: " + nome + "\n\n" +
                            "Valor do Imóvel: R$ " +
                            String.format("%.2f", valorImovel) + "\n" +
                            "Entrada: R$ " +
                            String.format("%.2f", entrada) + "\n\n" +
                            "Valor Financiado: R$ " +
                            String.format("%.2f", valorFinanciado) + "\n\n" +
                            "Parcela Estimada: R$ " +
                            String.format("%.2f", parcela) + "\n\n" +
                            "Renda Recomendada: R$ " +
                            String.format("%.2f", rendaMinima);

            areaResultado.setText(proposta);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos corretamente!"
            );
        }
    }
}