package br.com.calculadoraorganizacional.dao;

import br.com.calculadoraorganizacional.ConexaoMySQL;
import br.com.calculadoraorganizacional.ConexaoMySQL;
import br.com.calculadoraorganizacional.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private ConexaoMySQL ConexaoMySQL;

    public List<Cliente> buscarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";

        try {
            Connection conn = ConexaoMySQL.conectar();
            if (conn == null) return lista;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setEndereco(rs.getString("endereco"));
                c.setRendaMensal(rs.getBigDecimal("renda_mensal"));
                c.setDataCadastro(rs.getDate("data_cadastro"));
                lista.add(c);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes!");
            e.printStackTrace();
        }

        return lista;
    }

    public List<Cliente> buscarPorNome(String nomeBusca) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ? ORDER BY nome";

        try {
            Connection conn = ConexaoMySQL.conectar();
            if (conn == null) return lista;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setEndereco(rs.getString("endereco"));
                c.setRendaMensal(rs.getBigDecimal("renda_mensal"));
                c.setDataCadastro(rs.getDate("data_cadastro"));
                lista.add(c);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes por nome!");
            e.printStackTrace();
        }

        return lista;
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try {
            Connection conn = ConexaoMySQL.conectar();
            if (conn == null) return null;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Cliente c = null;
            if (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setEndereco(rs.getString("endereco"));
                c.setRendaMensal(rs.getBigDecimal("renda_mensal"));
                c.setDataCadastro(rs.getDate("data_cadastro"));
            }

            rs.close();
            stmt.close();
            conn.close();
            return c;

        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID!");
            e.printStackTrace();
        }

        return null;
    }
}