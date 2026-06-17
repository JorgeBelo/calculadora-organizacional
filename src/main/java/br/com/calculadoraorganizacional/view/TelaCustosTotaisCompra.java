package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TelaCustosTotaisCompra extends JFrame {

    private JTextField campoValorImovel;
    private JTextField campoEntrada;
    private JTextField campoTaxaITBI;
    private JTextField campoEscritura;
    private JTextField campoRegistro;
    private JTextField campoAvaliacao;
    private JTextField campoOutros;

    private JLabel lblITBI;
    private JLabel lblEscritura;
    private JLabel lblRegistro;
    private JLabel lblAvaliacao;
    private JLabel lblOutros;
    private JLabel lblTotalCustos;
    private JLabel lblTotalGeral;
    private JLabel lblEntradaExibida;
    private JLabel lblFinanciado;

    private JPanel painelPrincipal;
    private boolean modoEscuro = true;

    private String nomeUsuario;
    private int usuarioId;

    private static final Color FUNDO_ESCURO = new Color(15, 23, 42);
    private static final Color FUNDO_CLARO = new Color(248, 250, 252);
    private static final Color CARD_ESCURO = new Color(30, 41, 59);
    private static final Color CARD_CLARO = Color.WHITE;
    private static final Color TEXTO_SECUNDARIO_ESCURO = new Color(203, 213, 225);
    private static final Color TEXTO_SECUNDARIO_CLARO = new Color(71, 85, 105);
    private static final Color COR_AZUL = new Color(29, 78, 216);
    private static final Color VERDE = new Color(34, 197, 94);

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final NumberFormat FMT_PCT = NumberFormat.getPercentInstance(new Locale("pt", "BR"));

    public TelaCustosTotaisCompra(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("Custos Totais de Compra");
        setSize(730, 660);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBounds(0, 0, 730, 660);
        add(painelPrincipal);

        int y = 0;

        // HEADER
        y = criarHeader(y);

        // CAMPOS
        y = criarCampos(y);

        // RESULTADOS
        y = criarResultados(y);

        // RODAPÉ
        criarFooter();

        aplicarTemaEscuro();
        setVisible(true);
    }

    private int criarHeader(int y) {
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, y, 730, 60);
        header.setBackground(FUNDO_ESCURO);

        JLabel titulo = new JLabel("Custos Totais de Compra", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 8, 730, 28);
        header.add(titulo);

        JLabel subtitulo = new JLabel("Simule todos os custos envolvidos na aquisição do imóvel", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        subtitulo.setBounds(0, 34, 730, 20);
        header.add(subtitulo);

        RoundedButton btnTema = new RoundedButton("Alternar Tema");
        btnTema.setBounds(580, 10, 130, 40);
        btnTema.setFont(new Font("Arial", Font.BOLD, 12));
        btnTema.addActionListener(e -> alternarTema());
        header.add(btnTema);

        painelPrincipal.add(header);
        return y + 60;
    }

    private int criarCampos(int y) {
        int xLabel = 30;
        int xCampo = 160;
        int larguraCampo = 120;
        int espaco = 35;
    int yAtual = y + 15;

        // Valor do Imóvel
        JLabel lblValor = new JLabel("Valor do Imóvel (R$):");
        lblValor.setBounds(xLabel, yAtual, 150, 25);
        painelPrincipal.add(lblValor);
        campoValorImovel = new JTextField();
        campoValorImovel.setBounds(xCampo, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoValorImovel);

        // Entrada
        JLabel lblEntrada = new JLabel("Entrada (R$):");
        lblEntrada.setBounds(xLabel + 300, yAtual, 120, 25);
        painelPrincipal.add(lblEntrada);
        campoEntrada = new JTextField();
        campoEntrada.setBounds(xLabel + 420, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoEntrada);

        yAtual += espaco + 10;

        // ITBI
        JLabel lblITBILabel = new JLabel("Taxa ITBI (%):");
        lblITBILabel.setBounds(xLabel, yAtual, 150, 25);
        painelPrincipal.add(lblITBILabel);
        campoTaxaITBI = new JTextField("3");
        campoTaxaITBI.setBounds(xCampo, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoTaxaITBI);

        // Escritura
        JLabel lblEscrituraLabel = new JLabel("Escritura (R$):");
        lblEscrituraLabel.setBounds(xLabel + 300, yAtual, 120, 25);
        painelPrincipal.add(lblEscrituraLabel);
        campoEscritura = new JTextField("2000");
        campoEscritura.setBounds(xLabel + 420, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoEscritura);

        yAtual += espaco + 10;

        // Registro
        JLabel lblRegistroLabel = new JLabel("Registro (R$):");
        lblRegistroLabel.setBounds(xLabel, yAtual, 150, 25);
        painelPrincipal.add(lblRegistroLabel);
        campoRegistro = new JTextField("1500");
        campoRegistro.setBounds(xCampo, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoRegistro);

        // Avaliação Bancária
        JLabel lblAvaliacaoLabel = new JLabel("Avaliação Bancária (R$):");
        lblAvaliacaoLabel.setBounds(xLabel + 300, yAtual, 150, 25);
        painelPrincipal.add(lblAvaliacaoLabel);
        campoAvaliacao = new JTextField("800");
        campoAvaliacao.setBounds(xLabel + 460, yAtual, larguraCampo - 10, 28);
        painelPrincipal.add(campoAvaliacao);

        yAtual += espaco + 10;

        // Outros
        JLabel lblOutrosLabel = new JLabel("Outros Custos (R$):");
        lblOutrosLabel.setBounds(xLabel, yAtual, 150, 25);
        painelPrincipal.add(lblOutrosLabel);
        campoOutros = new JTextField("0");
        campoOutros.setBounds(xCampo, yAtual, larguraCampo, 28);
        painelPrincipal.add(campoOutros);

        // Botão Calcular
        JButton btnCalcular = new JButton("Calcular Custos");
        btnCalcular.setBackground(COR_AZUL);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBounds(480, yAtual - 5, 200, 36);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(e -> calcularCustos());
        painelPrincipal.add(btnCalcular);

        // Botão Exportar
        JButton btnExportar = new JButton("Exportar .txt");
        btnExportar.setBounds(480, yAtual + 45, 200, 30);
        btnExportar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(e -> exportarTxt());
        painelPrincipal.add(btnExportar);

        return yAtual + 90;
    }

    private int criarResultados(int y) {
        // Cards de resultado
        int cardWidth = 150;
        int cardHeight = 70;
        int espacoCards = 15;
        int xBase = 25;

        lblITBI = criarCard(xBase, y, cardWidth, cardHeight, "ITBI", "R$ 0,00");
        lblEscritura = criarCard(xBase + cardWidth + espacoCards, y, cardWidth, cardHeight, "Escritura", "R$ 0,00");
        lblRegistro = criarCard(xBase + 2 * (cardWidth + espacoCards), y, cardWidth, cardHeight, "Registro", "R$ 0,00");
        lblAvaliacao = criarCard(xBase + 3 * (cardWidth + espacoCards), y, cardWidth, cardHeight, "Avaliação", "R$ 0,00");
        lblOutros = criarCard(xBase + 4 * (cardWidth + espacoCards), y, cardWidth, cardHeight, "Outros", "R$ 0,00");

        y += cardHeight + 15;

        // Totais
        lblEntradaExibida = criarCard(xBase, y, 200, 60, "Entrada", "R$ 0,00");
        lblFinanciado = criarCard(xBase + 215, y, 200, 60, "Valor Financiado", "R$ 0,00");
        lblTotalCustos = criarCard(xBase + 430, y, 200, 60, "Custos Totais", "R$ 0,00");
        lblTotalGeral = criarCard(xBase + 430, y + 75, 200, 60, "Total Geral", "R$ 0,00");

        return y + 150;
    }

    private JLabel criarCard(int x, int y, int w, int h, String titulo, String valor) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(x, y, w, h);
        card.setBackground(CARD_ESCURO);
        card.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85), 1));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblTitulo.setBounds(0, 6, w, 18);
        card.add(lblTitulo);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 16));
        lblValor.setForeground(Color.WHITE);
        lblValor.setBounds(0, 26, w, 30);
        card.add(lblValor);

        painelPrincipal.add(card);
        return lblValor;
    }

    private void calcularCustos() {
        try {
            double valorImovel = Double.parseDouble(campoValorImovel.getText().replace(",", ".").replace("R$", "").trim());
            double entrada = Double.parseDouble(campoEntrada.getText().replace(",", ".").replace("R$", "").trim());
            double taxaITBI = Double.parseDouble(campoTaxaITBI.getText().replace(",", ".").trim()) / 100.0;
            double escritura = Double.parseDouble(campoEscritura.getText().replace(",", ".").replace("R$", "").trim());
            double registro = Double.parseDouble(campoRegistro.getText().replace(",", ".").replace("R$", "").trim());
            double avaliacao = Double.parseDouble(campoAvaliacao.getText().replace(",", ".").replace("R$", "").trim());
            double outros = Double.parseDouble(campoOutros.getText().replace(",", ".").replace("R$", "").trim());

            if (valorImovel <= 0 || entrada < 0) {
                JOptionPane.showMessageDialog(this, "Preencha o valor do imóvel e a entrada corretamente!");
                return;
            }

            double itbi = valorImovel * taxaITBI;
            double custosExtra = escritura + registro + avaliacao + outros;
            double custosTotais = itbi + custosExtra;
            double valorFinanciado = Math.max(0, valorImovel - entrada);
            double totalGeral = entrada + valorFinanciado + custosTotais;

            lblITBI.setText(FMT.format(itbi));
            lblEscritura.setText(FMT.format(escritura));
            lblRegistro.setText(FMT.format(registro));
            lblAvaliacao.setText(FMT.format(avaliacao));
            lblOutros.setText(FMT.format(outros));
            lblEntradaExibida.setText(FMT.format(entrada));
            lblFinanciado.setText(FMT.format(valorFinanciado));
            lblTotalCustos.setText(FMT.format(custosTotais));
            lblTotalGeral.setText(FMT.format(totalGeral));

            // Salvar no banco
            String expressao = "Imóvel=" + FMT.format(valorImovel)
                    + " | Entrada=" + FMT.format(entrada)
                    + " | ITBI=" + FMT.format(itbi)
                    + " | Escritura=" + FMT.format(escritura)
                    + " | Registro=" + FMT.format(registro);
            String resumo = "CustosExtra=" + FMT.format(custosExtra)
                    + " | TotalCustos=" + FMT.format(custosTotais)
                    + " | TotalGeral=" + FMT.format(totalGeral);

            Historico h = new Historico(usuarioId, "Custos Compra", expressao, resumo);
            new HistoricoDAO().salvar(h);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos com valores válidos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void exportarTxt() {
        if (lblTotalCustos.getText().equals("R$ 0,00")) {
            JOptionPane.showMessageDialog(this, "Calcule os custos primeiro!");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("custos_compra_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(chooser.getSelectedFile()))) {
                String valor = campoValorImovel.getText();
                String entrada = campoEntrada.getText();

                pw.println("CUSTOS TOTAIS DE COMPRA - ImobiCalc Pro");
                pw.println("Gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
                pw.println("----------------------------------------");
                pw.println("Valor do Imóvel: R$" + valor);
                pw.println("Entrada: R$" + entrada);
                pw.println("----------------------------------------");
                pw.println("ITBI:           " + lblITBI.getText());
                pw.println("Escritura:      " + lblEscritura.getText());
                pw.println("Registro:       " + lblRegistro.getText());
                pw.println("Avaliação:      " + lblAvaliacao.getText());
                pw.println("Outros:         " + lblOutros.getText());
                pw.println("----------------------------------------");
                pw.println("Custos Totais:  " + lblTotalCustos.getText());
                pw.println("Entrada:        " + lblEntradaExibida.getText());
                pw.println("Valor Financiado: " + lblFinanciado.getText());
                pw.println("Total Geral:    " + lblTotalGeral.getText());
                pw.println("----------------------------------------");
                pw.println("ImobiCalc Pro | SENAC - Programador de Sistemas");

                JOptionPane.showMessageDialog(this, "Arquivo exportado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void criarFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 610, 730, 50);
        footer.setBackground(FUNDO_ESCURO);

        JLabel lblFooter = new JLabel("Logado como: " + nomeUsuario + " \u00B7 ImobiCalc Pro");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblFooter.setBounds(20, 15, 350, 20);
        footer.add(lblFooter);

        JLabel lblStatus = new JLabel("\u25CF MySQL conectado");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(new Color(34, 197, 94));
        lblStatus.setBounds(530, 15, 180, 20);
        footer.add(lblStatus);

        painelPrincipal.add(footer);
    }

    private void alternarTema() {
        modoEscuro = !modoEscuro;
        if (modoEscuro) aplicarTemaEscuro();
        else aplicarTemaClaro();
    }

    private void aplicarTemaEscuro() {
        painelPrincipal.setBackground(FUNDO_ESCURO);
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel) c.setForeground(TEXTO_SECUNDARIO_ESCURO);
        }
    }

    private void aplicarTemaClaro() {
        painelPrincipal.setBackground(FUNDO_CLARO);
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel) c.setForeground(TEXTO_SECUNDARIO_CLARO);
        }
    }
}