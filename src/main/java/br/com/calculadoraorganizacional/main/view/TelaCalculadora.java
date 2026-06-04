package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import java.awt.*;

public class TelaCalculadora extends JFrame {

    private JTextField visor;

    private double numero1;
    private double numero2;

    private String operacao;

    private int usuarioId;

    public TelaCalculadora(String nomeUsuario, int usuarioId) {

        this.usuarioId = usuarioId;

        setTitle("Calculadora Organizacional");

        setSize(520, 650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel labelUsuario = new JLabel("Bem-vindo, " + nomeUsuario);

        labelUsuario.setBounds(20, 20, 300, 30);

        labelUsuario.setFont(new Font("Arial", Font.BOLD, 16));

        add(labelUsuario);

        visor = new JTextField();

        visor.setBounds(50, 70, 400, 60);

        visor.setFont(new Font("Arial", Font.BOLD, 24));

        visor.setHorizontalAlignment(SwingConstants.RIGHT);

        add(visor);

        // LINHA 1

        JButton botao7 = criarBotao("7", 50, 160);
        JButton botao8 = criarBotao("8", 150, 160);
        JButton botao9 = criarBotao("9", 250, 160);
        JButton botaoDiv = criarBotao("/", 350, 160);

        // LINHA 2

        JButton botao4 = criarBotao("4", 50, 240);
        JButton botao5 = criarBotao("5", 150, 240);
        JButton botao6 = criarBotao("6", 250, 240);
        JButton botaoMult = criarBotao("*", 350, 240);

        // LINHA 3

        JButton botao1 = criarBotao("1", 50, 320);
        JButton botao2 = criarBotao("2", 150, 320);
        JButton botao3 = criarBotao("3", 250, 320);
        JButton botaoSub = criarBotao("-", 350, 320);

        // LINHA 4

        JButton botao0 = criarBotao("0", 50, 400);
        JButton botaoPorcentagem = criarBotao("%", 150, 400);
        JButton botaoRaiz = criarBotao("√", 250, 400);
        JButton botaoSoma = criarBotao("+", 350, 400);

        // LINHA 5

        JButton botaoLimpar = criarBotao("C", 50, 480);

        JButton botaoApagar = criarBotao("←", 150, 480);

        JButton botaoIgual = criarBotao("=", 250, 480);

        JButton botaoHistorico = criarBotao("Hist", 350, 480);

        // BOTÕES NÚMEROS

        adicionarNumero(botao0, "0");
        adicionarNumero(botao1, "1");
        adicionarNumero(botao2, "2");
        adicionarNumero(botao3, "3");
        adicionarNumero(botao4, "4");
        adicionarNumero(botao5, "5");
        adicionarNumero(botao6, "6");
        adicionarNumero(botao7, "7");
        adicionarNumero(botao8, "8");
        adicionarNumero(botao9, "9");

        // OPERAÇÕES

        botaoSoma.addActionListener(e -> definirOperacao("+"));

        botaoSub.addActionListener(e -> definirOperacao("-"));

        botaoMult.addActionListener(e -> definirOperacao("*"));

        botaoDiv.addActionListener(e -> definirOperacao("/"));

        botaoPorcentagem.addActionListener(e -> definirOperacao("%"));

        // RAIZ QUADRADA

        botaoRaiz.addActionListener(e -> {

            try {

                double valor = Double.parseDouble(visor.getText());

                double resultado = Math.sqrt(valor);

                visor.setText(String.valueOf(resultado));

                // SALVAR HISTÓRICO

                Historico historico = new Historico();

                historico.setUsuarioId(usuarioId);

                historico.setExpressao("√ " + valor);

                historico.setResultado(String.valueOf(resultado));

                HistoricoDAO historicoDAO = new HistoricoDAO();

                historicoDAO.salvarHistorico(historico);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao calcular raiz!"
                );
            }
        });

        // LIMPAR TUDO

        botaoLimpar.addActionListener(e -> visor.setText(""));

        // APAGAR ÚLTIMO CARACTERE

        botaoApagar.addActionListener(e -> {

            String texto = visor.getText();

            if (!texto.isEmpty()) {

                visor.setText(
                        texto.substring(0, texto.length() - 1)
                );
            }
        });

        // HISTÓRICO

        botaoHistorico.addActionListener(e ->
                new TelaHistorico(usuarioId));

        // IGUAL

        botaoIgual.addActionListener(e -> calcularResultado());

        setVisible(true);
    }

    // CRIAR BOTÃO

    private JButton criarBotao(String texto, int x, int y) {

        JButton botao = new JButton(texto);

        botao.setBounds(x, y, 80, 60);

        botao.setFont(new Font("Arial", Font.BOLD, 20));

        add(botao);

        return botao;
    }

    // ADICIONAR NÚMERO

    private void adicionarNumero(JButton botao, String numero) {

        botao.addActionListener(e ->
                visor.setText(visor.getText() + numero));
    }

    // DEFINIR OPERAÇÃO

    private void definirOperacao(String op) {

        try {

            numero1 = Double.parseDouble(visor.getText());

            operacao = op;

            visor.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite um número primeiro!"
            );
        }
    }

    // CALCULAR RESULTADO

    private void calcularResultado() {

        try {

            numero2 = Double.parseDouble(visor.getText());

            double resultado = 0;

            switch (operacao) {

                case "+":
                    resultado = numero1 + numero2;
                    break;

                case "-":
                    resultado = numero1 - numero2;
                    break;

                case "*":
                    resultado = numero1 * numero2;
                    break;

                case "/":

                    if (numero2 == 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Não é possível dividir por zero!"
                        );

                        return;
                    }

                    resultado = numero1 / numero2;
                    break;

                case "%":
                    resultado = (numero1 * numero2) / 100;
                    break;
            }

            visor.setText(String.valueOf(resultado));

            // EXPRESSÃO

            String expressao =
                    numero1 + " " + operacao + " " + numero2;

            // HISTÓRICO

            Historico historico = new Historico();

            historico.setUsuarioId(usuarioId);

            historico.setExpressao(expressao);

            historico.setResultado(String.valueOf(resultado));

            // SALVAR MYSQL

            HistoricoDAO historicoDAO = new HistoricoDAO();

            historicoDAO.salvarHistorico(historico);

            System.out.println("Histórico salvo!");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao calcular!"
            );

            e.printStackTrace();
        }
    }
}