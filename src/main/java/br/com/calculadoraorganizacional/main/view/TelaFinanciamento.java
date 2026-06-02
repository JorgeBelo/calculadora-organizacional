package br.com.calculadoraorganizacional.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaFinanciamento extends JFrame {

    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JLabel resultado;

    public TelaFinanciamento() {

        setTitle("Simulador de Financiamento");

        setSize(500, 500);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel lblValor = new JLabel("Valor do Imóvel:");

        lblValor.setBounds(30, 30, 150, 30);

        add(lblValor);

        campoValorImovel = new JTextField();

        campoValorImovel.setBounds(180, 30, 200, 30);

        add(campoValorImovel);

        JLabel lblEntrada = new JLabel("Entrada:");

        lblEntrada.setBounds(30, 80, 150, 30);

        add(lblEntrada);

        campoEntrada = new JTextField();

        campoEntrada.setBounds(180, 80, 200, 30);

        add(campoEntrada);

        JLabel lblJuros = new JLabel("Juros (% ao mês):");

        lblJuros.setBounds(30, 130, 150, 30);

        add(lblJuros);

        campoJuros = new JTextField();

        campoJuros.setBounds(180, 130, 200, 30);

        add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");

        lblPrazo.setBounds(30, 180, 150, 30);

        add(lblPrazo);

        campoPrazo = new JTextField();

        campoPrazo.setBounds(180, 180, 200, 30);

        add(campoPrazo);

        JButton btnCalcular = new JButton("Calcular");

        btnCalcular.setBounds(150, 250, 150, 40);

        add(btnCalcular);

        resultado = new JLabel("");

        resultado.setBounds(30, 330, 420, 40);

        add(resultado);

        btnCalcular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                calcularFinanciamento();
            }
        });

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

            resultado.setText(
                    String.format(
                            "Valor financiado: R$ %.2f | Parcela: R$ %.2f",
                            valorFinanciado,
                            parcela
                    )
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos corretamente!"
            );
        }
    }
}