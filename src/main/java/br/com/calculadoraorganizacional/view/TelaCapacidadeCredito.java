package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaCapacidadeCredito extends JFrame {

    private JTextField campoRenda;
    private JTextField campoJuros;
    private JTextField campoPrazo;
    private JComboBox<String> comboComprometimento;

    private JLabel lblParcelaMax;
    private JLabel lblValorFinanciavel;
    private JLabel lblValorImovel;
    private JLabel lblEntrada;
    private JTextArea areaExplicacao;

    private JPanel painelPrincipal;
    private boolean modoEscuro = true;

    private String nomeUsuario;
    private int usuarioId;

    private static final Color FUNDO_ESCURO = new Color(15, 23, 42);
    private static final Color FUNDO_CLARO = new Color(248, 250, 252);
    private static final Color TEXTO_SECUNDARIO_ESCURO = new Color(203, 213, 225);
    private static final Color TEXTO_SECUNDARIO_CLARO = new Color(71, 85, 105);
    private static final Color COR_AZUL = new Color(29, 78, 216);
    private static final Color CARD_BG = new Color(30, 41, 59);
    private static final Color CARD_BORDER = new Color(51, 65, 85);

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public TelaCapacidadeCredito(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("Capacidade de Cr\u00E9dito");
        setSize(680, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBounds(0, 0, 680, 560);
        add(painelPrincipal);

        int y = 0;

        // HEADER
        y = criarHeader(y);

        // CAMPOS
        y = criarCampos(y);

        // CARDS DE RESULTADO
        y = criarCards(y);

        // TEXTO EXPLICATIVO
        criarExplicacao(y);

        // RODAPÉ
        criarFooter();

        aplicarTemaEscuro();
        setVisible(true);
    }

    private int criarHeader(int y) {
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, y, 680, 60);
        header.setBackground(FUNDO_ESCURO);

        JLabel titulo = new JLabel("Capacidade de Cr\u00E9dito", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 8, 680, 28);
        header.add(titulo);

        JLabel subtitulo = new JLabel("Descubra quanto voc\u00EA pode financiar com sua renda", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        subtitulo.setBounds(0, 34, 680, 20);
        header.add(subtitulo);

        RoundedButton btnTema = new RoundedButton("Alternar Tema");
        btnTema.setBounds(530, 10, 130, 40);
        btnTema.setFont(new Font("Arial", Font.BOLD, 12));
        btnTema.addActionListener(e -> alternarTema());
        header.add(btnTema);

        painelPrincipal.add(header);
        return y + 60;
    }

    private int criarCampos(int y) {
        int xBase = 30;

        JLabel lblRenda = new JLabel("Renda Mensal Bruta (R$):");
        lblRenda.setBounds(xBase, y + 12, 170, 25);
        painelPrincipal.add(lblRenda);

        campoRenda = new JTextField();
        campoRenda.setBounds(xBase + 175, y + 12, 140, 28);
        painelPrincipal.add(campoRenda);

        JLabel lblJuros = new JLabel("Taxa (% a.m.):");
        lblJuros.setBounds(xBase + 330, y + 12, 100, 25);
        painelPrincipal.add(lblJuros);

        campoJuros = new JTextField();
        campoJuros.setBounds(xBase + 420, y + 12, 80, 28);
        painelPrincipal.add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");
        lblPrazo.setBounds(xBase + 515, y + 12, 100, 25);
        painelPrincipal.add(lblPrazo);

        campoPrazo = new JTextField();
        campoPrazo.setBounds(xBase + 600, y + 12, 60, 28);
        painelPrincipal.add(campoPrazo);

        JLabel lblComp = new JLabel("Comprometimento:");
        lblComp.setBounds(xBase, y + 55, 130, 25);
        painelPrincipal.add(lblComp);

        comboComprometimento = new JComboBox<>(new String[]{"30%", "25%", "20%"});
        comboComprometimento.setBounds(xBase + 130, y + 55, 80, 28);
        painelPrincipal.add(comboComprometimento);

        JButton btnCalcular = new JButton("Calcular Capacidade");
        btnCalcular.setBackground(COR_AZUL);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBounds(250, y + 55, 200, 34);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(e -> calcular());
        painelPrincipal.add(btnCalcular);

        return y + 110;
    }

    private int criarCards(int y) {
        // Grid 2x2 de cards
        int cardW = 290;
        int cardH = 65;
        int gapX = 30;
        int gapY = 15;
        int startX = 35;

        lblParcelaMax = criarCard("Parcela m\u00E1xima/m\u00EAs", startX, y, cardW, cardH);
        lblValorFinanciavel = criarCard("Valor m\u00E1ximo financi\u00E1vel", startX + cardW + gapX, y, cardW, cardH);
        lblValorImovel = criarCard("Valor m\u00E1ximo do im\u00F3vel", startX, y + cardH + gapY, cardW, cardH);
        lblEntrada = criarCard("Entrada necess\u00E1ria (20%)", startX + cardW + gapX, y + cardH + gapY, cardW, cardH);

        return y + cardH * 2 + gapY * 2 + 10;
    }

    private JLabel criarCard(String titulo, int x, int y, int w, int h) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(x, y, w, h);
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblTitulo.setBounds(10, 5, w - 20, 18);
        card.add(lblTitulo);

        JLabel lblValor = new JLabel("R$ 0,00");
        lblValor.setFont(new Font("Arial", Font.BOLD, 18));
        lblValor.setForeground(Color.WHITE);
        lblValor.setBounds(10, 25, w - 20, 30);
        card.add(lblValor);

        painelPrincipal.add(card);

        // Salvar referência pelo título
        if (titulo.startsWith("Parcela")) return lblValor;
        if (titulo.startsWith("Valor m\u00E1ximo financi")) return lblValor;
        if (titulo.startsWith("Valor m\u00E1ximo do im")) return lblValor;
        if (titulo.startsWith("Entrada")) return lblValor;

        return lblValor;
    }

    private void criarExplicacao(int y) {
        areaExplicacao = new JTextArea();
        areaExplicacao.setEditable(false);
        areaExplicacao.setFont(new Font("Monospaced", Font.PLAIN, 11));
        areaExplicacao.setBackground(CARD_BG);
        areaExplicacao.setForeground(TEXTO_SECUNDARIO_ESCURO);
        areaExplicacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        areaExplicacao.setLineWrap(true);
        areaExplicacao.setWrapStyleWord(true);
        areaExplicacao.setBounds(35, y + 10, 610, 80);

        areaExplicacao.setText("Preencha os campos acima e clique em \"Calcular Capacidade\" para ver o resultado.");
        painelPrincipal.add(areaExplicacao);
    }

    private void criarFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 510, 680, 50);
        footer.setBackground(FUNDO_ESCURO);

        JLabel lblFooter = new JLabel("Logado como: " + nomeUsuario + " \u00B7 ImobiCalc Pro");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblFooter.setBounds(20, 15, 350, 20);
        footer.add(lblFooter);

        JLabel lblStatus = new JLabel("\u25CF MySQL conectado");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(new Color(34, 197, 94));
        lblStatus.setBounds(500, 15, 160, 20);
        footer.add(lblStatus);

        painelPrincipal.add(footer);
    }

    private void calcular() {
        try {
            double renda = Double.parseDouble(campoRenda.getText().replace(",", ".").replace("R$", "").trim());
            double taxa = Double.parseDouble(campoJuros.getText().replace(",", ".").trim());
            int prazo = Integer.parseInt(campoPrazo.getText().trim());
            String compStr = (String) comboComprometimento.getSelectedItem();
            double percentual = Double.parseDouble(compStr.replace("%", ""));

            double i = taxa / 100.0;

            if (renda <= 0 || i < 0 || prazo <= 0) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos com valores v\u00E1lidos!");
                return;
            }

            double parcelaMax = renda * (percentual / 100.0);
            double valorFinanciavel = parcelaMax * (1 - Math.pow(1 + i, -prazo)) / i;
            double valorMaxImovel = valorFinanciavel / 0.80;
            double entradaNecessaria = valorMaxImovel * 0.20;

            lblParcelaMax.setText(FMT.format(parcelaMax));
            lblValorFinanciavel.setText(FMT.format(valorFinanciavel));
            lblValorImovel.setText(FMT.format(valorMaxImovel));
            lblEntrada.setText(FMT.format(entradaNecessaria));

            areaExplicacao.setText(
                    "Com renda de " + FMT.format(renda) + " comprometendo " + compStr
                            + " (" + FMT.format(parcelaMax) + "/m\u00EAs), voc\u00EA pode financiar at\u00E9 "
                            + FMT.format(valorFinanciavel) + " em " + prazo + " meses.\n"
                            + "Considerando entrada m\u00EDnima de 20%, o valor m\u00E1ximo do im\u00F3vel \u00E9 "
                            + FMT.format(valorMaxImovel) + "."
            );

            // Salvar no banco
            String expressao = "Renda=" + FMT.format(renda)
                    + " | Comprometimento=" + compStr
                    + " | Juros=" + (taxa * 100) + "% a.m."
                    + " | Prazo=" + prazo + " meses";
            String resumo = "ParcelaMax=" + FMT.format(parcelaMax)
                    + " | ValorFinanciavel=" + FMT.format(valorFinanciavel)
                    + " | ValorMaxImovel=" + FMT.format(valorMaxImovel)
                    + " | EntradaNecessaria=" + FMT.format(entradaNecessaria);

            Historico h = new Historico(usuarioId, "Capacidade", expressao, resumo);
            new HistoricoDAO().salvar(h);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void alternarTema() {
        modoEscuro = !modoEscuro;
        if (modoEscuro) aplicarTemaEscuro();
        else aplicarTemaClaro();
    }

    private void aplicarTemaEscuro() {
        painelPrincipal.setBackground(FUNDO_ESCURO);
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel && !"titulo".equals(c.getName())) {
                c.setForeground(TEXTO_SECUNDARIO_ESCURO);
            }
        }
        areaExplicacao.setBackground(CARD_BG);
        areaExplicacao.setForeground(TEXTO_SECUNDARIO_ESCURO);
    }

    private void aplicarTemaClaro() {
        painelPrincipal.setBackground(FUNDO_CLARO);
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel) c.setForeground(TEXTO_SECUNDARIO_CLARO);
        }
        areaExplicacao.setBackground(new Color(241, 245, 249));
        areaExplicacao.setForeground(TEXTO_SECUNDARIO_CLARO);
    }
}