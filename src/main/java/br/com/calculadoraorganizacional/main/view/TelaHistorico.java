package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.main.connection.ConexaoMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.*;

public class TelaHistorico extends JFrame {

    private JTable tabelaHistorico;

    public TelaHistorico(int usuarioId) {

        setTitle("Histórico de Simulações");

        setSize(800, 500);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(new BorderLayout());

        String[] colunas = {
                "Expressão",
                "Resultado",
                "Data"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(colunas, 0);

        tabelaHistorico =
                new JTable(modelo);

        JScrollPane scrollPane =
                new JScrollPane(tabelaHistorico);

        add(scrollPane, BorderLayout.CENTER);

        carregarHistorico(modelo, usuarioId);

        setVisible(true);
    }

    private void carregarHistorico(
            DefaultTableModel modelo,
            int usuarioId
    ) {

        String sql =
                "SELECT expressao, resultado, data_operacao " +
                        "FROM historico WHERE usuario_id = ?";

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