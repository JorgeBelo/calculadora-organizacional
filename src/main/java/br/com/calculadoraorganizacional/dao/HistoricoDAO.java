package br.com.calculadoraorganizacional.dao;

import br.com.calculadoraorganizacional.ConexaoMySQL;
import br.com.calculadoraorganizacional.model.Historico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoDAO {

    public void salvar(Historico historico) {

        String sql = "INSERT INTO historico (usuario_id, tipo, expressao, resultado) VALUES (?, ?, ?, ?)";

        try {

            Connection conn = ConexaoMySQL.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, historico.getUsuarioId());
            stmt.setString(2, historico.getTipo());
            stmt.setString(3, historico.getExpressao());
            stmt.setString(4, historico.getResultado());

            stmt.executeUpdate();

            System.out.println("Histórico salvo com sucesso!");

            stmt.close();
            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro ao salvar histórico!");
            e.printStackTrace();
        }
    }
}