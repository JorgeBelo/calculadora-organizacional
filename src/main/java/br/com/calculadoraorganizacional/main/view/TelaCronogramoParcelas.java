package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.SimulacaoDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class TelaCronogramoParcelas extends JFrame {

    private JTextField campoValor;
    private JTextField campoJuros;
    private JTextField campoPrazo;
    private JComboBox<String> comboSistema;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JPanel painelPrincipal;
    private boolean modoEscuro = true;

    private String nomeUsuario;
    private int usuarioId;

    private String ultimoResultado;

    private static final Color FUNDO_ESCURO = new Color(15, 23, 42);
    private static final Color FUNDO_CLARO = new Color(248, 250, 252);
    private static final Color COR_HEADER = new Color(15, 23, 42);
    private static final Color TEXTO_SECUNDARIO_ESCURO = new Color(203, 213, 225);
    private static final Color TEXTO_SECUNDARIO_CLARO = new Color(71, 85, 105);
    private static final Color COR_AZUL = new Color(29, 78, 216);

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public TelaCronogramoParcelas(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        setTitle("Cronograma de Parcelas");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBounds(0, 0, 900, 700);
        add(painelPrincipal);

        int y = 0;

        // HEADER
        y = criarHeader(y);

        // CAMPOS
        y = criarCampos(y);

        // TABELA
        y = criarTabela(y);

        // RODAPÉ
        criarFooter();

        aplicarTemaEscuro();
        setVisible(true);
    }

    private int criarHeader(int y) {
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, y, 900, 60);
        header.setBackground(FUNDO_ESCURO);

        JLabel titulo = new JLabel("Cronograma de Parcelas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 8, 900, 28);
        header.add(titulo);

        JLabel subtitulo = new JLabel("Simula\u00E7\u00E3o m\u00EAs a m\u00EAs do financiamento", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        subtitulo.setBounds(0, 34, 900, 20);
        header.add(subtitulo);

        RoundedButton btnTema = new RoundedButton("Alternar Tema");
        btnTema.setBounds(750, 10, 130, 40);
        btnTema.setFont(new Font("Arial", Font.BOLD, 12));
        btnTema.addActionListener(e -> alternarTema());
        header.add(btnTema);

        painelPrincipal.add(header);
        return y + 60;
    }

    private int criarCampos(int y) {
        int xBase = 30;

        JLabel lblValor = new JLabel("Valor Financiado (R$):");
        lblValor.setBounds(xBase, y + 12, 150, 25);
        painelPrincipal.add(lblValor);

        campoValor = new JTextField();
        campoValor.setBounds(xBase + 155, y + 12, 140, 28);
        painelPrincipal.add(campoValor);

        JLabel lblJuros = new JLabel("Taxa (% a.m.):");
        lblJuros.setBounds(xBase + 310, y + 12, 100, 25);
        painelPrincipal.add(lblJuros);

        campoJuros = new JTextField();
        campoJuros.setBounds(xBase + 400, y + 12, 80, 28);
        painelPrincipal.add(campoJuros);

        JLabel lblPrazo = new JLabel("Prazo (meses):");
        lblPrazo.setBounds(xBase + 495, y + 12, 100, 25);
        painelPrincipal.add(lblPrazo);

        campoPrazo = new JTextField();
        campoPrazo.setBounds(xBase + 585, y + 12, 70, 28);
        painelPrincipal.add(campoPrazo);

        JLabel lblSistema = new JLabel("Sistema:");
        lblSistema.setBounds(xBase + 670, y + 12, 60, 25);
        painelPrincipal.add(lblSistema);

        comboSistema = new JComboBox<>(new String[]{"Price", "SAC"});
        comboSistema.setBounds(xBase + 725, y + 12, 80, 28);
        painelPrincipal.add(comboSistema);

        JButton btnGerar = new JButton("Gerar Cronograma");
        btnGerar.setBackground(COR_AZUL);
        btnGerar.setForeground(Color.WHITE);
        btnGerar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGerar.setBounds(xBase + 820, y + 9, 150, 34);
        btnGerar.setFocusPainted(false);
        btnGerar.addActionListener(e -> gerarCronograma());
        painelPrincipal.add(btnGerar);

        JButton btnExportar = new JButton("Exportar .txt");
        btnExportar.setBounds(715, y + 55, 160, 30);
        btnExportar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(e -> exportarTxt());
        painelPrincipal.add(btnExportar);

        return y + 100;
    }

    private int criarTabela(int y) {
        String[] colunas = {"N\u00BA", "M\u00EAs/Ano", "Parcela (R$)", "Amortiza\u00E7\u00E3o (R$)", "Juros (R$)", "Saldo Devedor (R$)", "% Quitado"};

        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        JTableHeader header = tabela.getTableHeader();
        header.setBackground(COR_HEADER);
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
        tabela.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer());

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, y, 860, 460);
        painelPrincipal.add(scroll);

        return y + 470;
    }

    private void criarFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 650, 900, 50);
        footer.setBackground(FUNDO_ESCURO);

        JLabel lblFooter = new JLabel("Logado como: " + nomeUsuario + " \u00B7 ImobiCalc Pro");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblFooter.setBounds(20, 15, 350, 20);
        footer.add(lblFooter);

        JLabel lblStatus = new JLabel("\u25CF MySQL conectado");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(new Color(34, 197, 94));
        lblStatus.setBounds(700, 15, 180, 20);
        footer.add(lblStatus);

        painelPrincipal.add(footer);
    }

    private void gerarCronograma() {
        try {
            double financiado = Double.parseDouble(campoValor.getText().replace(",", ".").replace("R$", "").trim());
            double taxa = Double.parseDouble(campoJuros.getText().replace(",", ".").trim());
            int prazo = Integer.parseInt(campoPrazo.getText().trim());
            String sistema = (String) comboSistema.getSelectedItem();

            double i = taxa / 100.0;

            if (financiado <= 0 || i < 0 || prazo <= 0) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos com valores v\u00E1lidos!");
                return;
            }

            modelo.setRowCount(0);

            LocalDate hoje = LocalDate.now();
            int mesRef = hoje.getMonthValue();
            int anoRef = hoje.getYear();

            double totalParcela = 0;
            double totalAmort = 0;
            double totalJuros = 0;
            double saldo;

            StringBuilder resultadoStr = new StringBuilder();

            if ("Price".equals(sistema)) {
                double parcela = (financiado * i) / (1 - Math.pow(1 + i, -prazo));
                saldo = financiado;

                for (int mes = 1; mes <= prazo; mes++) {
                    String mesAno = getMesAno(mesRef, anoRef, mes - 1);
                    double jurosM = saldo * i;
                    double amortM = parcela - jurosM;
                    saldo -= amortM;
                    if (saldo < 0) saldo = 0;
                    double pct = (financiado > 0) ? ((financiado - saldo) / financiado * 100) : 100;

                    totalParcela += parcela;
                    totalAmort += amortM;
                    totalJuros += jurosM;

                    modelo.addRow(new Object[]{
                            mes, mesAno, FMT.format(parcela), FMT.format(amortM),
                            FMT.format(jurosM), FMT.format(saldo),
                            String.format("%.1f%%", pct)
                    });

                    resultadoStr.append(mes).append("|").append(mesAno).append("|")
                            .append(String.format("%.2f", parcela)).append("|")
                            .append(String.format("%.2f", amortM)).append("|")
                            .append(String.format("%.2f", jurosM)).append("|")
                            .append(String.format("%.2f", saldo)).append(";");
                }

            } else { // SAC
                double amortFixa = financiado / prazo;
                saldo = financiado;

                for (int mes = 1; mes <= prazo; mes++) {
                    String mesAno = getMesAno(mesRef, anoRef, mes - 1);
                    double jurosM = saldo * i;
                    double parcelaM = amortFixa + jurosM;
                    saldo -= amortFixa;
                    if (saldo < 0) saldo = 0;
                    double pct = (financiado > 0) ? ((financiado - saldo) / financiado * 100) : 100;

                    totalParcela += parcelaM;
                    totalAmort += amortFixa;
                    totalJuros += jurosM;

                    modelo.addRow(new Object[]{
                            mes, mesAno, FMT.format(parcelaM), FMT.format(amortFixa),
                            FMT.format(jurosM), FMT.format(saldo),
                            String.format("%.1f%%", pct)
                    });

                    resultadoStr.append(mes).append("|").append(mesAno).append("|")
                            .append(String.format("%.2f", parcelaM)).append("|")
                            .append(String.format("%.2f", amortFixa)).append("|")
                            .append(String.format("%.2f", jurosM)).append("|")
                            .append(String.format("%.2f", saldo)).append(";");
                }
            }

            // LINHA DE TOTAL
            modelo.addRow(new Object[]{
                    "TOTAL", "", FMT.format(totalParcela), FMT.format(totalAmort),
                    FMT.format(totalJuros), FMT.format(0.0), ""
            });

            ultimoResultado = "Sistema: " + sistema + " | Financiado: " + FMT.format(financiado)
                    + " | Total pago: " + FMT.format(totalParcela)
                    + " | Total juros: " + FMT.format(totalJuros);

            // Salvar no banco
            SimulacaoDAO dao = new SimulacaoDAO();
            dao.salvarSimulacao(usuarioId, "CRONOGRAMA", financiado, 0, prazo, taxa,
                    sistema + " | Total pago=" + FMT.format(totalParcela)
                            + " | Total juros=" + FMT.format(totalJuros));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar cronograma: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String getMesAno(int mesInicial, int anoInicial, int incremento) {
        int mes = mesInicial + incremento;
        int ano = anoInicial;
        while (mes > 12) {
            mes -= 12;
            ano++;
        }
        String nomeMes = LocalDate.of(ano, mes, 1)
                .getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        // Primeira letra maiúscula
        nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
        return nomeMes + "/" + ano;
    }

    private void exportarTxt() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Gere um cronograma primeiro!");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("cronograma_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(chooser.getSelectedFile()), "UTF-8"))) {
                String sistema = (String) comboSistema.getSelectedItem();
                String valor = campoValor.getText();
                String taxa = campoJuros.getText();
                String prazo = campoPrazo.getText();

                pw.println("CRONOGRAMA DE FINANCIAMENTO \u2014 ImobiCalc Pro");
                pw.println("Gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())
                        + " | Sistema: " + sistema);
                pw.println("Valor: R$" + valor + " | Taxa: " + taxa + "% a.m. | Prazo: " + prazo + " meses");
                pw.println("---------------------------------------------------");
                pw.println("N\u00BA  | M\u00EAs/Ano | Parcela | Amortiza\u00E7\u00E3o | Juros | Saldo");

                for (int r = 0; r < modelo.getRowCount(); r++) {
                    String linha = "";
                    for (int c = 0; c < modelo.getColumnCount(); c++) {
                        Object val = modelo.getValueAt(r, c);
                        linha += (val != null ? val.toString() : "");
                        if (c < modelo.getColumnCount() - 1) linha += " | ";
                    }
                    pw.println(linha);
                }

                pw.println("---------------------------------------------------");
                pw.println("ImobiCalc Pro | SENAC \u2014 Programador de Sistemas");

                JOptionPane.showMessageDialog(this, "Arquivo exportado com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
                ex.printStackTrace();
            }
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