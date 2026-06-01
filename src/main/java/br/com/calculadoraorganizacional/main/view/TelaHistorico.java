package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.connection.ConexaoMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaHistorico extends JFrame {

    private JTable tabelaHistorico;

    public TelaHistorico(int usuarioId) {

        setTitle("Histórico de Cálculos");

        setSize(700, 500);

        setLocationRelativeTo(null);

        setLayout(null);

        String[] colunas = {
                "Expressão",
                "Resultado",
                "Data"
        };

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        tabelaHistorico = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);

        scrollPane.setBounds(20, 20, 640, 380);

        add(scrollPane);

        carregarHistorico(modelo, usuarioId);

        setVisible(true);
    }

    private void carregarHistorico(DefaultTableModel modelo, int usuarioId) {

        String sql =
                "SELECT expressao, resultado, data_operacao " +
                        "FROM historico WHERE usuario_id = ?";

        try {

            Connection conn = ConexaoMySQL.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, usuarioId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String expressao = rs.getString("expressao");

                String resultado = rs.getString("resultado");

                String data = rs.getString("data_operacao");

                modelo.addRow(new Object[]{
                        expressao,
                        resultado,
                        data
                });
            }

            rs.close();

            stmt.close();

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}