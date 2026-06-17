package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.model.Historico;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaComparadorPriceSAC extends JFrame {

    private JTextField campoValor;
    private JTextField campoEntrada;
    private JTextField campoJuros;
    private JTextField campoPrazo;

    private JTable tabelaPrice;
    private JTable tabelaSAC;
    private DefaultTableModel modeloPrice;
    private DefaultTableModel modeloSAC;

    private JLabel lblResumoPrice;
    private JLabel lblResumoSAC;
    private JLabel lblEconomia;

    private JPanel painelPrincipal;
    private boolean modoEscuro = true;

    private String nomeUsuario;
    private int usuarioId;

    private static final Color COR_PRICE = new Color(29, 78, 216);
    private static final Color COR_SAC = new Color(16, 185, 129);
    private static final Color FUNDO_ESCURO = new Color(15, 23, 42);
    private static final Color FUNDO_CLARO = new Color(248, 250, 252);
    private static final Color TEXTO_SECUNDARIO_ESCURO = new Color(203, 213, 225);
    private static final Color TEXTO_SECUNDARIO_CLARO = new Color(71, 85, 105);

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public TelaComparadorPriceSAC(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("Comparador Price x SAC");
        setSize(1100, 740);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBounds(0, 0, 1100, 740);
        add(painelPrincipal);

        int y = 0;

        // ========== HEADER ==========
        y = criarHeader(y);

        // ========== CAMPOS ==========
        y = criarCampos(y);

        // ========== TABELAS ==========
        y = criarTabelas(y);

        // ========== RESUMO ==========
        criarResumo(y);

        aplicarTemaEscuro();
        setVisible(true);
    }

    private int criarHeader(int y) {
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, y, 1100, 70);
        header.setBackground(FUNDO_ESCURO);

        JLabel titulo = new JLabel("Comparador Price x SAC", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 1100, 30);
        header.add(titulo);

        JLabel subtitulo = new JLabel("Compare os sistemas de amortização lado a lado", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        subtitulo.setBounds(0, 40, 1100, 20);
        header.add(subtitulo);

        RoundedButton btnTema = new RoundedButton("Alternar Tema");
        btnTema.setBounds(950, 15, 130, 40);
        btnTema.setFont(new Font("Arial", Font.BOLD, 12));
        btnTema.addActionListener(e -> alternarTema());
        header.add(btnTema);

        painelPrincipal.add(header);
        return y + 70;
    }

    private int criarCampos(int y) {
        int xBase = 30;

        JLabel lblValor = new JLabel("Valor do Imóvel (R$):");
        lblValor.setBounds(xBase, y + 10, 140, 25);
        painelPrincipal.add(lblValor);

        campoValor = new JTextField();
        campoValor.setBounds(xBase + 145, y + 10, 140, 28);
        painelPrincipal.add(campoValor);

        JLabel lblEntrada = new JLabel("Entrada (R$):");
        lblEntrada.setBounds(xBase + 300, y + 10, 100, 25);
        painelPrincipal.add(lblEntrada);

        campoEntrada = new JTextField();
        campoEntrada.setBounds(xBase + 395, y + 10, 120, 28);
        painelPrincipal.add(campoEntrada);

        JLabel lblJuros = new JLabel("Taxa de Juros (% a.m.):");
        lblJuros.setBounds(xBase + 530, y + 10, 150, 25);
        painelPrincipal.add(lblJuros);

        campoJuros = new JTextField();
        campoJuros.setBounds(xBase + 680, y + 10, 80, 28);
        painelPrincipal.add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");
        lblPrazo.setBounds(xBase + 775, y + 10, 100, 25);
        painelPrincipal.add(lblPrazo);

        campoPrazo = new JTextField();
        campoPrazo.setBounds(xBase + 870, y + 10, 70, 28);
        painelPrincipal.add(campoPrazo);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(COR_PRICE);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBounds(xBase + 960, y + 7, 110, 34);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(e -> calcular());
        painelPrincipal.add(btnCalcular);

        return y + 50;
    }

    private int criarTabelas(int y) {
        String[] colunas = {"Mês", "Parcela (R$)", "Amortização (R$)", "Juros (R$)", "Saldo Devedor (R$)"};

        // --- TABELA PRICE ---
        modeloPrice = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPrice = new JTable(modeloPrice);
        configurarTabela(tabelaPrice, COR_PRICE);
        JScrollPane scrollPrice = new JScrollPane(tabelaPrice);
        scrollPrice.setBounds(20, y, 520, 380);
        painelPrincipal.add(scrollPrice);

        // --- TABELA SAC ---
        modeloSAC = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaSAC = new JTable(modeloSAC);
        configurarTabela(tabelaSAC, COR_SAC);
        JScrollPane scrollSAC = new JScrollPane(tabelaSAC);
        scrollSAC.setBounds(560, y, 520, 380);
        painelPrincipal.add(scrollSAC);

        return y + 390;
    }

    private void configurarTabela(JTable tabela, Color corHeader) {
        JTableHeader header = tabela.getTableHeader();
        header.setBackground(corHeader);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 11));

        tabela.setFont(new Font("Arial", Font.PLAIN, 11));
        tabela.setRowHeight(20);
        tabela.setFillsViewportHeight(true);
        tabela.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void criarResumo(int y) {
        // LABELS DE RESUMO
        lblResumoPrice = new JLabel();
        lblResumoPrice.setBounds(20, y + 10, 520, 60);
        lblResumoPrice.setFont(new Font("Arial", Font.BOLD, 12));
        lblResumoPrice.setVerticalAlignment(SwingConstants.TOP);
        painelPrincipal.add(lblResumoPrice);

        lblResumoSAC = new JLabel();
        lblResumoSAC.setBounds(560, y + 10, 520, 60);
        lblResumoSAC.setFont(new Font("Arial", Font.BOLD, 12));
        lblResumoSAC.setVerticalAlignment(SwingConstants.TOP);
        painelPrincipal.add(lblResumoSAC);

        lblEconomia = new JLabel("", SwingConstants.CENTER);
        lblEconomia.setBounds(20, y + 80, 1060, 30);
        lblEconomia.setFont(new Font("Arial", Font.BOLD, 14));
        lblEconomia.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(lblEconomia);

        // RODAPÉ
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 690, 1100, 50);
        footer.setBackground(FUNDO_ESCURO);

        JLabel lblFooter = new JLabel("Logado como: " + nomeUsuario + " \u00B7 ImobiCalc Pro");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblFooter.setBounds(20, 15, 350, 20);
        footer.add(lblFooter);

        JLabel lblStatus = new JLabel("\u25CF MySQL conectado");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(new Color(34, 197, 94));
        lblStatus.setBounds(900, 15, 180, 20);
        footer.add(lblStatus);

        painelPrincipal.add(footer);
    }

    private void calcular() {
        try {
            double valorImovel = Double.parseDouble(campoValor.getText().replace(",", ".").replace("R$", "").trim());
            double entrada = Double.parseDouble(campoEntrada.getText().replace(",", ".").replace("R$", "").trim());
            double taxa = Double.parseDouble(campoJuros.getText().replace(",", ".").trim());
            int prazo = Integer.parseInt(campoPrazo.getText().trim());

            double financiado = valorImovel - entrada;
            double i = taxa / 100.0;

            if (financiado <= 0 || i < 0 || prazo <= 0) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos com valores válidos!");
                return;
            }

            modeloPrice.setRowCount(0);
            modeloSAC.setRowCount(0);

            // ====== PRICE ======
            double parcelaPrice = (financiado * i) / (1 - Math.pow(1 + i, -prazo));
            double saldoPrice = financiado;
            double totalJurosPrice = 0;
            double totalPagoPrice = 0;

            for (int mes = 1; mes <= prazo; mes++) {
                double jurosM = saldoPrice * i;
                double amortM = parcelaPrice - jurosM;
                saldoPrice -= amortM;
                if (saldoPrice < 0) saldoPrice = 0;
                totalJurosPrice += jurosM;
                totalPagoPrice += parcelaPrice;

                modeloPrice.addRow(new Object[]{
                        mes,
                        FMT.format(parcelaPrice),
                        FMT.format(amortM),
                        FMT.format(jurosM),
                        FMT.format(saldoPrice)
                });
            }

            // ====== SAC ======
            double amortSAC = financiado / prazo;
            double saldoSAC = financiado;
            double totalJurosSAC = 0;
            double primeiraParcelaSAC = 0;
            double ultimaParcelaSAC = 0;

            for (int mes = 1; mes <= prazo; mes++) {
                double jurosM = saldoSAC * i;
                double parcelaM = amortSAC + jurosM;
                saldoSAC -= amortSAC;
                if (saldoSAC < 0) saldoSAC = 0;
                totalJurosSAC += jurosM;

                if (mes == 1) primeiraParcelaSAC = parcelaM;
                if (mes == prazo) ultimaParcelaSAC = parcelaM;

                modeloSAC.addRow(new Object[]{
                        mes,
                        FMT.format(parcelaM),
                        FMT.format(amortSAC),
                        FMT.format(jurosM),
                        FMT.format(saldoSAC)
                });
            }

            double economia = totalJurosPrice - totalJurosSAC;

            // Resumo
            lblResumoPrice.setText("<html><b style='color:#1D4ED8;'>PRICE</b><br>" +
                    "1\u00AA parcela: " + FMT.format(parcelaPrice) +
                    " | Total pago: " + FMT.format(totalPagoPrice) +
                    " | Total juros: " + FMT.format(totalJurosPrice) + "</html>");

            lblResumoSAC.setText("<html><b style='color:#10B981;'>SAC</b><br>" +
                    "1\u00AA parcela: " + FMT.format(primeiraParcelaSAC) +
                    " | \u00DAltima parcela: " + FMT.format(ultimaParcelaSAC) +
                    " | Total juros: " + FMT.format(totalJurosSAC) + "</html>");

            if (economia > 0) {
                lblEconomia.setText("O SAC economiza " + FMT.format(economia) + " em juros em rela\u00E7\u00E3o \u00E0 Price");
                lblEconomia.setForeground(new Color(34, 197, 94));
            } else {
                lblEconomia.setText("Price e SAC t\u00EAm juros equivalentes para este cen\u00E1rio");
                lblEconomia.setForeground(TEXTO_SECUNDARIO_ESCURO);
            }

            // Salvar no banco
            String resultado = "PRICE: 1\u00AA parcela=" + FMT.format(parcelaPrice)
                    + ", Total pago=" + FMT.format(totalPagoPrice)
                    + ", Total juros=" + FMT.format(totalJurosPrice)
                    + " | SAC: 1\u00AA parcela=" + FMT.format(primeiraParcelaSAC)
                    + ", \u00DAltima parcela=" + FMT.format(ultimaParcelaSAC)
                    + ", Total juros=" + FMT.format(totalJurosSAC)
                    + " | Economia SAC=" + FMT.format(economia);

            String expressao = "Imóvel=" + FMT.format(valorImovel)
                    + " | Entrada=" + FMT.format(entrada)
                    + " | Juros=" + (taxa * 100) + "% a.m."
                    + " | Prazo=" + prazo + " meses";

            Historico h = new Historico(usuarioId, "Comparador", expressao, resultado);
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
        if (modoEscuro) {
            aplicarTemaEscuro();
        } else {
            aplicarTemaClaro();
        }
    }

    private void aplicarTemaEscuro() {
        painelPrincipal.setBackground(FUNDO_ESCURO);
        Color labelCor = TEXTO_SECUNDARIO_ESCURO;
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(labelCor);
            }
        }
        if (lblResumoPrice != null) lblResumoPrice.setForeground(Color.WHITE);
        if (lblResumoSAC != null) lblResumoSAC.setForeground(Color.WHITE);
    }

    private void aplicarTemaClaro() {
        painelPrincipal.setBackground(FUNDO_CLARO);
        Color labelCor = TEXTO_SECUNDARIO_CLARO;
        for (Component c : painelPrincipal.getComponents()) {
            if (c instanceof JLabel && c != lblResumoPrice && c != lblResumoSAC && c != lblEconomia) {
                c.setForeground(labelCor);
            }
        }
        if (lblEconomia != null) lblEconomia.setForeground(new Color(34, 197, 94));
    }
}