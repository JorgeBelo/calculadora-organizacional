package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaFinanciamento extends JFrame {

    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JLabel resultado;

    private int usuarioId;
    private String nomeUsuario;

    public TelaFinanciamento(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("ImobiCalc Pro - Financiamento");

        setSize(650, 650);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        getContentPane().setBackground(
                new Color(15, 23, 42)
        );

        // TÍTULO

        JLabel titulo = new JLabel(
                "Simulação de Financiamento",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setForeground(Color.WHITE);

        titulo.setBounds(75, 20, 500, 40);

        add(titulo);

        // SUBTÍTULO

        JLabel subtitulo = new JLabel(
                "Preencha os dados abaixo",
                SwingConstants.CENTER
        );

        subtitulo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        subtitulo.setForeground(
                new Color(203, 213, 225)
        );

        subtitulo.setBounds(75, 55, 500, 25);

        add(subtitulo);

        // CARD

        JPanel card = new JPanel();

        card.setLayout(null);

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBounds(
                50,
                100,
                530,
                460
        );

        add(card);

        // VALOR IMÓVEL

        JLabel lblValor =
                new JLabel("Valor do Imóvel");

        lblValor.setForeground(Color.WHITE);

        lblValor.setBounds(40, 30, 200, 25);

        card.add(lblValor);

        campoValorImovel = new JTextField();

        campoValorImovel.setBounds(
                40,
                55,
                450,
                35
        );

        card.add(campoValorImovel);

        // ENTRADA

        JLabel lblEntrada =
                new JLabel("Valor da Entrada");

        lblEntrada.setForeground(Color.WHITE);

        lblEntrada.setBounds(40, 100, 200, 25);

        card.add(lblEntrada);

        campoEntrada = new JTextField();

        campoEntrada.setBounds(
                40,
                125,
                450,
                35
        );

        card.add(campoEntrada);

        // JUROS

        JLabel lblJuros =
                new JLabel("Taxa de Juros (% ao mês)");

        lblJuros.setForeground(Color.WHITE);

        lblJuros.setBounds(40, 170, 250, 25);

        card.add(lblJuros);

        campoJuros = new JTextField();

        campoJuros.setBounds(
                40,
                195,
                450,
                35
        );

        card.add(campoJuros);

        // PRAZO

        JLabel lblPrazo =
                new JLabel("Prazo (meses)");

        lblPrazo.setForeground(Color.WHITE);

        lblPrazo.setBounds(40, 240, 200, 25);

        card.add(lblPrazo);

        campoPrazo = new JTextField();

        campoPrazo.setBounds(
                40,
                265,
                450,
                35
        );

        card.add(campoPrazo);

        // BOTÃO

        RoundedButton btnCalcular =
                new RoundedButton("Calcular");

        btnCalcular.setBounds(
                165,
                330,
                200,
                45
        );

        btnCalcular.setBackground(
                new Color(59, 130, 246)
        );

        btnCalcular.setForeground(Color.WHITE);

        btnCalcular.addActionListener(
                e -> calcularFinanciamento()
        );

        card.add(btnCalcular);

        // RESULTADO

        resultado = new JLabel();

        resultado.setForeground(Color.WHITE);

        resultado.setVerticalAlignment(
                SwingConstants.TOP
        );

        resultado.setBounds(
                40,
                395,
                450,
                80
        );

        card.add(resultado);

        setVisible(true);
    }

    private void calcularFinanciamento() {

        try {

            double valorImovel =
                    Double.parseDouble(
                            campoValorImovel
                                    .getText()
                                    .replace(",", ".")
                    );

            double entrada =
                    Double.parseDouble(
                            campoEntrada
                                    .getText()
                                    .replace(",", ".")
                    );

            double juros =
                    Double.parseDouble(
                            campoJuros
                                    .getText()
                                    .replace(",", ".")
                    ) / 100;

            int prazo =
                    Integer.parseInt(
                            campoPrazo.getText()
                    );

            double valorFinanciado =
                    valorImovel - entrada;

            double parcela =
                    (valorFinanciado * juros)
                            /
                            (1 - Math.pow(
                                    1 + juros,
                                    -prazo
                            ));

            double totalPago =
                    parcela * prazo;

            double totalJuros =
                    totalPago - valorFinanciado;

            NumberFormat moeda =
                    NumberFormat.getCurrencyInstance(
                            new Locale("pt", "BR")
                    );

            resultado.setText(
                    "<html>" +
                            "<b>Resultado da Simulação</b><br><br>" +

                            "Valor financiado: "
                            + moeda.format(valorFinanciado)
                            + "<br>" +

                            "Parcela mensal: "
                            + moeda.format(parcela)
                            + "<br>" +

                            "Total pago: "
                            + moeda.format(totalPago)
                            + "<br>" +

                            "Total de juros: "
                            + moeda.format(totalJuros)
                            + "</html>"
            );

            // Salvar no historico
            try {
                String expressao = "Imóvel=" + moeda.format(valorImovel)
                        + " | Entrada=" + moeda.format(entrada)
                        + " | Juros=" + (juros * 100) + "% a.m."
                        + " | Prazo=" + prazo + " meses";
                String resumo = "Financiado=" + moeda.format(valorFinanciado)
                        + " | Parcela=" + moeda.format(parcela)
                        + " | Total=" + moeda.format(totalPago)
                        + " | Juros=" + moeda.format(totalJuros);

                Historico h = new Historico(usuarioId, "Financiamento", expressao, resumo);
                new HistoricoDAO().salvar(h);
            } catch (Exception ex) {
                System.out.println("Erro ao salvar historico: " + ex.getMessage());
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos corretamente!"
            );
        }
    }
}