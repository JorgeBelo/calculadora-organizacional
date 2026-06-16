package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.connection.ConexaoMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaHistorico extends JFrame {

    private JTable tabelaHistorico;

    public TelaHistorico(int usuarioId) {

        setTitle("ImobiCalc Pro - Histórico");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        getContentPane().setBackground(
                new Color(15, 23, 42)
        );

        // TÍTULO

        JLabel titulo = new JLabel(
                "Histórico de Simulações",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setForeground(Color.WHITE);

        titulo.setBounds(
                150,
                20,
                600,
                40
        );

        add(titulo);

        // SUBTÍTULO

        JLabel subtitulo = new JLabel(
                "Visualize todas as operações realizadas",
                SwingConstants.CENTER
        );

        subtitulo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        subtitulo.setForeground(
                new Color(203, 213, 225)
        );

        subtitulo.setBounds(
                150,
                55,
                600,
                25
        );

        add(subtitulo);

        // CARD

        JPanel card = new JPanel();

        card.setLayout(new BorderLayout());

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBounds(
                30,
                100,
                825,
                420
        );

        add(card);

        String[] colunas = {
                "Expressão",
                "Resultado",
                "Data"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(colunas, 0);

        tabelaHistorico =
                new JTable(modelo);

        // ESTILO DA TABELA

        tabelaHistorico.setRowHeight(30);

        tabelaHistorico.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        tabelaHistorico.setBackground(Color.WHITE);

        tabelaHistorico.setSelectionBackground(
                new Color(59, 130, 246)
        );

        tabelaHistorico.setGridColor(
                new Color(220, 220, 220)
        );

        JTableHeader header =
                tabelaHistorico.getTableHeader();

        header.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        header.setBackground(
                new Color(59, 130, 246)
        );

        header.setForeground(Color.WHITE);

        JScrollPane scrollPane =
                new JScrollPane(tabelaHistorico);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        card.add(scrollPane, BorderLayout.CENTER);

        carregarHistorico(
                modelo,
                usuarioId
        );

        setVisible(true);
    }

    private void carregarHistorico(
            DefaultTableModel modelo,
            int usuarioId
    ) {

        String sql =
                "SELECT expressao, resultado, data_operacao " +
                        "FROM historico " +
                        "WHERE usuario_id = ? " +
                        "ORDER BY data_operacao DESC";

        try {

            Connection conn =
                    ConexaoMySQL.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, usuarioId);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                modelo.addRow(new Object[]{

                        rs.getString("expressao"),

                        rs.getString("resultado"),

                        rs.getString("data_operacao")
                });
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar histórico!"
            );

            e.printStackTrace();
        }
    }
}