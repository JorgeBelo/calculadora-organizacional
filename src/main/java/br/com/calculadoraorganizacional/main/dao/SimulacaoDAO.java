package br.com.calculadoraorganizacional.main.dao;

import br.com.calculadoraorganizacional.connection.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SimulacaoDAO {

    public void salvarSimulacao(int usuarioId, String tipo,
                                double valorImovel, double entrada,
                                int prazo, double juros, String resultado) {

        String sql = "INSERT INTO simulacoes (usuario_id, tipo, valor_imovel, entrada, prazo, juros, resultado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection conn = ConexaoMySQL.conectar();

            if (conn == null) return;

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, usuarioId);
            stmt.setString(2, tipo);
            stmt.setDouble(3, valorImovel);
            stmt.setDouble(4, entrada);
            stmt.setInt(5, prazo);
            stmt.setDouble(6, juros);
            stmt.setString(7, resultado);

            stmt.executeUpdate();

            System.out.println("Simulação salva com sucesso! Tipo: " + tipo);

            stmt.close();
            conn.close();

        } catch (Exception e) {

            System.out.println("Erro ao salvar simulação!");
            e.printStackTrace();
        }
    }
}