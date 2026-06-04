package br.com.calculadoraorganizacional.main.view;

import javax.swing.*;
import java.awt.*;

public class TelaFinanciamento extends JFrame {

    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JLabel resultado;

    public TelaFinanciamento() {

        setTitle("Simulador de Financiamento");

        setSize(550, 550);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        JLabel lblValor = new JLabel("Valor do Imóvel:");

        lblValor.setBounds(30, 30, 150, 30);

        add(lblValor);

        campoValorImovel = new JTextField();

        campoValorImovel.setBounds(200, 30, 250, 30);

        add(campoValorImovel);

        JLabel lblEntrada = new JLabel("Entrada:");

        lblEntrada.setBounds(30, 80, 150, 30);

        add(lblEntrada);

        campoEntrada = new JTextField();

        campoEntrada.setBounds(200, 80, 250, 30);

        add(campoEntrada);

        JLabel lblJuros = new JLabel("Juros (% ao mês):");

        lblJuros.setBounds(30, 130, 150, 30);

        add(lblJuros);

        campoJuros = new JTextField();

        campoJuros.setBounds(200, 130, 250, 30);

        add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");

        lblPrazo.setBounds(30, 180, 150, 30);

        add(lblPrazo);

        campoPrazo = new JTextField();

        campoPrazo.setBounds(200, 180, 250, 30);

        add(campoPrazo);

        JButton btnCalcular = new JButton("Calcular");

        btnCalcular.setBounds(190, 250, 150, 40);

        add(btnCalcular);

        resultado = new JLabel();

        resultado.setBounds(30, 320, 470, 140);

        resultado.setFont(new Font("Arial", Font.PLAIN, 14));

        add(resultado);

        btnCalcular.addActionListener(e -> calcularFinanciamento());

        setVisible(true);
    }

    private void calcularFinanciamento() {

        try {

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

            double totalPago =
                    parcela * prazo;

            double totalJuros =
                    totalPago - valorFinanciado;

            resultado.setText(
                    "<html>" +
                            "<b>Resultado da Simulação</b><br><br>" +

                            "Valor financiado: R$ "
                            + String.format("%.2f", valorFinanciado)
                            + "<br>" +

                            "Parcela mensal: R$ "
                            + String.format("%.2f", parcela)
                            + "<br>" +

                            "Total pago: R$ "
                            + String.format("%.2f", totalPago)
                            + "<br>" +

                            "Total de juros: R$ "
                            + String.format("%.2f", totalJuros)
                            +
                            "</html>"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos corretamente!"
            );
        }
    }
}