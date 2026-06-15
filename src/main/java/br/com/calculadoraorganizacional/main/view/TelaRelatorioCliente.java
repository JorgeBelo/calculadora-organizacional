package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.ClienteDAO;
import br.com.calculadoraorganizacional.model.Cliente;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TelaRelatorioCliente extends JFrame {

    private JTextField campoBusca;
    private JTable tabela;
    private DefaultTableModel modelo;

    private JPanel painelPrincipal;
    private boolean modoEscuro = true;

    private String nomeUsuario;
    private int usuarioId;

    private ClienteDAO clienteDAO;

    private static final Color FUNDO_ESCURO = new Color(15, 23, 42);
    private static final Color FUNDO_CLARO = new Color(248, 250, 252);
    private static final Color COR_HEADER = new Color(15, 23, 42);
    private static final Color TEXTO_SECUNDARIO_ESCURO = new Color(203, 213, 225);
    private static final Color TEXTO_SECUNDARIO_CLARO = new Color(71, 85, 105);
    private static final Color COR_AZUL = new Color(29, 78, 216);
    private static final Color VERDE = new Color(34, 197, 94);

    private static final NumberFormat FMT_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public TelaRelatorioCliente(String nomeUsuario, int usuarioId) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioId = usuarioId;

        clienteDAO = new ClienteDAO();

        setTitle("Relatório de Clientes");
        setSize(860, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBounds(0, 0, 860, 680);
        add(painelPrincipal);

        int y = 0;

        // HEADER
        y = criarHeader(y);

        // BUSCA
        y = criarBusca(y);

        // TABELA
        y = criarTabela(y);

        // RODAPÉ
        criarFooter();

        // Carregar dados
        carregarClientes();

        aplicarTemaEscuro();
        setVisible(true);
    }

    private int criarHeader(int y) {
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, y, 860, 60);
        header.setBackground(FUNDO_ESCURO);

        JLabel titulo = new JLabel("Relatório de Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 8, 860, 28);
        header.add(titulo);

        JLabel subtitulo = new JLabel("Visualize e exporte dados cadastrais dos clientes", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(TEXTO_SECUNDARIO_ESCURO);
        subtitulo.setBounds(0, 34, 860, 20);
        header.add(subtitulo);

        RoundedButton btnTema = new RoundedButton("Alternar Tema");
        btnTema.setBounds(710, 10, 130, 40);
        btnTema.setFont(new Font("Arial", Font.BOLD, 12));
        btnTema.addActionListener(e -> alternarTema());
        header.add(btnTema);

        painelPrincipal.add(header);
        return y + 60;
    }

    private int criarBusca(int y) {
        int yAtual = y + 15;

        JLabel lblBusca = new JLabel("Buscar Cliente:");
        lblBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBusca.setBounds(30, yAtual, 120, 28);
        painelPrincipal.add(lblBusca);

        campoBusca = new JTextField();
        campoBusca.setBounds(150, yAtual, 250, 28);
        painelPrincipal.add(campoBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(COR_AZUL);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBounds(410, yAtual, 100, 30);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscarClientes());
        painelPrincipal.add(btnBuscar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(520, yAtual, 100, 30);
        btnLimpar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(e -> {
            campoBusca.setText("");
            carregarClientes();
        });
        painelPrincipal.add(btnLimpar);

        JButton btnExportar = new JButton("Exportar .txt");
        btnExportar.setBounds(680, yAtual, 130, 30);
        btnExportar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(e -> exportarTxt());
        painelPrincipal.add(btnExportar);

        return yAtual + 55;
    }

    private int criarTabela(int y) {
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "E-mail", "Renda Mensal", "Data Cadastro"};

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
        header.setFont(new Font("Arial", Font.BOLD, 12));

        tabela.setFont(new Font("Arial", Font.PLAIN, 11));
        tabela.setRowHeight(22);
        tabela.setFillsViewportHeight(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Centralizar ID
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centralizado);

        // Alinhar renda à direita
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, y, 820, 510);
        painelPrincipal.add(scroll);

        return y + 520;
    }

    private void criarFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 630, 860, 50);
        footer.setBackground(FUNDO_ESCURO);

        JLabel lblFooter = new JLabel("Logado como: " + nomeUsuario + " \u00B7 ImobiCalc Pro");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(TEXTO_SECUNDARIO_ESCURO);
        lblFooter.setBounds(20, 15, 350, 20);
        footer.add(lblFooter);

        JLabel lblStatus = new JLabel("\u25CF MySQL conectado");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(VERDE);
        lblStatus.setBounds(680, 15, 160, 20);
        footer.add(lblStatus);

        painelPrincipal.add(footer);
    }

    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.buscarTodos();
            preencherTabela(clientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void buscarClientes() {
        String busca = campoBusca.getText().trim();
        if (busca.isEmpty()) {
            carregarClientes();
            return;
        }

        try {
            List<Cliente> clientes = clienteDAO.buscarPorNome(busca);
            preencherTabela(clientes);

            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado para: " + busca);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void preencherTabela(List<Cliente> clientes) {
        modelo.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Cliente c : clientes) {
            String cpf = c.getCpf() != null ? c.getCpf() : "-";
            String telefone = c.getTelefone() != null ? c.getTelefone() : "-";
            String email = c.getEmail() != null ? c.getEmail() : "-";
            String renda = c.getRendaMensal() != null ? FMT_MOEDA.format(c.getRendaMensal()) : "-";
            String data = c.getDataCadastro() != null ? sdf.format(c.getDataCadastro()) : "-";

            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    cpf,
                    telefone,
                    email,
                    renda,
                    data
            });
        }
    }

    private void exportarTxt() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente para exportar!");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("relatorio_clientes_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String caminho = chooser.getSelectedFile().getAbsolutePath();
            try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
                pw.println("RELAT\u00d3RIO DE CLIENTES \u2014 ImobiCalc Pro");
                pw.println("Gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
                pw.println("Total de registros: " + modelo.getRowCount());
                pw.println("========================================");

                String formatoLinha = "| %-3s | %-30s | %-14s | %-15s | %-30s | %-14s | %-12s |%n";
                String separador = "+-----+--------------------------------+----------------+-----------------+--------------------------------+----------------+--------------+%n";

                pw.printf(separador);
                pw.printf(formatoLinha, "ID", "Nome", "CPF", "Telefone", "E-mail", "Renda Mensal", "Data Cadastro");
                pw.printf(separador);

                for (int r = 0; r < modelo.getRowCount(); r++) {
                    pw.printf(formatoLinha,
                            modelo.getValueAt(r, 0),
                            modelo.getValueAt(r, 1),
                            modelo.getValueAt(r, 2),
                            modelo.getValueAt(r, 3),
                            modelo.getValueAt(r, 4),
                            modelo.getValueAt(r, 5),
                            modelo.getValueAt(r, 6)
                    );
                }

                pw.printf(separador);
                pw.println("ImobiCalc Pro | SENAC \u2014 Programador de Sistemas");

                JOptionPane.showMessageDialog(this, "Relat\u00f3rio exportado com sucesso!");

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