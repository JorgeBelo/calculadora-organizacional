package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TelaPropostas extends JFrame {

    private JTextField campoNome;
    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JTextArea areaResultado;

    private int usuarioId;
    private String nomeUsuario;

    public TelaPropostas(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("ImobiCalc Pro - Gerar Proposta");

        setSize(800, 700);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        getContentPane().setBackground(
                new Color(15, 23, 42)
        );

        // TÍTULO

        JLabel titulo = new JLabel(
                "Gerador de Propostas",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setForeground(Color.WHITE);

        titulo.setBounds(
                150,
                20,
                500,
                40
        );

        add(titulo);

        // SUBTÍTULO

        JLabel subtitulo = new JLabel(
                "Monte uma proposta imobiliária para seu cliente",
                SwingConstants.CENTER
        );

        subtitulo.setForeground(
                new Color(203, 213, 225)
        );

        subtitulo.setBounds(
                150,
                55,
                500,
                25
        );

        add(subtitulo);

        // CARD

        JPanel card = new JPanel();

        card.setLayout(null);

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBounds(
                40,
                100,
                700,
                520
        );

        add(card);

        JLabel lblNome = criarLabel("Cliente:");
        lblNome.setBounds(40, 30, 150, 30);
        card.add(lblNome);

        campoNome = criarCampo();
        campoNome.setBounds(220, 30, 400, 35);
        card.add(campoNome);

        JLabel lblValor = criarLabel("Valor do Imóvel:");
        lblValor.setBounds(40, 80, 150, 30);
        card.add(lblValor);

        campoValorImovel = criarCampo();
        campoValorImovel.setBounds(220, 80, 400, 35);
        card.add(campoValorImovel);

        JLabel lblEntrada = criarLabel("Entrada:");
        lblEntrada.setBounds(40, 130, 150, 30);
        card.add(lblEntrada);

        campoEntrada = criarCampo();
        campoEntrada.setBounds(220, 130, 400, 35);
        card.add(campoEntrada);

        JLabel lblJuros = criarLabel("Juros (% ao mês):");
        lblJuros.setBounds(40, 180, 150, 30);
        card.add(lblJuros);

        campoJuros = criarCampo();
        campoJuros.setBounds(220, 180, 400, 35);
        card.add(campoJuros);

        JLabel lblPrazo = criarLabel("Prazo (meses):");
        lblPrazo.setBounds(40, 230, 150, 30);
        card.add(lblPrazo);

        campoPrazo = criarCampo();
        campoPrazo.setBounds(220, 230, 400, 35);
        card.add(campoPrazo);

        RoundedButton btnGerar =
                new RoundedButton("Gerar Proposta");

        btnGerar.setBounds(
                240,
                290,
                220,
                45
        );

        btnGerar.setBackground(
                new Color(59, 130, 246)
        );

        btnGerar.setForeground(Color.WHITE);

        card.add(btnGerar);

        areaResultado = new JTextArea();

        areaResultado.setEditable(false);

        areaResultado.setFont(
                new Font("Consolas", Font.PLAIN, 14)
        );

        areaResultado.setBackground(
                new Color(248, 250, 252)
        );

        JScrollPane scroll =
                new JScrollPane(areaResultado);

        scroll.setBounds(
                40,
                360,
                620,
                130
        );

        card.add(scroll);

        btnGerar.addActionListener(
                e -> gerarProposta()
        );

        setVisible(true);
    }

    private JLabel criarLabel(String texto) {

        JLabel label = new JLabel(texto);

        label.setForeground(Color.WHITE);

        label.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        return label;
    }

    private JTextField criarCampo() {

        JTextField campo = new JTextField();

        campo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        return campo;
    }

    private void gerarProposta() {

        try {

            DecimalFormat df =
                    new DecimalFormat(
                            "#,##0.00",
                            new DecimalFormatSymbols(
                                    new Locale("pt", "BR")
                            )
                    );

            String nome =
                    campoNome.getText();

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

            double rendaMinima =
                    parcela * 3;

            areaResultado.setText(

                    "PROPOSTA DE FINANCIAMENTO\n\n" +

                            "Cliente: "
                            + nome +

                            "\n\nValor do Imóvel: R$ "
                            + df.format(valorImovel) +

                            "\nEntrada: R$ "
                            + df.format(entrada) +

                            "\n\nValor Financiado: R$ "
                            + df.format(valorFinanciado) +

                            "\n\nParcela Estimada: R$ "
                            + df.format(parcela) +

                            "\n\nRenda Recomendada: R$ "
                            + df.format(rendaMinima)
            );

            // Salvar no historico
            try {
                String expressao = "Cliente=" + nome
                        + " | Imóvel=R$" + df.format(valorImovel)
                        + " | Entrada=R$" + df.format(entrada)
                        + " | Juros=" + (juros * 100) + "% a.m."
                        + " | Prazo=" + prazo + " meses";
                String resumo = "Financiado=R$" + df.format(valorFinanciado)
                        + " | Parcela=R$" + df.format(parcela)
                        + " | Renda Recomendada=R$" + df.format(rendaMinima);

                Historico h = new Historico(usuarioId, "Proposta", expressao, resumo);
                new HistoricoDAO().salvar(h);
            } catch (Exception ex) {
                System.out.println("Erro ao salvar historico: " + ex.getMessage());
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos corretamente!"
            );
        }
    }
}