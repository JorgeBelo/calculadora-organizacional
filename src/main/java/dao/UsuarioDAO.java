package br.com.calculadoraorganizacional.dao;

import br.com.calculadoraorganizacional.connection.ConexaoMySQL;
import br.com.calculadoraorganizacional.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void cadastrarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome, login, senha) VALUES (?, ?, ?)";

        try {

            Connection conn = ConexaoMySQL.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());

            stmt.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

            stmt.close();
            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar usuário!");
            e.printStackTrace();
        }
    }
}